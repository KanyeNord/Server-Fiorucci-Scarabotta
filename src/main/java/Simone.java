import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "FormServlet", urlPatterns = "/calculateServlet")
public class Simone extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException {

    	response.getWriter().write("ciao");
    }

}