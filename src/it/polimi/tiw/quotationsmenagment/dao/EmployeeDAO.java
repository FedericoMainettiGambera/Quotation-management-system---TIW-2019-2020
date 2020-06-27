package it.polimi.tiw.quotationsmenagment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAO {
private Connection connection;
	
	public EmployeeDAO(Connection connection) {
		this.connection = connection;
	}
	
	public boolean checkCredentials(String username, String password) throws SQLException {
		boolean validCredentials = false;
		
		String query = "SELECT * FROM db_quotation_management.emplyee WHERE username = '?' AND password = '?';";
		
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setString(1, username);
			pstatement.setString(2, password);
			try (ResultSet result = pstatement.executeQuery();) {
				if (result.next()) {
					validCredentials = true;
				}
			}
		} 
	
		return validCredentials;
	}
}
