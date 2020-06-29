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
	<h2>Client Data:</h2>
	<ul>
		<li>
			<c:out value="${quotation.clientUsername}"/>
		</li>
	</ul>
	<h2>Quotation Data:</h2>
	<ul>
		<li><c:out value="${quotation.product.name}" /></li>
		<c:forEach var="option" items="${quotation.product.options}">
			<li><c:out value="${option.name}" />, <c:out value="${option.type}" /></li>
		</c:forEach>
		<li>[image]</li>
	</ul>
	<h2>Price the quotations:</h2>
	<form action="/quotationMenagementTIW2019-2020/PriceQuotation/" method="post">
		<label for="price">Price</label><br> 
		<input type="number" name="price" id="price" placeholder="00.00" min="0" required autofocus><br>
		<input type="hidden" value="<c:out value="${quotation.ID }"/>" name="quotationID" id="quotationID" />
		<input type="submit" value="Submit">
	</form>
</body>
</html>