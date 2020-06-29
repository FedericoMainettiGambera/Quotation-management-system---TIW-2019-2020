package it.polimi.tiw.quotationsmenagment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polimi.tiw.quotationsmenagment.beans.User;

public class ClientDAO {
	private Connection connection;
	
	public ClientDAO(Connection connection) {
		this.connection = connection;
	}
	
	public User findByID(int clientID) throws SQLException {
		User clientBean = new User();
		
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
	
	public User checkCredentials(String username, String password) throws SQLException {		
		User userBean = null;
		
		String query = "SELECT * FROM db_quotation_management.client WHERE username = ? AND password = ?;";
		
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setString(1, username);
			pstatement.setString(2, password);
			try (ResultSet result = pstatement.executeQuery();) {
				if (result.next()) {
					userBean = new User();
					userBean.setIsClient(true);
					userBean.setID(result.getInt("ID"));
					userBean.setUsername(result.getString("username"));
				}	
			}
		}
		return userBean; //might be null
	}
}
