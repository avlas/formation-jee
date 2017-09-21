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

		Livre livre = livreService.findById(Livre.class, Integer.parseInt(request.getPathInfo().substring(1)));
		if (livre == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.getWriter()
					.append("{\"status\": \" " + response.getStatus() + " \", \"description\" : \"Livre not found\"}");
		} else {
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

			response.getWriter().append(livreObj.toString());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		
		String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

		JSONObject jsonObj = new JSONObject(body);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/aaaa");
		Date datePublication = null;
		try {
			datePublication = sdf.parse(jsonObj.getString("datePublication"));
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Auteur auteur = auteurService.findById(Auteur.class, Integer.valueOf(jsonObj.getInt("auteur")));
		if (auteur == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.getWriter()
					.append("{\"status\": \" " + response.getStatus() + " \", \"description\" : \"Auteur not found\"}");
		} else {
			Livre livre = new Livre(jsonObj.getString("titre"), datePublication, jsonObj.getString("description"),
					jsonObj.getString("categorie"), auteur, jsonObj.getInt("exemplaires"),
					jsonObj.getInt("exemplairesDispo"));

			livreService.insert(livre);

			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_CREATED);
			response.getWriter().append("{\"status\": \" " + response.getStatus()
					+ " \", \"description\" : \"Livre was created !\"}");
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("application/json");
		
		Livre livre = livreService.findById(Livre.class, Integer.parseInt(request.getPathInfo().substring(1)));
		if (livre == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.getWriter()
					.append("{\"status\": \" " + response.getStatus() + " \", \"description\" : \"Livre not found\"}");
		} else {

			String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

			JSONObject bodyJson = new JSONObject(body);

			Auteur auteur = auteurService.findById(Auteur.class, Integer.valueOf(bodyJson.getInt("auteur")));
			if (auteur == null) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				response.getWriter()
						.append("{\"status\": \" " + response.getStatus() + " \", \"description\" : \"Auteur not found\"}");
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/aaaa");
				Date datePublication = null;
				try {
					datePublication = sdf.parse(bodyJson.getString("datePublication"));
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				livre.setTitre(bodyJson.getString("titre"));
				livre.setDatePublication(datePublication);
				livre.setDescription(bodyJson.getString("description"));
				livre.setCategorie(bodyJson.getString("categorie"));
				livre.setAuteur(auteur);
				livre.setExemplaires(bodyJson.getInt("exemplaires"));
				livre.setExemplairesDispo(bodyJson.getInt("exemplairesDispo"));

				livreService.update(livre);

				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().append("{\"status\": \" " + response.getStatus()
						+ " \", \"description\" : \"Livre was updated !\"}");

			}
		}
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");

		Livre livre = livreService.findById(Livre.class, Integer.parseInt(request.getPathInfo().substring(1)));
		if (livre == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.getWriter()
					.append("{\"status\": \" " + response.getStatus() + " \", \"description\" : \"Livre not found\"}");
		} else {
			livreService.delete(livre);

			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().append("{\"status\": \" " + response.getStatus()
					+ " \", \"description\" : \"Livre was deleted !\"}");
		}
	}

}
