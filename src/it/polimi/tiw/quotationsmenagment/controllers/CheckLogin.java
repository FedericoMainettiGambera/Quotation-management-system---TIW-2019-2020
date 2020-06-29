package it.polimi.tiw.quotationsmenagment.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import it.polimi.tiw.quotationsmenagment.utils.ConnectionHandler;
import it.polimi.tiw.quotationsmenagment.beans.User;
import it.polimi.tiw.quotationsmenagment.dao.ClientDAO;
import it.polimi.tiw.quotationsmenagment.dao.EmployeeDAO;

@WebServlet("/CheckLogin")
@MultipartConfig
public class CheckLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	
    public CheckLogin() {
        super();
    }

    public void init() throws ServletException {
		connection = ConnectionHandler.getConnection(getServletContext());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		System.out.println("CheckLogin.doPost() just started.");
		String usrn = null;
		String pwd = null;
		usrn = StringEscapeUtils.escapeJava(request.getParameter("username"));
		pwd = StringEscapeUtils.escapeJava(request.getParameter("password"));
		if (usrn == null || pwd == null || usrn.isEmpty() || pwd.isEmpty() ) {
			System.out.println("Parameters username or password (or both) are null or empty.");
			response.sendError(505, "Incorrect credentials");
			return;
		}
		System.out.println("Parameter username is "  + usrn + " and parameter password is " + pwd +".");
		System.out.println("Checking if parameters match a Client form DB.");
		ClientDAO clientDAO = new ClientDAO(connection);
		User userBean;
		try {
			userBean = clientDAO.checkCredentials(usrn, pwd);
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(505, "Internal server error");
			return;
		}
		
		if (userBean != null) { //CLIENT FOUND
			System.out.println("Correct credentials: CLIENT LOGGED IN.");
			request.getSession().setAttribute("user", userBean);
			System.out.println("User stored in session: " + userBean.toString());
			System.out.println("Redirecting to GoToClientHomePage");
			String path = "/quotationMenagementTIW2019-2020/GoToClientHomePage"; //TODO should move project root to Tomcat root
			response.sendRedirect(path);
		} 
		else {
			System.out.println("Incorrect credentials: NOT A CLIENT.");
			System.out.println("Checking if parameters match an Employee form DB.");
			EmployeeDAO employeeDAO = new EmployeeDAO(connection);
			try {
				userBean = employeeDAO.checkCredentials(usrn, pwd);
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendError(505, "Internal server error");
				return;
			}
			
			if (userBean != null) { //EMPLOYEE FOUND
				System.out.println("Correct credentials: EMPLOYEE LOGGED IN.");
				request.getSession().setAttribute("user", userBean);
				System.out.println("User stored in session: " + userBean.toString());
				System.out.println("Redirecting to GoToEmployeeHomePage");
				String path = "/quotationMenagementTIW2019-2020/GoToEmployeeHomePage"; //TODO should move project root to Tomcat root
				response.sendRedirect(path);
			}
			else {
				System.out.println("Incorrect credentials: NOT AN EMPLOYEE.");
				System.out.println("Redirecting to GoToLoginPage.");
				String path = "/quotationMenagementTIW2019-2020/GoToLoginPage"; //TODO should move project root to Tomcat root
				response.sendRedirect(path);
			}
		}
	}
	
	public void destroy() {
		try {
			ConnectionHandler.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
