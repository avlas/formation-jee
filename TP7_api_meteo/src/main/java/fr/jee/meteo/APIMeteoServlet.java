package fr.jee.meteo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class APIMeteoServlet
 */
@WebServlet("/meteo")
public class APIMeteoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public APIMeteoServlet() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		// http://localhost:8080/TP7_api_meteo/meteo?city=Lyon
		String ville = request.getParameter("city");
		
		String host = "http://samples.openweathermap.org/data/2.5/";
		String endpoint = "weather";		
		//String ville = "Lyon";
		String apiKey = "b1b15e88fa797225412429c1c50c122a1";

		String requestUrl = host + endpoint + "?q=" + ville + "&appid=" + apiKey;
		
		InputStream httpContent = null;
		String resultJsonString = null;
		String line = null;

		
		// GET from API
		
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			
			HttpGet httpGetMethod = new HttpGet(requestUrl);
			HttpResponse httpResponse = httpClient.execute(httpGetMethod);

			int statusCode = httpResponse.getStatusLine().getStatusCode();
			System.out.println("Response Code : " + statusCode);

			HttpEntity httpEntity = httpResponse.getEntity();
			httpContent = httpEntity.getContent();
		} catch (Exception e) {
			// e.printStackTrace("Error in http connection" + e.toString());
		}

		// Convert API Response to String
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(httpContent, "UTF-8"));

			StringBuffer resultBuffer = new StringBuffer();

			while ((line = br.readLine()) != null) {
				resultBuffer.append(line);
			}

			httpContent.close();

			resultJsonString = resultBuffer.toString();

		} catch (Exception e) {
			// e.printStackTrace("Error converting result " + e.toString());
		}

		// Parsing data
		try {
			JSONObject resultJsonObject = new JSONObject(resultJsonString);

			JSONObject coordObject = resultJsonObject.getJSONObject("coord");
			System.out.println("coord = " + coordObject);
			System.out.println("coord.lon = " + coordObject.getDouble("lon"));
			System.out.println("coord.lat = " + coordObject.getDouble("lat"));

			JSONArray weatherArray = resultJsonObject.getJSONArray("weather");
			for (int i = 0; i < weatherArray.length(); i++) {
				
				JSONObject weatherObject = weatherArray.getJSONObject(i);
				System.out.println("id = " + weatherObject.getInt("id"));
				System.out.println("main = " + weatherObject.getString("main"));
				System.out.println("description = " + weatherObject.getString("description"));
				System.out.println("icon = " + weatherObject.getString("icon"));
			}

			System.out.println("base = " + resultJsonObject.getString("base"));

			JSONObject mainObject = resultJsonObject.getJSONObject("main");
			System.out.println("temp = " + mainObject.getDouble("temp"));
			System.out.println("pressure = " + mainObject.getInt("pressure"));
			System.out.println("humidity = " + mainObject.getInt("humidity"));
			System.out.println("temp_min = " + mainObject.getDouble("temp_min"));
			System.out.println("temp_max = " + mainObject.getDouble("temp_max"));

			System.out.println("visibility = " + resultJsonObject.getInt("visibility"));
		   
			JSONObject windObject = resultJsonObject.getJSONObject("wind");
			System.out.println("speed = " + windObject.getDouble("speed"));
			System.out.println("deg = " + windObject.getInt("deg"));
			
			JSONObject cloudsObject = resultJsonObject.getJSONObject("clouds");
			System.out.println("all = " + cloudsObject.getInt("all"));
			   
			System.out.println("dt = " + resultJsonObject.getInt("dt"));
		  
			JSONObject sysObject = resultJsonObject.getJSONObject("sys");
			System.out.println("type = " + sysObject.getInt("type"));
			System.out.println("id = " + sysObject.getInt("id"));
			System.out.println("message = " + sysObject.getDouble("message"));
			System.out.println("country = " + sysObject.getString("country"));
			System.out.println("sunrise = " + sysObject.getInt("sunrise"));
			System.out.println("sunset = " + sysObject.getInt("sunset"));
		    

			System.out.println("id = " + resultJsonObject.getInt("id"));
			System.out.println("name = " + resultJsonObject.getString("name"));
			System.out.println("cod = " + resultJsonObject.getInt("cod"));
			
			response.getWriter().append( Double.toString(mainObject.getDouble("temp")));
			
		} catch (ParseException e) {
			e.printStackTrace();
		}

	//	response.getWriter().append(resultJsonString);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
