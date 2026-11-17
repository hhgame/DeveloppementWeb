package reception;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/add")
public class addServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        int nombre1 = 0;
        int nombre2 = 0;
        boolean valide = true;
        try {
	        nombre1 = Integer.parseInt(request.getParameter("nombre1"));
	        nombre2 = Integer.parseInt(request.getParameter("nombre2"));
        } catch (NumberFormatException e) {
        	valide = false;
        }
        
        
        try (PrintWriter out = response.getWriter()) {
        	if(valide) {
        		int resAddition = nombre1 + nombre2;
	            out.println("<!DOCTYPE html>");
	            out.println("<html>");
	            out.println("<head><title>ADDITION</title></head>");
	            out.println("<body>");
	            out.println("<h1>Résultat de l'addition de "+nombre1+" et "+ nombre2 + "</h1>");
	            out.println("<p>Le résultat est <strong>"+ resAddition +"</strong></p>");
	            out.println("</body>");
	            out.println("</html>");
	            return;
        	}
        	else {
        		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètre d'addition invalides");
        	    return;
        	}
        }
    }
}
