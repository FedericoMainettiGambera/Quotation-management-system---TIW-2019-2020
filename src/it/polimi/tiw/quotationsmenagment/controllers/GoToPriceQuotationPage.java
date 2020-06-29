package it.polimi.tiw.quotationsmenagment.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.tiw.quotationsmenagment.beans.Quotation;
import it.polimi.tiw.quotationsmenagment.dao.QuotationDAO;
import it.polimi.tiw.quotationsmenagment.utils.ConnectionHandler;

@WebServlet("/GoToPriceQuotationPage")
public class GoToPriceQuotationPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;

    public GoToPriceQuotationPage() {
        super();
    }
    
    public void init() throws ServletException {
		connection = ConnectionHandler.getConnection(getServletContext());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GoToPriceQuotationPage.doGet() just started.");
		Integer quotationID = null;
		try {
			quotationID = Integer.parseInt(request.getParameter("quotationID"));
		} catch (NumberFormatException | NullPointerException e) {
			e.printStackTrace();
			response.sendError(505, "Invalid parameter");
			return;
		}
		System.out.println("Request's parameter \"quotationID\" is: " + quotationID + ", searching quotation in DB.");
		QuotationDAO quotationDAO = new QuotationDAO(connection);
		Quotation quotationBean = null;
		try {
			quotationBean = quotationDAO.findByID(quotationID);
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(505, "Internal Server Error");
			return;
		}
		if(quotationBean == null || quotationBean.getPrice() != null) {
			response.sendError(505, "Invalid parameter");
			return;
		}
		request.setAttribute("quotation", quotationBean);
		System.out.println("Quotation found and atached to the request.\n" + quotationBean.toString());
		
		System.out.println("Forwarding to PriceQuotation.jsp");
		String path = "/PriceQuotation.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}
	
	public void destroy() {
		try {
			ConnectionHandler.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
