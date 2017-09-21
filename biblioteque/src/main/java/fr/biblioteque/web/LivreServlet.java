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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.biblioteque.business.LivreService;
import fr.biblioteque.business.LivreServiceImpl;
import fr.biblioteque.dao.entity.Auteur;
import fr.biblioteque.dao.entity.Livre;

@WebServlet(urlPatterns = { "/livre", "/livre/*" })
public class LivreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private LivreService service;

	public LivreServlet() {
		service = new LivreServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");

		Livre livre = service.findById(Integer.parseInt(request.getPathInfo().substring(1)));
		if (livre == null) {
			response.setStatus(404);
			response.getWriter().append("{\"status\": \" " + response.getStatus() + " \", \"description\" : \"Livreo not found\"}");
		} else {
			JSONObject livresJson = new JSONObject();
			
			JSONArray livresArray = new JSONArray();
			
			JSONObject livreJson = new JSONObject();			
			livreJson.put("id", livre.getId());
			livreJson.put("titre", livre.getTitre());
			livreJson.put("datePublication", livre.getDatePublication());
			livreJson.put("description", livre.getDescription());
			livreJson.put("categorie", livre.getCategorie());

			Auteur auteur = livre.getAuteur();
			
			JSONObject auteursJson = new JSONObject();
			JSONArray auteurArray = new JSONArray();			
			JSONObject auteurJson = new JSONObject();			
			auteurJson.put("id", auteur.getId());
			auteurJson.put("nom", auteur.getNom());
			auteurJson.put("prenom", auteur.getPrenom());
			auteurJson.put("langue", auteur.getLangue());			
			auteurArray.put(auteurJson);
			auteursJson.put("auteurs", auteurArray);
			
			livreJson.put("exemplaires", livre.getExemplaires());
			livreJson.put("exemplairesDispo", livre.getExemplairesDispo());

			livresArray.put(livreJson);
					
			livresJson.put("livres", livresArray);
			
			response.getWriter().append(livresJson.toString());			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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
		
		Auteur auteur = new Auteur(jsonObj.getString("nom"), jsonObj.getString("prenom"), jsonObj.getString("langue"), null);
		Livre livre = new Livre(jsonObj.getString("titre"), datePublication, jsonObj.getString("description"), jsonObj.getString("categorie"), auteur, jsonObj.getInt("exemplaires"), jsonObj.getInt("exemplairesDispo"));

		service.insert(livre);
		
		response.setContentType("application/json");
		response.setStatus(200);		
		response.getWriter().append("{\"status\": \" "+ response.getStatus() + " \", \"description\" : \"The auteur was created succesfully !\"}");

	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Livre livre = service.findById(Integer.parseInt(request.getPathInfo().substring(1)));
		if (livre == null) {
			response.setStatus(404);
			response.getWriter().append("{\"status\": \" " + response.getStatus() + " \", \"description\" : \"Livreo not found\"}");
		} else {
			
			String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

			JSONObject bodyJson = new JSONObject(body);
	
			Auteur auteur = new Auteur(bodyJson.getString("nom"), bodyJson.getString("prenom"), bodyJson.getString("langue"), null);

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
	
			service.update(livre);
			
			response.setStatus(200);		
			response.getWriter().append("{\"status\": \" "+ response.getStatus() + " \", \"description\" : \"The auteur was updated succesfully !\"}");		

		}
	}

}
