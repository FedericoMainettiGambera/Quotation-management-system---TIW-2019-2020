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
	<h1>Welcome back! Your name is <c:out value="${user.username}" /></h1>
	<a href="/quotationMenagementTIW2019-2020/GoToProductSelectionPage">Create a new quotation!</a>
	<h2>List of previously managed quotations:</h2>
	<ul>
		<c:forEach var="quotation" items="${quotations}">
			<li> 
				<c:out value="${quotation.clientUsername}"/> has ordered a 
				<c:out value="${quotation.product.name}" /> with the following options:
				<c:forEach var="option" items="${quotation.product.options}">
					[<c:out value="${option.name}" />, <c:out value="${option.type}" />]
				</c:forEach>
				Price: <c:out value="${quotation.price.wholePart}" />.
					   <c:if test="${ managedQuotation.price.decimalPart <= 9 }">0</c:if><c:out value="${quotation.price.decimalPart}" />
				[image]
			</li>
		</c:forEach>
	</ul>
</body>
</html>