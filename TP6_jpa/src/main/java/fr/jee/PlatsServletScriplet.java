package fr.jee;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.jee.dao.EntityManagerInstance;
import fr.jee.model.Plat;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/plats")
public class PlatsServletScriplet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public PlatsServletScriplet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		EntityManager entityManager = EntityManagerInstance.getInstance();
        
		TypedQuery<Plat> query = entityManager.createQuery("from Plat", Plat.class);
	
		request.setAttribute("plats", query.getResultList());	
		
		this.getServletContext().getRequestDispatcher("/views/platsByScriplet.jsp").forward(request, response);
		
		entityManager.close();
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
