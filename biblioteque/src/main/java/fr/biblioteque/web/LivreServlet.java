package fr.biblioteque.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import fr.biblioteque.dao.entity.Livre;

@WebServlet(urlPatterns = { "/livre", "/livre/*" })
public class LivreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private GenericService<Livre> livreService;
	private GenericService<Auteur> auteurService;

	public LivreServlet() {
		livreService = new GenericServiceImpl<Livre>();
		auteurService = new GenericServiceImpl<Auteur>();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");

		String livreIdFromUrl = request.getPathInfo().substring(1);		
		Livre livre = livreService.findById(Livre.class, Integer.parseInt(livreIdFromUrl));
		if (livre == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.getWriter()
					.append("{\"status\": \"" + response.getStatus() + "\", \"description\" : \"Livre not found\"}");
		} else {
			JSONObject livreJsonObj = new JSONObject();
			livreJsonObj.put("id", livre.getId());
			livreJsonObj.put("titre", livre.getTitre());			

			Date datePublication = livre.getDatePublication();
			
			String datePublicationStr = null;
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {
				datePublicationStr = sdf.format(datePublication);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			livreJsonObj.put("datePublication", datePublicationStr);
			
			livreJsonObj.put("description", livre.getDescription());
			livreJsonObj.put("categorie", livre.getCategorie());

			Auteur auteur = livre.getAuteur();

			JSONObject auteurJsonObj = new JSONObject();
			auteurJsonObj.put("id", auteur.getId());
			auteurJsonObj.put("nom", auteur.getNom());
			auteurJsonObj.put("prenom", auteur.getPrenom());
			auteurJsonObj.put("langue", auteur.getLangue());

			livreJsonObj.put("auteur", auteurJsonObj);
			livreJsonObj.put("exemplaires", livre.getExemplaires());
			livreJsonObj.put("exemplairesDispo", livre.getExemplairesDispo());

			response.getWriter().append(livreJsonObj.toString());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");

		// recuperer le BODY, qui contient les parametres a inserer
		String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

		// transformer en JSON
		JSONObject bodyJsonObj = new JSONObject(body);

		String titre = null;
		try {
			titre = bodyJsonObj.getString("titre");
		} catch (JSONException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append("{\"status\": \"" + response.getStatus()
					+ "\", \"description\" : \"You must give the parameter 'titre' ! \"}");
		}

		String datePublicationStr = null;
		try {
			datePublicationStr = bodyJsonObj.getString("datePublication");
		} catch (JSONException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append("{\"status\": \"" + response.getStatus()
					+ "\", \"description\" : \"You must give the parameter 'datePublication'! \"}");
		}

		String description = null;
		try {
			description = bodyJsonObj.getString("description");
		} catch (JSONException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append("{\"status\": \"" + response.getStatus()
					+ "\", \"description\" : \"You must give the parameter 'description'! \"}");
		}

		String categorie = null;
		try {
			categorie = bodyJsonObj.getString("categorie");
		} catch (JSONException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append("{\"status\": \"" + response.getStatus()
					+ "\", \"description\" : \"You must give the parameter 'categorie'! \"}");
		}

		int auteurId = 0;
		try {
			auteurId = bodyJsonObj.getInt("auteur");
		} catch (JSONException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append("{\"status\": \"" + response.getStatus()
					+ "\", \"description\" : \"You must give the parameter 'auteur id'! \"}");
		}

		int exemplaires = 0;
		try {
			exemplaires = bodyJsonObj.getInt("exemplaires");
		} catch (JSONException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append("{\"status\": \"" + response.getStatus()
					+ "\", \"description\" : \"You must give the parameter 'exemplaires'! \"}");
		}

		int exemplairesDispo = 0;
		try {
			exemplairesDispo = bodyJsonObj.getInt("exemplairesDispo");
		} catch (JSONException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append("{\"status\": \"" + response.getStatus()
					+ "\", \"description\" : \"You must give the parameter 'exemplairesDispo'! \"}");
		}

		if (titre.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append("{\"status\": \"" + response.getStatus()
					+ "\", \"description\" : \"You must give a value for the parameter 'titre'! \"}");
		} else if (datePublicationStr.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append("{\"status\": \"" + response.getStatus()
					+ "\", \"description\" : \"You must give a value for the parameter 'datePublication'! \"}");
		} else if (description.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append("{\"status\": \"" + response.getStatus()
					+ "\", \"description\" : \"You must give a value for the parameter 'description'! \"}");
		} else if (categorie.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append("{\"status\": \"" + response.getStatus()
					+ "\", \"description\" : \"You must give a value for the parameter 'categorie'! \"}");
		} else if (auteurId == 0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append("{\"status\": \"" + response.getStatus()
					+ "\", \"description\" : \"You must give a value for the parameter 'auteur'! \"}");
		} else if (exemplaires == 0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append("{\"status\": \"" + response.getStatus()
					+ "\", \"description\" : \"You must give a value for the parameter 'exemplaires'! \"}");
		} else if (exemplairesDispo == 0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append("{\"status\": \"" + response.getStatus()
					+ "\", \"description\" : \"You must give a value for the parameter 'exemplairesDispo'! \"}");
		} else {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date datePublication = null;
			try {
				datePublication = sdf.parse(datePublicationStr);
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}

			Auteur auteur = auteurService.findById(Auteur.class, Integer.valueOf(auteurId));
			if (auteur == null) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				response.getWriter().append(
						"{\"status\": \"" + response.getStatus() + "\", \"description\" : \"Auteur not found\"}");
			} else {
				Livre livre = new Livre(titre, datePublication, description, categorie, auteur, exemplaires,
						exemplairesDispo);

				livreService.insert(livre);

				response.setStatus(HttpServletResponse.SC_CREATED);

				response.getWriter().append(
						"{\"status\": \"" + response.getStatus() + "\", \"description\" : \"Livre was created !\"}");
			}
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");

		Livre livre = livreService.findById(Livre.class, Integer.parseInt(request.getPathInfo().substring(1)));
		if (livre == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.getWriter()
					.append("{\"status\": \"" + response.getStatus() + "\", \"description\" : \"Livre not found\"}");
		} else {

			String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

			JSONObject bodyJsonObj = new JSONObject(body);

			String titre = null;
			try {
				titre = bodyJsonObj.getString("titre");
			} catch (JSONException e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().append("{\"status\": \"" + response.getStatus()
						+ "\", \"description\" : \"You must give the parameter 'titre'! \"}");
			}

			String datePublicationStr = null;
			try {
				datePublicationStr = bodyJsonObj.getString("datePublication");
			} catch (JSONException e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().append("{\"status\": \"" + response.getStatus()
						+ "\", \"description\" : \"You must give the parameter 'datePublication'! \"}");
			}

			String description = null;
			try {
				description = bodyJsonObj.getString("description");
			} catch (JSONException e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().append("{\"status\": \"" + response.getStatus()
						+ "\", \"description\" : \"You must give the parameter 'description'! \"}");
			}

			String categorie = null;
			try {
				categorie = bodyJsonObj.getString("categorie");
			} catch (JSONException e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().append("{\"status\": \"" + response.getStatus()
						+ "\", \"description\" : \"You must give the parameter 'categorie'! \"}");
			}

			int auteurId = 0;
			try {
				auteurId = bodyJsonObj.getInt("auteur");
			} catch (JSONException e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().append("{\"status\": \"" + response.getStatus()
						+ "\", \"description\" : \"You must give the parameter 'auteur'! \"}");
			}

			int exemplaires = 0;
			try {
				exemplaires = bodyJsonObj.getInt("exemplaires");
			} catch (JSONException e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().append("{\"status\": \"" + response.getStatus()
						+ "\", \"description\" : \"You must give the parameter 'exemplaires'! \"}");
			}

			int exemplairesDispo = 0;
			try {
				exemplairesDispo = bodyJsonObj.getInt("exemplairesDispo");
			} catch (JSONException e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().append("{\"status\": \"" + response.getStatus()
						+ "\", \"description\" : \"You must give the parameter 'exemplairesDispo'! \"}");
			}

			if (titre.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().append("{\"status\": \"" + response.getStatus()
						+ "\", \"description\" : \"You must give a value for the parameter 'titre'! \"}");
			} else if (datePublicationStr.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().append("{\"status\": \"" + response.getStatus()
						+ "\", \"description\" : \"You must give a value for the parameter 'datePublication'! \"}");
			} else if (description.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().append("{\"status\": \"" + response.getStatus()
						+ "\", \"description\" : \"You must give a value for the parameter 'description'! \"}");
			} else if (categorie.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().append("{\"status\": \"" + response.getStatus()
						+ "\", \"description\" : \"You must give a value for the parameter 'categorie'! \"}");
			} else if (auteurId == 0) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().append("{\"status\": \"" + response.getStatus()
						+ "\", \"description\" : \"You must give a value for the parameter 'auteur'! \"}");
			} else if (exemplaires == 0) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().append("{\"status\": \"" + response.getStatus()
						+ "\", \"description\" : \"You must give a value for the parameter 'exemplaires'! \"}");
			} else if (exemplairesDispo == 0) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().append("{\"status\": \"" + response.getStatus()
						+ "\", \"description\" : \"You must give a value for the parameter 'exemplairesDispo'! \"}");
			} else {
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date datePublication = null;
				try {
					datePublication = sdf.parse(datePublicationStr);
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}

				Auteur auteur = auteurService.findById(Auteur.class, Integer.valueOf(auteurId));
				if (auteur == null) {
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
					response.getWriter().append(
							"{\"status\": \"" + response.getStatus() + "\", \"description\" : \"Auteur not found\"}");
				} else {
					livre.setTitre(titre);
					livre.setDatePublication(datePublication);
					livre.setDescription(description);
					livre.setCategorie(categorie);
					livre.setAuteur(auteur);
					livre.setExemplaires(exemplaires);
					livre.setExemplairesDispo(exemplairesDispo);

					livreService.update(livre);

					response.setStatus(HttpServletResponse.SC_OK);

					response.getWriter().append("{\"status\": \"" + response.getStatus()
							+ "\", \"description\" : \"Livre was updated ! \"}");
				}
			}
		}
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");

		String livreIdFromUrl = request.getPathInfo().substring(1);
		
		Livre livre = livreService.findById(Livre.class, Integer.parseInt(livreIdFromUrl));
		if (livre == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.getWriter()
					.append("{\"status\": \"" + response.getStatus() + "\", \"description\" : \"Livre not found\"}");
		} else {
			livreService.delete(livre);

			response.setStatus(HttpServletResponse.SC_OK);

			response.getWriter().append(
					"{\"status\": \"" + response.getStatus() + "\", \"description\" : \"Livre was deleted !\"}");
		}
	}

}
