<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="CSS/normalize.css">
<link rel="stylesheet" type="text/css" href="CSS/ProductSelectionStyle.css">
<link href="https://fonts.googleapis.com/css2?family=Palanquin+Dark:wght@700&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
</head>
<body>
        <div class="noHoveredContainer">
                <div class="noHoveredFlex">
                        <div class="productName">NO PRODUCT HOVERED</div>
                </div>
        </div>
        <div class="left"> <!-- left scrollable products images-->
                <div class="clientNameContainer"><!-- top-right fixed select a product-->
                        Select a product:
                </div>
                <div class="topMask"></div> <!-- top mask -->
                <div class="topGradient"></div> <!-- top gradient -->
                <div class="bottomGradient"></div> <!-- bottom gradient -->
                <div class="bottomMask"></div> <!-- bottom mask -->
                <div class="quotationsContainer"> 
                        <div class="spacer"></div> <!--spacer-->
                        <div class="productsContainer"> <!--products container--> 
                        	<c:forEach var="product" items="${products}">
                                <div class="productContainer"> <!--a product-->
                                        <c:url value="/Image" var="imageURL">
											<c:param name="productID" value="${product.ID}"/>
										</c:url>
										<img src="${imageURL}">
										<c:url value="/GoToOptionSelectionPage" var="optionSelectionURL"></c:url>
                                        <form class="chooseForm" action="<c:out value = "${optionSelectionURL}"/>" method="GET">
                                        		<input type="hidden" id="productSelected" name="productSelected" value="<c:out value="${product.ID}"/>">
                                        		<input type="hidden" id="productSelectedName" name="productSelectedName" value="<c:out value="${product.name}"/>">
                                                <input type="submit" value="CHOOSE" />
                                        </form>
                                        <div class="productInfoContainer">   <!-- productInfo -->
                                                <div class="productInfoFlex">
                                                        <div class="productName">
                                                                <c:out value="${product.name}"/>, ID: <c:out value="${product.ID}"/>
                                                        </div>
                                                        <div class="Lorem">Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime magnam placeat libero iste, repellendus obcaecati minus commodi debitis illum nostrum accusamus, doloremque dicta possimus corporis at exercitationem, reiciendis corrupti? Velit dolores, facilis earum officia culpa unde rerum minus harum eaque?</div>
                                                        <div class="buttonContainer"><!--new quotation button-->
                                                                <form action="#">
                                                                        <input type="submit" value="MORE INFO" />
                                                                </form>
                                                        </div>
                                                </div>
                                        </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="spacer"></div> <!--spacer-->
                        <div class="spacer"></div> <!--spacer-->
                </div>
        </div>
</body>
</html>

<!--
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
-->