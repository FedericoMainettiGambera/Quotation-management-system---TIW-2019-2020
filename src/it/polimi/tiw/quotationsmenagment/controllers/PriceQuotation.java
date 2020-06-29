package it.polimi.tiw.quotationsmenagment.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import it.polimi.tiw.quotationsmenagment.beans.Quotation;
import it.polimi.tiw.quotationsmenagment.beans.User;
import it.polimi.tiw.quotationsmenagment.dao.QuotationDAO;
import it.polimi.tiw.quotationsmenagment.utils.ConnectionHandler;

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
		
		String sPrice;
		String sQuotationID;
		sPrice = StringEscapeUtils.escapeJava(request.getParameter("price"));
		sQuotationID = StringEscapeUtils.escapeJava(request.getParameter("quotationID"));
		if (sPrice == null || sQuotationID == null || sQuotationID.isEmpty() || sPrice.isEmpty() ) {
			System.out.println("Parameters price or quotationID are empty or null.");
			response.sendError(505, "Incorrect price");
			return;
		}
		System.out.println("Parameter \"price\" is "  + sPrice + " and parameter \"quotationID\" is " + sQuotationID);
		int quotationID = Integer.parseInt(sQuotationID);
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
		if(quotation == null || quotation.getPrice()>=0) {
			response.sendError(505, "Incorrect parameters");
			return;
		}
		float fPrice = Float.parseFloat(sPrice);
		int price = (int) (fPrice * 100);
		try {
			System.out.println("Setting price " + price + " (in cents) for quotation: \n" + quotation.toString() );
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
