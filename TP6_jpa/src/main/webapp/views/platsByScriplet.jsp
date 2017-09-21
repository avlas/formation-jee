<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="fr.jee.model.Plat" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Plats using Scriplets</title>
	</head>
	
	<body>
	 	<table border="1">
			<%  List<Plat> plats = (List<Plat>)request.getAttribute("plats");
				for (Plat plat : plats) { 
			%>
				<tr>
					<td> <%= plat.getId() %> </td>
					<td> <%= plat.getName() %></td>
					<td> <%= plat.getTarif() %> </td>
				</tr>
			<% } %>
		 </table>
	</body>

</html>