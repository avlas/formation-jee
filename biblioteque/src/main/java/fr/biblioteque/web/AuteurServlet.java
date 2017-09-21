package fr.biblioteque.web;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import fr.biblioteque.business.GenericService;
import fr.biblioteque.business.GenericServiceImpl;
import fr.biblioteque.dao.entity.Auteur;

@WebServlet(urlPatterns = { "/auteur", "/auteur/*" })
public class AuteurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private GenericService<Auteur> auteurService;

	public AuteurServlet() {
		auteurService = new GenericServiceImpl<Auteur>();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");

		Auteur auteur = auteurService.findById(Auteur.class, Integer.parseInt(request.getPathInfo().substring(1)));
		if (auteur == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.getWriter()
					.append("{\"status\": \" " + response.getStatus() + " \", \"description\" : \"Auteur not found\"}");
		} else {
			JSONObject auteurJson = new JSONObject();

			auteurJson.put("id", auteur.getId());
			auteurJson.put("nom", auteur.getNom());
			auteurJson.put("prenom", auteur.getPrenom());
			auteurJson.put("langue", auteur.getLangue());

			response.getWriter().append(auteurJson.toString());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		JSONObject bodyJson = new JSONObject(body);

		String nom = null;
		try {
			nom = bodyJson.getString("nom");
		} catch (JSONException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append("{\"status\": \" " + response.getStatus()
					+ " \", \"description\" : \"You must give the parameter 'nom'! \"}");
		}

		String prenom = null;
		try {
			prenom = bodyJson.getString("prenom");
		} catch (JSONException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append("{\"status\": \" " + response.getStatus()
					+ " \", \"description\" : \"You must give the parameter 'prenom'! \"}");
		}

		String langue = null;
		try {
			langue = bodyJson.getString("langue");
		} catch (JSONException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append("{\"status\": \" " + response.getStatus()
					+ " \", \"description\" : \"You must give the parameter 'langue'! \"}");
		}

		if (nom.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append("{\"status\": \" " + response.getStatus()
					+ " \", \"description\" : \"You must give a value for the parameter 'nom'! \"}");
		} else if (langue.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append("{\"status\": \" " + response.getStatus()
					+ " \", \"description\" : \"You must give a value for the parameter 'langue'! \"}");
		} else if (prenom.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append("{\"status\": \" " + response.getStatus()
					+ " \", \"description\" : \"You must give a value for the parameter 'prenom'! \"}");
		} else {
			auteurService.insert(new Auteur(bodyJson.getString("nom"), bodyJson.getString("prenom"),
					bodyJson.getString("langue"), null));

			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_CREATED);
			response.getWriter().append("{\"status\": \" " + response.getStatus()
					+ " \", \"description\" : \"The auteur was created !\"}");
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");

		Auteur auteur = auteurService.findById(Auteur.class, Integer.parseInt(request.getPathInfo().substring(1)));
		if (auteur == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.getWriter()
					.append("{\"status\": \" " + response.getStatus() + " \", \"description\" : \"Auteur not found\"}");
		} else {

			String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			JSONObject bodyJson = new JSONObject(body);

			String nom = null;
			try {
				nom = bodyJson.getString("nom");
			} catch (JSONException e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().append("{\"status\": \" " + response.getStatus()
						+ " \", \"description\" : \"You must give the parameter 'nom'! \"}");
			}

			String prenom = null;
			try {
				prenom = bodyJson.getString("prenom");
			} catch (JSONException e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().append("{\"status\": \" " + response.getStatus()
						+ " \", \"description\" : \"You must give the parameter 'prenom'! \"}");
			}

			String langue = null;
			try {
				langue = bodyJson.getString("langue");
			} catch (JSONException e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().append("{\"status\": \" " + response.getStatus()
						+ " \", \"description\" : \"You must give the parameter 'langue'! \"}");
			}

			if (nom.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().append("{\"status\": \" " + response.getStatus()
						+ " \", \"description\" : \"You must give a value for the parameter 'nom'! \"}");
			} else if (prenom.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().append("{\"status\": \" " + response.getStatus()
						+ " \", \"description\" : \"You must give a value for the parameter 'prenom'! \"}");
			} else if (langue.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().append("{\"status\": \" " + response.getStatus()
						+ " \", \"description\" : \"You must give a value for the parameter 'langue'! \"}");
			} else {
				auteur.setNom(nom);
				auteur.setPrenom(prenom);
				auteur.setLangue(langue);

				auteurService.update(auteur);

				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().append("{\"status\": \" " + response.getStatus()
						+ " \", \"description\" : \"The auteur was updated !\"}");
			}
		}
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");

		Auteur auteur = auteurService.findById(Auteur.class, Integer.parseInt(request.getPathInfo().substring(1)));
		if (auteur == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.getWriter()
					.append("{\"status\": \" " + response.getStatus() + " \", \"description\" : \"Auteur not found\"}");
		} else {
			auteurService.delete(auteur);

			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().append("{\"status\": \" " + response.getStatus()
					+ " \", \"description\" : \"The auteur was deleted !\"}");
		}
	}
}
