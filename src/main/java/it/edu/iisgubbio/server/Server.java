package it.edu.iisgubbio.server;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/nino")

public class Server {

    static ArrayList<String> aUtenti = new ArrayList<String>();
    static ArrayList<Session> aSessione = new ArrayList<Session>();
    static Map<Session, Boolean> mappaSessioni = new HashMap<>();//a ogni sessione corrisponde una boolean che dice se quella sessione ha effettuato l'accesso o no

    @OnMessage
    public void visualizzaRichiesta(Session session, String msg) {
        System.out.println("messaggio: " + msg);
        if (mappaSessioni.get(session) == null) {//se alla sessione corrisponde null significa che è nuova e va assegnato il valore false
            mappaSessioni.put(session, false);
        }
        if (session.isOpen()) {
            if (!msg.equals("")) {
                String vMessaggio[] = msg.split("\\|");
                if (vMessaggio[0].equals("A")) {//se il messaggio è di tipo accesso
                    if (mappaSessioni.get(session) == false) {//se la sessione non ha già effettuato accesso
                        if (vMessaggio.length == 3) {//se il messaggio è una richiesta di accesso deve essere diviso in 3 parametri
                            String username = vMessaggio[1];
                            String pwd = vMessaggio[2];
                            System.out.println("nome: " + username);
                            System.out.println("password: " + pwd);
                            if (username.toUpperCase().equals(username)) {
                                if (pwd.charAt(1) == 'l') {
                                    System.out.println("ENTRATO");
                                    aUtenti.add(username);
                                    aSessione.add(session);
                                    mappaSessioni.put(session, true);
                                    try {
                                        session.getBasicRemote().sendText("R|ok");
                                        for (int i = 0; i < aSessione.size(); i++) {
                                            aSessione.get(i).getBasicRemote().sendText(listaUtenti());
                                        }
                                    } catch (IOException e) {
                                        System.out.println(e);
                                    }
                                } else {
                                    try {
                                        session.getBasicRemote().sendText("R|no");
                                    } catch (IOException e) {
                                        System.out.println(e);
                                    }
                                    System.out.println("PASSWORD ERRATA");
                                }
                            } else {
                                try {
                                    session.getBasicRemote().sendText("R|no");
                                } catch (IOException e) {
                                    System.out.println(e);
                                }
                                System.out.println("NOME ERRATO");
                            }
                        } else {
                            try {
                                session.getBasicRemote().sendText("R|no");
                                System.out.println("NUMERO DI PARAMETRI ERRATO");
                            } catch (IOException e) {
                                System.out.println(e);
                            }
                        }
                    } else {
                        try {
                            session.getBasicRemote().sendText("R|no");
                        } catch (IOException e) {
                            System.out.println(e);
                        }
                        System.out.println("ACCESSO GIÀ EFFETTUATO");
                    }
                } else {
                    if (vMessaggio[0].equals("M")) {//se il messaggio è di tipo messaggio 
                        if (mappaSessioni.get(session)) {//se la sessione ha effettuato l'accesso
                            if (vMessaggio.length == 6) {//se il messaggio è diviso in 6 è una richiesta di messaggio

                                String nome = vMessaggio[1];
                                String timeStamp = vMessaggio[2];
                                String paese = vMessaggio[3];
                                String mediaType = vMessaggio[4];
                                String testo = vMessaggio[5];
                                //"M|nome|ts|paese|mt|testo"

                                for (int i = 0; i < aSessione.size(); i++) {
                                    if (session == aSessione.get(i)) {
                                        if (!aUtenti.get(i).equals(nome)) {
                                            try {
                                                session.close();
                                            } catch (IOException e) {
                                                System.out.println(e);
                                            }
                                            System.out.println("NOME NON CORRISPONDE A QUELLO FORNITO ALL'ACCESSO");
                                            break;
                                        }
                                    }
                                }

                                try {
                                    Instant.parse(timeStamp);
                                } catch (DateTimeParseException i) {
                                    try {
                                        session.close();
                                    } catch (IOException e) {
                                        System.out.println(e);
                                    }
                                    System.out.println("FORMATO DATA ERRATO");
                                }
                                
                                String codiciPaesi[] = Locale.getISOCountries();
                                for (int i = 0; i < codiciPaesi.length; i++) {
                                    if (codiciPaesi[i].equals(paese)) {
                                    }

                                }

                                if (!mediaType.equals("text/plain")) {
                                    try {
                                        session.close();
                                    } catch (IOException e) {
                                        System.out.println(e);
                                    }
                                    System.out.println("MEDIATYPE ERRATO O NON SUPPORTATO");
                                }
                                if (testo.length() > 50) {
                                    try {
                                        session.close();
                                    } catch (IOException e) {
                                        System.out.println(e);
                                    }
                                    System.out.println("TESTO VUOTO");
                                }

                                try {
                                    for (int i = 0; i < aSessione.size(); i++) {
                                        aSessione.get(i).getBasicRemote().sendText(msg);
                                    }
                                } catch (IOException e) {
                                    System.out.println(e);
                                }
                            } else {
                                try {
                                    session.getBasicRemote().sendText("R|no");
                                    System.out.println("NUMERO DI PARAMETRI ERRATO");
                                } catch (IOException e) {
                                    System.out.println(e);
                                }
                            }
                        } else {
                            try {
                                session.getBasicRemote().sendText("R|no");
                            } catch (IOException e) {
                                System.out.println(e);
                            }
                            System.out.println("ACCESSO NON EFFETTUATO");
                        }
                    } else {
                        try {
                            session.getBasicRemote().sendText("R|no");
                            System.out.println("TIPO DI MESSAGGIO NON ESISTENTE");
                        } catch (IOException e) {
                            System.out.println(e);
                        }

                    }

                }

            } else {
                try {
                    session.getBasicRemote().sendText("R|no");
                    System.out.println("MESSAGGIO VUOTO");
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
    }

    public String listaUtenti() {
        String nomiUtenti = "U";
        for (int i = 0; i < aUtenti.size(); i++) {
            nomiUtenti += "|" + aUtenti.get(i);
        }
        return nomiUtenti;
    }

    @OnClose
    public void chiudiConnessione(Session session) {
        for (int i = 0; i < aSessione.size(); i++) {
            if (session == aSessione.get(i)) {
                System.out.println(aUtenti.get(i) + " si è disconnesso");
                mappaSessioni.remove(aSessione.get(i));
                aSessione.remove(i);
                aUtenti.remove(i);
                break;
            }
        }

    }
}
