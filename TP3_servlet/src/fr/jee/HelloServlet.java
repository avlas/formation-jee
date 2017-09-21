package fr.jee;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public HelloServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
//		// Afficher dans console
		System.out.println("HelloWorld");
		
//		// Generer du HTML 		
		PrintWriter out = response.getWriter();
		out.println("<html><head>");
        out.println("<title> A very simple servlet example</title>");
        out.println("</head><body>");
		out.println("<h1> HelloWorld </h1>");
        out.println("</body></html>");
		
		
//		// Afficher depuis JSP
//	//	System.out.println("CONTEXT =" + request.getContextPath());
//		response.sendRedirect(request.getContextPath() + "/views/hello.jsp");
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
