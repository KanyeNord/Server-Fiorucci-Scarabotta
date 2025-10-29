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
	//TODO risposta messaggi broadcast
	//TODO controllo se uno non indenta la risposta correttamente
	//TODO fa le robe come cristo comanda
	static ArrayList<String> aUtenti = new ArrayList<String>();
	static ArrayList<Session> aSessione = new ArrayList<Session>();
	static Map<Session, Boolean> mappaSessioni = new HashMap<>();
	@OnMessage
	public void visualizzaRichiesta(Session session, String msg) {
		System.out.println("questo, brutto cattivo,Daniele Fiorucci, nato il 20/05/2007 è il messaggio, NON IL BROADCAAST:" + msg);
//		boolean accesso = false;
		if(mappaSessioni.get(session)==null) {
			mappaSessioni.put(session, false);
		}
		//try {
			if (session.isOpen()) {
				
				String vAccesso[] = msg.split("\\|");
				if(vAccesso[0].equals("A") && mappaSessioni.get(session)==false){
				String username = vAccesso[1];
				String pwd = vAccesso[2];
				System.out.println("nome= "+username);
				System.out.println("pwd= "+pwd);
				if(username.toUpperCase().equals(username)) {
					if(pwd.charAt(1)=='l') {
						System.out.println("ENTRATO");
//						accesso=true;
						aUtenti.add(username);
						aSessione.add(session);
						mappaSessioni.put(session, true);
						try {
							System.out.println("utenti nella sessione" + aSessione.size());
							for(int i = 0; i<aSessione.size();i++) {
								aSessione.get(i).getBasicRemote().sendText(broadcast());
							}
//						session.getBasicRemote().sendText(broadcast());
						} catch(IOException e) {System.out.println(e);}
//						System.out.println(broadcast());
					}else {
						System.out.println("NON ENTRATO");
					}
				}else {
					System.out.println("nome errato");
					
				}
				}else { 
					if(mappaSessioni.get(session) && vAccesso[0].equals("M")) {
					String vMessaggio[] = msg.split("\\|");
					String nome = vMessaggio[1];
					String timeStamp = vMessaggio[2];
					String paese = vMessaggio[3];
					String mediaType = vMessaggio[4];
					String testo = vMessaggio[5];
					
//					"M|nome|ts|paese|mt|testo"
					
				}
			}
			}
//		} catch (IOException e) {
//			try {
//
//				session.close();
//			} catch (IOException el) {
//				// niente
//			}
//		}
	
	
//    	response.getWriter().write("ciao");q
    }
	public String broadcast() {
		String nomiUtenti="U";
		for(int i=0; i<aUtenti.size();i++) {
			 nomiUtenti+="|" + aUtenti.get(i);
			 System.out.println("nomi utenti: " + nomiUtenti);
			 System.out.println("lista utenti: " + aUtenti);
		
		}
		return nomiUtenti;
	}
	@OnClose
	public void chiudiConnessione(Session session) {
//		System.out.println("disconnesso " + aUtenti.get(i));
		int i;
		for (i = 0;i<aSessione.size();i++) {
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