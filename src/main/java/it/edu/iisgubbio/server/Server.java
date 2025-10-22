package it.edu.iisgubbio.server;
import java.io.IOException;
import java.util.ArrayList;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;


@ServerEndpoint("/nino")

public class Server {
	
	ArrayList<String> aUtenti = new ArrayList<String>();
	ArrayList<Session> aSessione = new ArrayList<Session>();
	@OnMessage
	public void visualizzaRichiesta(Session session, String msg) {
		System.out.println(msg);
		boolean accesso = false;
		
		//try {
			if (session.isOpen()) {
				//session.getBasicRemote().sendText(msg);
				String vAccesso[] = msg.split("\\|");
				String username = vAccesso[1];
				String pwd = vAccesso[2];
				System.out.println("nome= "+username);
				System.out.println("pwd= "+pwd);
				if(username.toUpperCase().equals(username)) {
					if(pwd.charAt(1)=='l') {
						System.out.println("ENTRATO");
						accesso=true;
						aUtenti.add(username);
					}else {
						System.out.println("NON ENTRATO");
					}
				}else {
					System.out.println("nome errato");
					
				}
				if(accesso) {
					String vMessaggio[] = msg.split("\\|");
					String nome = vMessaggio[1];
					String timeStamp = vMessaggio[2];
					String paese = vMessaggio[3];
					String mediaType = vMessaggio[4];
					String testo = vMessaggio[5];
					
//					"M|nome|ts|paese|mt|testo"
					
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
	@OnClose
	public void chiudiConnessione(Session session) {
		for (int i = 0;i<aSessione.size();i++) {
			if(session == aSessione.get(i)) {
				aSessione.remove(i);
				aUtenti.remove(i);
			}
		}
	}
}