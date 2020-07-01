<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Client Home Page</title>
<link rel="stylesheet" type="text/css" href="CSS/normalize.css">
<link rel="stylesheet" type="text/css" href="CSS/ClientHomeStyle.css">
<link href="https://fonts.googleapis.com/css2?family=Palanquin+Dark:wght@700&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
</head>
<body>
        <div class="right"> <!-- right fixed create quotation seciton -->
                <div>
                        <div class="Lorem">Lorem ipsum dolor sit, amet consectetur adipisicing elit. Praesentium velit similique dicta hic? Molestias saepe ipsam non unde numquam fugiat?</div>
                        <div class="buttonContainer"><!--new quotation button-->
                                <form action="/quotationMenagementTIW2019-2020/GoToProductSelectionPage">
                                        <input type="submit" value="NEW QUOTATION" />
                                </form>
                        </div>
                </div>
        </div>   
        <div class="left"> <!-- left scrollable client's quotation section-->
                <div class="clientNameContainer"><!-- top-right fixed clientname's quotation-->
                        <c:out value="${user.username}" />'s quotations:
                </div>
                <div class="topMask"></div> <!-- top mask -->
                <div class="topGradient"></div> <!-- top gradient -->
                <div class="bottomGradient"></div> <!-- bottom gradient -->
                <div class="bottomMask"></div> <!-- bottom mask -->
                <div class="quotationsContainer">  
                        <div class="spacer"></div> <!--to space the first quotation-->
                        <c:forEach var="quotation" items="${quotations}">
                        <div class="quotationContainer"> <!--a quotation-->
                                <div class="quotationImage"> <!-- quotation image -->
                                		<c:url value="/Image" var="imageURL">
											<c:param name="productID" value="${quotation.product.ID}"/>
										</c:url>
										<img src="${imageURL}">
                                </div>
                                <div class="quotationInfo"> <!-- quotation info -->
                                        <div class="quotationName"> <!-- quotation name -->
                                                <span><c:out value="${quotation.product.name}" /></span>
                                        </div>
                                        <div class="options"> <!-- quotation options-->
                                                <div> <!-- option Label-->
                                                        <span class="optionLabel">Options:</span>
                                                </div>
                                                <c:forEach var="option" items="${quotation.product.options}">
                                                <div class="<c:out value="${option.type}" />Div"> <!-- option-->
                                                		<span class="<c:out value="${option.type}"/>Text">promotion</span>
                                                        <span class="option <c:out value="${option.type}" />Option"><c:out value="${option.name}" /></span>
                                                </div>
                                                </c:forEach>
                                 				<c:if test="${ quotation.price == null }">
                                 					Price: waiting to be quoted...
                                 				</c:if>
                                 				<c:if test="${ quotation.price != null }">
                                 					Price: <c:out value="${quotation.price.wholePart}" />.
					   								<c:if test="${quotation.price.decimalPart <= 9 }">0</c:if><c:out value="${quotation.price.decimalPart}" />
                                 				</c:if>
                                                
                                        </div>
                                </div>
                        </div>
                        </c:forEach>
                        <div class="spacer"></div> <!--to space the last quotation-->
                        <div class="spacer"></div> <!--to space the first quotation-->
                </div>
        </div>
</body>
</html>