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

import it.polimi.tiw.quotationsmenagment.beans.Quotation;
import it.polimi.tiw.quotationsmenagment.beans.User;
import it.polimi.tiw.quotationsmenagment.dao.QuotationDAO;
import it.polimi.tiw.quotationsmenagment.utils.ConnectionHandler;

@WebServlet("/GoToEmployeeHomePage")
public class GoToEmployeeHomePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
       
    public GoToEmployeeHomePage() {
        super();
    }
    
    public void init() throws ServletException {
		connection = ConnectionHandler.getConnection(getServletContext());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GoToEmployeeHomePage.doGet() just started.");
		System.out.println("Retriving data from DB.");
		QuotationDAO quotationDAO = new QuotationDAO(connection);
		ArrayList<Quotation> employeeQuotations;
		try {
			employeeQuotations = quotationDAO.findAllByEmplyeeID(((User)request.getSession().getAttribute("user")).getID());
			request.setAttribute("employeeQuotations", employeeQuotations);
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(505, "Internal server error");
			return;
		}
		ArrayList<Quotation> notManagedQuotations;
		try {
			notManagedQuotations = quotationDAO.findAllNotManaged();
			request.setAttribute("notManagedQuotations", notManagedQuotations);
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(505, "Internal server error");
			return;
		}		
		
		System.out.println("Stored data in request:");
		if(!employeeQuotations.isEmpty()) {
			System.out.println("1) employeeQuotations:");
			for(int i = 0; i< employeeQuotations.size(); i++) {
				System.out.println(employeeQuotations.get(i).toString());
			}
		}
		else {
			System.out.println("1) employeeQuotations: EMPTY");
		}
		if(!notManagedQuotations.isEmpty()) {
			System.out.println("2) notManagedQuotations:");
			for(int i = 0; i< notManagedQuotations.size(); i++) {
				System.out.println(notManagedQuotations.get(i).toString());
			}
		}
		else {
			System.out.println("2) notManagedQuotations: EMPTY");
		}
		
		System.out.println("Forwarding to EmployeeHome.jsp");
		
		String path = "/EmployeeHome.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void destroy() {
		try {
			ConnectionHandler.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
