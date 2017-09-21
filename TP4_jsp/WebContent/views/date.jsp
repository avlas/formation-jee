<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Date</title>
	</head>
	
	<body>
		<div>Date from SERVER :  <%= request.getAttribute("date") %> <div>
		<div>Date from CLIENT :  <%= java.util.Calendar.getInstance().getTime() %> <div>
	</body>
	</body>

</html>