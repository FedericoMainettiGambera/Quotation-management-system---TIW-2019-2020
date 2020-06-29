package it.polimi.tiw.quotationsmenagment.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.polimi.tiw.quotationsmenagment.beans.User;

@WebServlet("/GoToLoginPage")
public class GoToLoginPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GoToLoginPage() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Checking for existing session.");
		HttpSession session = request.getSession(true);
		if(session.isNew() || session.getAttribute("user") == null) {
			System.out.println("User has no active session or hasn't logged in, forwarding to LoginPage.jsp.");
			//TODO i don't know why it isn't possible to forward to an HTML file.. so i'm using a JSP file
			String path = "/LoginPage.jsp"; 
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		}
		else { //user already logged in
			System.out.println("User has an active session: " + ((User)session.getAttribute("user")).toString());
			if (((User)session.getAttribute("user")).isClient()) { //checking role
				System.out.println("User is a client, redirecting to GoToClientHomePage");
				String path = "/quotationMenagementTIW2019-2020/GoToClientHomePage"; //TODO should move project root to Tomcat root
				response.sendRedirect(path);
			}
			else {
				System.out.println("User is an employee, redirecting to GoToEmployeeHomePage");
				String path = "/quotationMenagementTIW2019-2020/GoToEmployeeHomePage"; //TODO should move project root to Tomcat root
				response.sendRedirect(path);
			}
		}
	}
}
