package fr.biblioteque.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.biblioteque.business.LivreService;
import fr.biblioteque.business.LivreServiceImpl;
import fr.biblioteque.dao.entity.Auteur;
import fr.biblioteque.dao.entity.Livre;

@WebServlet("/livres")
public class LivresServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private LivreService service;
	
	public LivresServlet() {
		service = new LivreServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		List<Livre> livres = service.findAll();		

		JSONArray livresArray = new JSONArray();
		for (Livre livre : livres) {
			livresArray.put(new JSONObject().put("id", livre.getId()));
			livresArray.put(new JSONObject().put("titre", livre.getTitre()));
			livresArray.put(new JSONObject().put("datePublication", livre.getDatePublication()));
			livresArray.put(new JSONObject().put("description", livre.getDescription()));
			livresArray.put(new JSONObject().put("categorie", livre.getCategorie()));
			
			Auteur auteur = livre.getAuteur();
			
			JSONArray auteurArray = new JSONArray();	
			auteurArray.put(new JSONObject().put("id", auteur.getId()));
			auteurArray.put(new JSONObject().put("nom", auteur.getNom()));
			auteurArray.put(new JSONObject().put("prenom", auteur.getPrenom()));
			auteurArray.put(new JSONObject().put("langue", auteur.getLangue()));			
			livresArray.put(auteurArray);		
			
			livresArray.put(new JSONObject().put("exemplaires", livre.getExemplaires()));
			livresArray.put(new JSONObject().put("exemplairesDispo", livre.getExemplairesDispo()));
		}
		
		response.getWriter().append(livresArray.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
