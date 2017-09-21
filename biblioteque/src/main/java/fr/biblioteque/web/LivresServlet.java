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

import fr.biblioteque.business.GenericService;
import fr.biblioteque.business.GenericServiceImpl;
import fr.biblioteque.dao.entity.Auteur;
import fr.biblioteque.dao.entity.Livre;

@WebServlet("/livres")
public class LivresServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private GenericService<Livre> service;
	
	public LivresServlet() {
		service = new GenericServiceImpl<Livre>();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		List<Livre> livres = service.findAll("from Livre", Livre.class);		
			
		JSONObject livresObj = new JSONObject();	
			
		JSONArray livresArray = new JSONArray();
		for (Livre livre : livres) {
			JSONObject livreObj = new JSONObject();			
		
			livreObj.put("id", livre.getId());
			livreObj.put("titre", livre.getTitre());
			livreObj.put("datePublication", livre.getDatePublication());
			livreObj.put("description", livre.getDescription());
			livreObj.put("categorie", livre.getCategorie());
			
			Auteur auteur = livre.getAuteur();
			
			JSONObject auteurJson = new JSONObject();	
			auteurJson.put("id", auteur.getId());
			auteurJson.put("nom", auteur.getNom());
			auteurJson.put("prenom", auteur.getPrenom());
			auteurJson.put("langue", auteur.getLangue());			
			
			livreObj.put("auteur", auteurJson);
			livreObj.put("exemplaires", livre.getExemplaires());
			livreObj.put("exemplairesDispo", livre.getExemplairesDispo());
			
			livresArray.put(livreObj);	
		}
		livresObj.put("livres", livresArray);
		response.getWriter().append(livresObj.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
