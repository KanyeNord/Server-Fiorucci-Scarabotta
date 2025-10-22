package it.edu.iisgubbio.server;
import java.io.IOException;
import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;


@ServerEndpoint("/nino")

public class Server {
	@OnMessage
	public void visualizzaRichiesta(Session session, String msg) {
		System.out.println(msg);
		boolean accesso = false;
		//try {
			if (session.isOpen()) {
				//session.getBasicRemote().sendText(msg);
				String x[] = msg.split("\\|");
				String username = x[1];
				String pwd = x[2];
				System.out.println("nome= "+username);
				System.out.println("pwd= "+pwd);
				if(username.toUpperCase().equals(username)) {
					if(pwd.charAt(1)=='l') {
						System.out.println("ENTRATO");
						accesso=true;
					}else {
						System.out.println("NON ENTRATO");
					}
				}else {
					System.out.println("nome errato");
					
				}
				if(accesso) {
					
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

}