package it.polimi.tiw.quotationsmenagment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polimi.tiw.quotationsmenagment.beans.Client;

public class ClientDAO {
	private Connection connection;
	
	public ClientDAO(Connection connection) {
		this.connection = connection;
	}
	
	public Client findByID(int clientID) throws SQLException {
		Client clientBean = new Client();
		
		String query = "SELECT * FROM db_quotation_management.client WHERE ID = ?;";
		
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, clientID);
			try (ResultSet result = pstatement.executeQuery();) {
				if (result.next()) {
					clientBean.setUsername(result.getString("username"));
				}
			} 
		}
	
		return clientBean;
	}
	
	public boolean checkCredentials(String username, String password) throws SQLException {
		System.out.println("Checking credentials.");
		
		boolean validCredentials = false;
		
		String query = "SELECT * FROM db_quotation_management.client WHERE username = ? AND password = ?;";
		
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			System.out.println("Prepared query: " + query);
			pstatement.setString(1, username);
			pstatement.setString(2, password);
			System.out.println("where parameter 1 is " + username + "; parameter 2 is " + password);
			try (ResultSet result = pstatement.executeQuery();) {
				System.out.println("Query executed.");
				if (result.next()) {
					System.out.println("Parsing result.");
					validCredentials = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		System.out.println("calidCredentials: " + validCredentials);
		return validCredentials;
	}
}
