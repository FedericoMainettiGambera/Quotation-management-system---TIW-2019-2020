<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h2>Please <c:out value="${user.username}"/>, select at least one option for product <c:out value="${productSelectedName}"/></h2>
	<form action="/quotationMenagementTIW2019-2020/CreateQuotation/" method="post">
		<c:forEach var="option" items="${options}"> 
			<input type="checkbox" 
				   id="<c:out value="${option.ID}"/>" 
				   name="option<c:out value="${option.ID}" />" 
				   value="<c:out value="${option.ID}"/>">
  			<label for="option<c:out value="${option.ID}"/>"> <c:out value="${ option.name }"/>, <c:out value="${ option.type }"/> </label><br> 
		</c:forEach>
		<input type= "hidden" name="productSelectedID" value="<c:out value="${productSelectedID}"/>">
		<input type="submit" value="Submit">
	</form>
</body>
</html>