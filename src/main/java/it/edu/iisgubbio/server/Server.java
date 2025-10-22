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
		try {
			if (session.isOpen()) {
				session.getBasicRemote().sendText(msg);
				System.out.println("so felice " + msg);
			}
		} catch (IOException e) {
			try {

				session.close();
			} catch (IOException el) {
				// niente
			}
		}
	
	
//    	response.getWriter().write("ciao");q
    }

}