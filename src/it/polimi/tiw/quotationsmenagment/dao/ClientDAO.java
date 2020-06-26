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
	
	public Client findByID(int clientID) {
		Client clientBean = new Client();
		
		String query = "SELECT * FROM db_quotation_management.client WHERE ID = ?;";
		
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, clientID);
			try (ResultSet result = pstatement.executeQuery();) {
				if (result.next()) {
					clientBean.setUsername(result.getString("username"));
				}
			} catch (SQLException e) {
				// TODO
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO
			e.printStackTrace();
		}
	
		return clientBean;
	}
}
