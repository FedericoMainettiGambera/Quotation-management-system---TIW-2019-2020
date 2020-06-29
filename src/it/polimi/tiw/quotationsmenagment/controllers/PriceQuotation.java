package it.polimi.tiw.quotationsmenagment.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.tiw.quotationsmenagment.beans.Quotation;
import it.polimi.tiw.quotationsmenagment.beans.User;
import it.polimi.tiw.quotationsmenagment.dao.QuotationDAO;
import it.polimi.tiw.quotationsmenagment.utils.ConnectionHandler;
import it.polimi.tiw.quotationsmenagment.utils.Money;

@WebServlet("/PriceQuotation")
public class PriceQuotation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;

    public PriceQuotation() {
        super();
    }

    public void init() throws ServletException {
		connection = ConnectionHandler.getConnection(getServletContext());
	}
    

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("PriceQuotation.doPost() just started.");
		
		Integer wholePart;
		Integer decimalPart;
		Integer quotationID;
		try {
			quotationID = Integer.parseInt(request.getParameter("quotationID"));
			System.out.println(request.getParameter("wholePart"));
			System.out.println(request.getParameter("decimalPart"));			
			wholePart = Integer.parseInt(request.getParameter("wholePart"));
			decimalPart = Integer.parseInt(request.getParameter("decimalPart"));
		}
		catch (NumberFormatException | NullPointerException e){
			e.printStackTrace();
			response.sendError(505, "Invalid parameters");
			return;
		}
		if(decimalPart >= 100 || decimalPart < 0 ) {
			response.sendError(505, "Invalid parameters");
			return;
		}
		
		Money price = new Money(wholePart,decimalPart);
		System.out.println("Parameter price (wholePart.decimalPart) is " + price.toString() + " and parameter quotationID is " + quotationID);
		
		QuotationDAO quotationDAO = new QuotationDAO(connection);
		Quotation quotation = null;
		System.out.println("Retriving data from DB.");
		try {
			quotation = quotationDAO.findByID(quotationID);
		} catch (SQLException e1) {
			e1.printStackTrace();
			response.sendError(505, "Internal server Error");
			return;
		}
		if(quotation == null || quotation.getPrice() != null) {
			//TODO maybe should redirect to the priceQuotationPage... or to the home.. or to a special error page that leads to the home
			response.sendError(505, "Incorrect parameters");
			return;
		}
		try {
			System.out.println("Setting price " + price.toString() + " for quotation: \n" + quotation.toString() );
			quotationDAO.priceQuotation(quotationID, price, ((User)request.getSession().getAttribute("user")).getID());
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(505, "Internal server Error");
			return;
		}
		
		System.out.println("Redirecting to GoToEmployeeHomePage");
		
		String path = "/quotationMenagementTIW2019-2020/GoToEmployeeHomePage"; //TODO should move project root to Tomcat root
		response.sendRedirect(path);
	}

	public void destroy() {
		try {
			ConnectionHandler.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
