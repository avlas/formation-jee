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

@WebServlet(urlPatterns = { "/livres", "/livres?*" })
public class LivresServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private GenericService<Livre> service;

	public LivresServlet() {
		service = new GenericServiceImpl<Livre>();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Livre> livres = null;

		if (request.getQueryString() != null) {
			String url = request.getQueryString();
			System.out.println("URLLLLLLLLLLLLLLL = " + url);

			String[] paramParts = url.split("&");
			
			System.out.println("paramParts[0] = " + paramParts[0]);
			System.out.println("paramParts[1] = " + paramParts[1]);
			
			if (paramParts[0].startsWith("sort") && paramParts[1].startsWith("order")) {

				for (String part : paramParts) {
					String[] parts = part.split("=");
					String value = parts[1];

					livres = service.findOrderedByDateAsc(value);
				}
			} else {
				for (String part : paramParts) {
					String[] parts = part.split("=");
					String key = parts[0];
					String value = parts[1];

					if (key.equalsIgnoreCase("categ")) {
						livres = service.findByCategorie(value);
					}
				}
			}
		} else {
			livres = service.findAll("from Livre", Livre.class);
		}

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

		response.setContentType("application/json");
		response.getWriter().append(livresObj.toString());
	}

}
