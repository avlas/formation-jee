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

@WebServlet("/auteurs")
public class AuteursServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private GenericService<Auteur> service;
	
	public AuteursServlet() {
		service = new GenericServiceImpl<Auteur>();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("application/json");
		
		List<Auteur> auteurs = service.findAll("from Auteur", Auteur.class);
		
		JSONObject auteursObj = new JSONObject();	
		
		JSONArray auteursArray = new JSONArray();	
		for (Auteur auteur : auteurs) {	
			JSONObject auteurObj = new JSONObject();
			
			auteurObj.put("id", auteur.getId());
			auteurObj.put("nom", auteur.getNom());
			auteurObj.put("prenom", auteur.getPrenom());
			auteurObj.put("langue", auteur.getLangue());
			
/*			JSONArray livresArray = new JSONArray();
			for (Livre livre : auteur.getLivres()) {
				livresArray.put(new JSONObject().put("id", livre.getId()));
				livresArray.put(new JSONObject().put("titre", livre.getTitre()));
				livresArray.put(new JSONObject().put("datePublication", livre.getDatePublication()));
				livresArray.put(new JSONObject().put("description", livre.getDescription()));
				livresArray.put(new JSONObject().put("categorie", livre.getCategorie()));
				livresArray.put(new JSONObject().put("exemplaires", livre.getExemplaires()));
				livresArray.put(new JSONObject().put("exemplairesDispo", livre.getExemplairesDispo()));
			}
			auteurObj.put("livres", livresArray);*/

			auteursArray.put(auteurObj);			
		}
		auteursObj.put("auteurs", auteursArray);
		
		response.getWriter().append(auteursObj.toString());
	}
}
