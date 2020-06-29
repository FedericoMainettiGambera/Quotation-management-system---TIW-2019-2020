package it.polimi.tiw.quotationsmenagment.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.tiw.quotationsmenagment.beans.User;
import it.polimi.tiw.quotationsmenagment.dao.QuotationDAO;
import it.polimi.tiw.quotationsmenagment.utils.ConnectionHandler;

@WebServlet("/CreateQuotation")
public class CreateQuotation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;

    public CreateQuotation() {
        super();
    }

    public void init() throws ServletException {
		connection = ConnectionHandler.getConnection(getServletContext());
	}
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("CreateQuotation.doPost() just started.");
		
		Integer productSelectedID = null;
		try {
			productSelectedID = Integer.parseInt(request.getParameter("productSelectedID"));
		} catch (NumberFormatException | NullPointerException e) {
			e.printStackTrace();
			response.sendError(505, "Invalid parameters");
			return;
		}
		
		Map<String, String[]> parametersMap = request.getParameterMap();
		System.out.println("All parameters names are:");
		parametersMap.forEach((key,value) -> System.out.println("  " + key + ": [array lenght =" + value.length + "] first element is " + value[0])); 
		
		ArrayList<Integer> optionsID = new ArrayList<Integer>();
		
		for (Map.Entry<String, String[]> entry : parametersMap.entrySet()) {
		    if(entry.getKey().startsWith("option")) {
		    	Integer currentOptionSelectedID = null;
				try {
					currentOptionSelectedID = Integer.parseInt(entry.getValue()[0]);
				} catch (NumberFormatException | NullPointerException e) {
					e.printStackTrace();
					response.sendError(505, "Invalid parameters");
					return;
				}
		    	optionsID.add(currentOptionSelectedID);
		    }
		}
		
		System.out.println("Inserting new quotation in DB.");
		QuotationDAO quotationDAO = new QuotationDAO(connection);
		try {
			quotationDAO.createQuotation(productSelectedID, ((User)request.getSession().getAttribute("user")).getID(), optionsID);
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(505, "Internal server error");
			return;
		}
		
		System.out.println("Redirecting to GoToClientHomePage");
		String path = "/quotationMenagementTIW2019-2020/GoToClientHomePage"; //TODO should move project root to Tomcat root
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
