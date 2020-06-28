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
		System.out.println("GoToEmployeeHomePage.doGet() just started");
		
		QuotationDAO quotationDAO = new QuotationDAO(connection);
		ArrayList<Quotation> employeeQuotations;
		try {
			employeeQuotations = quotationDAO.findAllByEmplyeeID(((User)request.getSession().getAttribute("user")).getID());
			request.setAttribute("employeeQuotations", employeeQuotations);
		} catch (SQLException e) {
			//TODO
			e.printStackTrace();
			response.sendError(505, "Can't find Employee's quotations");
			return;
		}
		ArrayList<Quotation> notManagedQuotations;
		try {
			notManagedQuotations = quotationDAO.findAllNotManaged();
			request.setAttribute("notManagedQuotations", notManagedQuotations);
		} catch (SQLException e) {
			//TODO
			e.printStackTrace();
			response.sendError(505, "Can't find not managed quotations");
			return;
		}		
		
		System.out.println("Retrived data from DB...");
		if(!employeeQuotations.isEmpty()) {
			System.out.println(" employeeQuotations:");
			for(int i = 0; i < employeeQuotations.size(); i++) {
				System.out.println("  Quotation " + i + ":\n" 
						+ "   clientName: " + employeeQuotations.get(i).getClientUsername() + "\n"
						+ "   price: " + employeeQuotations.get(i).getPrice() + "\n "
						+ "   product name: " + employeeQuotations.get(i).getProduct().getName());
				for(int j = 0; j< employeeQuotations.get(i).getProduct().getOptions().size(); j++) {
					System.out.println("   option " + j + ": " + employeeQuotations.get(i).getProduct().getOptions().get(j).getName() 
							+ ", " + employeeQuotations.get(i).getProduct().getOptions().get(j).getType());
				}
			}
		}
		else {
			System.out.println(" NO employeeQuotations found");
		}
		if(!notManagedQuotations.isEmpty()) {
			System.out.println(" notManagedQuotations:");
			for(int i = 0; i < notManagedQuotations.size(); i++) {
				System.out.println( "  Quotation " + i + ":\n" 
						+ "   clientName: " + notManagedQuotations.get(i).getClientUsername() + "\n"
						+ "   product name: " + notManagedQuotations.get(i).getProduct().getName());
				for(int j = 0; j< notManagedQuotations.get(i).getProduct().getOptions().size(); j++) {
					System.out.println("   option " + j + ": " + notManagedQuotations.get(i).getProduct().getOptions().get(j).getName() 
							+ ", " + notManagedQuotations.get(i).getProduct().getOptions().get(j).getType());
				}
			}
		}
		else {
			System.out.println(" No notmanagedQuotations found");
		}
		
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
