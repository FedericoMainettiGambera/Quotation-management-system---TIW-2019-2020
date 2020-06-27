<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="/quotationMenagementTIW2019-2020/CheckLogin/" method="post">
		<label for="username">Username</label><br> 
		<input type="text" name="username" id="username" placeholder="username" required autofocus autocomplete><br> 
		<label for="password">Password</label><br>
		<input type="password" name="password" id="password" placeholder="password" required autocomplete><br> 	
		<input type="submit" value="Submit">
	</form>
</body>
</html>