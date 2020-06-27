package it.polimi.tiw.quotationsmenagment.controlles;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import it.polimi.tiw.quotationsmenagment.utils.ConnectionHandler;
import it.polimi.tiw.quotationsmenagment.dao.ClientDAO;

@WebServlet("/CheckLogin")
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
		String usrn = null;
		String pwd = null;
		usrn = StringEscapeUtils.escapeJava(request.getParameter("username"));
		pwd = StringEscapeUtils.escapeJava(request.getParameter("pwd"));
		if (usrn == null || pwd == null || usrn.isEmpty() || pwd.isEmpty() ) {
			response.sendError(505, "Incorrect credentials");
			return;
		}

		ClientDAO clientDAO = new ClientDAO(connection);
		boolean validCredentials = false;
		try {
			validCredentials = clientDAO.checkCredentials(usrn, pwd);
		} catch (SQLException e) {
			response.sendError(505, "Internal server error");
			return;
		}

		if (!validCredentials) {
			response.sendError(505, "Incorrect credentials");
			return;
		} else {
			//TODO call the correct view
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
