package it.edu.iisgubbio.server;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;


@ServerEndpoint("/nino")

public class Server {
	//TODO fare immagini o gif
	//TODO se fa prima la M di di fare prima la A
	//TODO dividere l'if dell'accesso fatto da quello del tipo di messaggio
	//TODO fare controllo se il paese ha più di 2 caratteri
	static ArrayList<String> aUtenti = new ArrayList<String>();
	static ArrayList<Session> aSessione = new ArrayList<Session>();
	static Map<Session, Boolean> mappaSessioni = new HashMap<>();//a ogni sessione corrisponde una boolean che dice se quella sessione ha effettuato l'accesso o no
	@OnMessage
	public void visualizzaRichiesta(Session session, String msg) {
		System.out.println("messaggio: " + msg);
		if(mappaSessioni.get(session)==null) {//se alla sessione corrisponde null significa che è nuova e va assegnato il valore false
			mappaSessioni.put(session, false);
		}
		if (session.isOpen()) {
			if(!msg.equals("")) {
				String vMessaggio[] = msg.split("\\|");
				if(vMessaggio.length==3 || vMessaggio.length==6) {//se il messaggio è una richiesta di accesso deve essere diviso in 3 parametri, se è diviso in 6 è un messaggio altrimenti è scritto male
			if(vMessaggio[0].equals("A") && mappaSessioni.get(session)==false){//se il messaggio è di tipo accesso e la sessione non ha già effettuato accesso
				String username = vMessaggio[1];
				String pwd = vMessaggio[2];
				System.out.println("nome: " + username);
				System.out.println("password: " + pwd);
				if(username.toUpperCase().equals(username)) {
					if(pwd.charAt(1)=='l') {
						System.out.println("ENTRATO");
						aUtenti.add(username);
						aSessione.add(session);
						mappaSessioni.put(session, true);
						try {
							session.getBasicRemote().sendText("R|ok");
							for(int i = 0; i<aSessione.size();i++) {
								aSessione.get(i).getBasicRemote().sendText(listaUtenti());
							}
						} catch(IOException e) {System.out.println(e);}
					}else {
						try {
							session.getBasicRemote().sendText("R|no");
						} catch (IOException e) {System.out.println(e);}
						System.out.println("PASSWORD ERRATA");
					}
				}else {
					try {
						session.getBasicRemote().sendText("R|no");
					} catch (IOException e) {System.out.println(e);}
					System.out.println("NOME ERRATO");
				}
			}else { 
				if(vMessaggio[0].equals("M") && mappaSessioni.get(session)) {//se il messaggio è di tipo messaggio e la sessione ha effettuato l'accesso
					String nome = vMessaggio[1];
//					String timeStamp = vMessaggio[2];
					String paese = vMessaggio[3];
					String mediaType = vMessaggio[4];
					String testo = vMessaggio[5];
					//"M|nome|ts|paese|mt|testo"
					
						for (int i = 0;i<aSessione.size();i++) {
							if(session == aSessione.get(i)) {
								if(!aUtenti.get(i).equals(nome)) {
									try {
										session.close();
									} catch (IOException e) {System.out.println(e);}
									System.out.println("NOME NON CORRISPONDE A QUELLO FORNITO ALL'ACCESSO");
									break;
								}	
								}
							}
						//if timestamp
						
					if (!paese.equals("IT")) {
						try {
							session.close();
						} catch (IOException e) {System.out.println(e);}
						System.out.println("IL TUO PAESE NON È BENVENUTO NELLA NOSTRA CHAT");
					}
					if(!mediaType.equals("text/plain")) {
						try {
							session.close();
						} catch (IOException e) {System.out.println(e);}
						System.out.println("MEDIATYPE ERRATO O NON SUPPORTATO");
					}
//					if(testo.equals("")) {
//						try {
//							session.close();
//						} catch (IOException e) {System.out.println(e);}
//						System.out.println("TESTO VUOTO");
//					} commentata perchè se il testo è "" lo split non lo conta come elemento da aggiungere a vMessaggio
					
						
						
						
						
						
					
					//il nome deve essere uguale al nome dell'accesso
					//il timestamp deve essere del formato giusto e non avere irregolarità nei dati
					//il paese deve esse italia senno non funziona / deve essere un paese esistente(difficile)
					//mediatype deve essere text/plain, oppure anche un png o una gif
					//il testo non deve eessere vuoto e non essere più lungo di un tot magari
					try {
						for(int i = 0; i<aSessione.size();i++) {
							aSessione.get(i).getBasicRemote().sendText(msg);
						}
					} catch(IOException e) {System.out.println(e);}
				}  else { 
					try {
						session.getBasicRemote().sendText("R|no");
						System.out.println("TIPO DI MESSAGGIO NON ESISTENTE");
					} catch(IOException e) {System.out.println(e);}
				
				}
						
			}
		} else { 
			try {
				session.getBasicRemote().sendText("R|no");
				System.out.println("NUMERO DI PARAMETRI ERRATO");
			} catch(IOException e) {System.out.println(e);}
		}
			} else { 
				try {
					session.getBasicRemote().sendText("R|no");
					System.out.println("MESSAGGIO VUOTO");
				} catch(IOException e) {System.out.println(e);}
			}
		}
    }
	public String listaUtenti() {
		String nomiUtenti="U";
		for(int i=0; i<aUtenti.size();i++) {
			 nomiUtenti+="|" + aUtenti.get(i);			
		}
		return nomiUtenti;
	}
	@OnClose
	public void chiudiConnessione(Session session) {
		for (int i = 0;i<aSessione.size();i++) {
			if(session == aSessione.get(i)) {
				System.out.println(aUtenti.get(i) + " si è disconnesso");
				mappaSessioni.remove(aSessione.get(i));
				aSessione.remove(i);
				aUtenti.remove(i);
				break;
			}
		}

	}
}