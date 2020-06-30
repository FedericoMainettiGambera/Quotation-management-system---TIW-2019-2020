<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
img {
	max-width: 50px;
	max-height: 50px;
}
</style>
</head>
<body>
	<h2>Please, select a product:</h2>
	<ul>
		<c:forEach var="product" items="${products}">
			<li> 
				<c:url value="/GoToOptionSelectionPage" var="optionSelectionURL">
					<c:param name="productSelected" value="${product.ID}"/>
					<c:param name="productSelectedName" value="${product.name}"/>
				</c:url>
				<a href="<c:out value = "${optionSelectionURL}"/>">
					<c:out value="${product.name}"/>
				</a>
				<c:url value="/Image" var="imageURL">
					<c:param name="productID" value="${product.ID}"/>
				</c:url>
				<img src="${imageURL}">
			</li>
		</c:forEach>
	</ul>
</body>
</html>