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
		try {
			usrn = StringEscapeUtils.escapeJava(request.getParameter("username"));
			pwd = StringEscapeUtils.escapeJava(request.getParameter("password"));
		}
		catch (NullPointerException e){
			e.printStackTrace();
			response.sendError(505, "Invalid parameters");
			return;
		}
		if (usrn == null || pwd == null || usrn.isEmpty() || pwd.isEmpty() ) {
			response.sendError(505, "Incorrect credentials");
			return;
		}
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
			request.getSession().setAttribute("user", userBean);
			String path = "/quotationMenagementTIW2019-2020/GoToClientHomePage"; 
			response.sendRedirect(path);
		} 
		else {
			EmployeeDAO employeeDAO = new EmployeeDAO(connection);
			try {
				userBean = employeeDAO.checkCredentials(usrn, pwd);
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendError(505, "Internal server error");
				return;
			}
			
			if (userBean != null) { //EMPLOYEE FOUND
				request.getSession().setAttribute("user", userBean);
				String path = "/quotationMenagementTIW2019-2020/GoToEmployeeHomePage"; 
				response.sendRedirect(path);
			}
			else {
				String path = "/quotationMenagementTIW2019-2020/GoToLoginPage"; 
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
