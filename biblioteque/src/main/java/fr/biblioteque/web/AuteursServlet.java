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

@WebServlet(urlPatterns = { "/auteurs", "/auteurs?*" })
public class AuteursServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private GenericService<Auteur> service;

	public AuteursServlet() {
		service = new GenericServiceImpl<Auteur>();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Auteur> auteurs = null;
		
		if (request.getQueryString() != null) {
			String url = request.getQueryString();
			System.out.println("URLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL" + url);

			String[] paramParts = url.split("&");
			for (String part : paramParts) {
				String[] parts = part.split("=");
				String key = parts[0];
				String value = parts[1];
				
				if(key.equalsIgnoreCase("lang")) {
					auteurs = service.findByLangue(value);
				}				
			}
		} else {
			auteurs = service.findAll("from Auteur", Auteur.class);
		}
		
		JSONObject auteursObj = new JSONObject();

		JSONArray auteursArray = new JSONArray();
		for (Auteur auteur : auteurs) {

			JSONObject auteurObj = new JSONObject();
			auteurObj.put("id", auteur.getId());
			auteurObj.put("nom", auteur.getNom());
			auteurObj.put("prenom", auteur.getPrenom());
			auteurObj.put("langue", auteur.getLangue());

			auteursArray.put(auteurObj);
		}
		auteursObj.put("auteurs", auteursArray);

		response.setContentType("application/json");
		response.getWriter().append(auteursObj.toString());
	}
}
