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
	<h2>List of previously managed quotations:</h2>
	<ul>
		<c:forEach var="managedQuotation" items="${employeeQuotations}">
			<li> 
				<c:out value="${managedQuotation.clientUsername}"/> has ordered a 
				<c:out value="${managedQuotation.product.name}" /> with the following options:
				<c:forEach var="option" items="${managedQuotation.product.options}">
					[<c:out value="${option.name}" />, <c:out value="${option.type}" />]
				</c:forEach>
				. Price: <c:out value="${managedQuotation.price}" />
				[image]
			</li>
		</c:forEach>
	</ul>
	<h2>List of quotations to be managed:</h2>
	<ul>
		<c:forEach var="notManagedQuotation" items="${notManagedQuotations}">
			<li> 
				<c:out value="${notManagedQuotation.clientUsername}"/> has ordered a 
				<c:out value="${notManagedQuotation.product.name}" /> with the following options:
				<c:forEach var="option" items="${notManagedQuotation.product.options}">
					[<c:out value="${option.name}" />, <c:out value="${option.type}" />]
				</c:forEach>
				. [image]
			</li>
		</c:forEach>
	</ul>
</body>
</html>