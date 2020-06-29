package it.polimi.tiw.quotationsmenagment.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import it.polimi.tiw.quotationsmenagment.beans.Option;
import it.polimi.tiw.quotationsmenagment.dao.ProductDAO;
import it.polimi.tiw.quotationsmenagment.utils.ConnectionHandler;

@WebServlet("/GoToOptionSelectionPage")
public class GoToOptionSelectionPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;

    public GoToOptionSelectionPage() {
        super();
    }

    public void init() throws ServletException {
		connection = ConnectionHandler.getConnection(getServletContext());
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GoToOptionSelectionPage.doGet() just started.");
		
		
		Integer productSelectedID = null;
		try {
			productSelectedID = Integer.parseInt(request.getParameter("productSelected"));
		} catch (NumberFormatException | NullPointerException e) {
			e.printStackTrace();
			response.sendError(505, "Invalid parameters");
			return;
		}
		System.out.println("Parameter productSelectedID is: " + productSelectedID);
		
		String productSelectedName = StringEscapeUtils.escapeJava(request.getParameter("productSelectedName"));
		if(productSelectedName == null || productSelectedName.isEmpty()) {
			System.out.println("Invalid or missing parameter \"productSelectedName\"");
			response.sendError(505, "Invalid parameters");
			return;
		}
		System.out.println("Parameter productSelectedName is: " + productSelectedName);
		
		System.out.println("Retriving data fom DB.");
		ProductDAO productDAO = new ProductDAO(connection);
		ArrayList<Option> options = null;
		try {
			options = productDAO.findAvailableOptionsForProduct(productSelectedID);
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(505, "Internal server error");
			return;
		}
		System.out.println("Options available are:");
		if(options.isEmpty()) {
			System.out.println("  There are no options available");
		}
		else {
			for(int i = 0; i< options.size(); i++) {
				System.out.println("  " + options.get(i).toString());
			}
		}
		System.out.println("Adding options, productSelectedID and productSelectedName to request.");
		request.setAttribute("options", options);
		request.setAttribute("productSelectedName", productSelectedName);
		request.setAttribute("productSelectedID", productSelectedID);
		
		System.out.println("Forwarding to OptionSelection.jsp");
		String path = "/OptionSelection.jsp";
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
