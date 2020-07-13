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

import it.polimi.tiw.quotationsmenagment.beans.Product;
import it.polimi.tiw.quotationsmenagment.dao.ProductDAO;
import it.polimi.tiw.quotationsmenagment.utils.ConnectionHandler;

@WebServlet("/GoToProductSelectionPage")
public class GoToProductSelectionPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;

    public GoToProductSelectionPage() {
        super();
    }

    public void init() throws ServletException {
		connection = ConnectionHandler.getConnection(getServletContext());
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GoToProductSelectionPage.doGet() just started.");
		
		ProductDAO productDAO = new ProductDAO(connection);
		ArrayList<Product> products = null;
		try {
			products = productDAO.findAllWithoutOptions();
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(505, "Internal server error");
			return;
		}
		
		request.setAttribute("products", products);
		
		String path = "/ProductSelection.jsp";
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
