package it.polimi.tiw.quotationsmenagment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import it.polimi.tiw.missions.beans.MissionStatus;
import it.polimi.tiw.quotationsmenagment.beans.Client;
import it.polimi.tiw.quotationsmenagment.beans.Option;
import it.polimi.tiw.quotationsmenagment.beans.Product;
import it.polimi.tiw.quotationsmenagment.beans.Quotation;

public class QuotationDAO {
	private Connection connection;
	
	public QuotationDAO(Connection connection) {
		this.connection = connection;
	}
	
	public ArrayList<Quotation> findAllByClientID(int clientID) throws SQLException {
		
		//TODO re-do with separated queries
		//==> this.getOptionsSelected()
		
		//returned object
		ArrayList<Quotation> quotations = new ArrayList<Quotation>();
		
		String query = "SELECT " +
				"   Q.ID AS quotationID, " +
				"   Q.price, " + 
				"	C.username AS clientusername, " + 
				"	P.name AS productname, " + 
				"	P.image, " + 
				"   O.name AS optionname, " + 
				"   O.type " + 
				"		FROM db_quotation_management.quotation Q " + 
				"		INNER JOIN db_quotation_management.client C " + 
				"			ON Q.clientID = C.ID " + 
				"		INNER JOIN db_quotation_management.product P " + 
				"			ON Q.productID = P.ID " + 
				"		INNER JOIN (db_quotation_management.selectedoption SO INNER JOIN db_quotation_management.option O ON SO.optionID = O.ID) " + 
				"			ON Q.ID = SO.quotationID " + 
				"		WHERE Q.clientID = ?; ";
		
		// Query result structure:
		// quotationID | price | clientusername | productname | image | optionname | type
		
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, clientID);
			try (ResultSet result = pstatement.executeQuery();) {
				
				//variables used in while loop
				Quotation quotationBean;
				ArrayList<Option> options;
				
				//parsing the result
				while (result.next()) { //loops on all the different quotations made by the client
					quotationBean = new Quotation();
					//price
					float price = result.getFloat("price");
					if(!result.wasNull()) {
						quotationBean.setPrice(price);
					}
					//client username
					quotationBean.setClientUsername(result.getString("username"));
					//product selected
					quotationBean.setProduct(new Product(
							result.getString("productname"),
							result.getBytes("image")
							));
					//options selected for the product
					options = new ArrayList<Option>();
					//first option
					options.add(new Option(
							result.getString("type"),
							result.getString("optionname")
							));
					int currentQuotationID = result.getInt("quotationID");
					while(result.next() && (result.getInt("quotationID") == currentQuotationID)) {//loops on all the options for the current quotation
						options.add(new Option(
								result.getString("type"),
								result.getString("optionname")
								));
					}
					quotationBean.setOptions(options);
					
					quotations.add(quotationBean);
					
					if(result.isAfterLast()) {
						break;
					}
				}
			}
		}
		
		return quotations; //might be empty
	}
	
	public ArrayList<Quotation> findAllByEmplyeeID(int emplyeeID) throws SQLException {

		//TODO re-do with separated queries
		//==> this.getOptionsSelected()
		
		//returned object
		ArrayList<Quotation> quotations = new ArrayList<Quotation>();
		
		String query = "SELECT" + 
				"	Q.ID AS quotationID, " + 
				"	Q.price, " + 
				"	C.username AS clientusername, " + 
				"	P.name AS productname, " + 
				"	P.image, " + 
				"   O.name as optionname, " + 
				"   O.type " + 
				"   E.username AS employeeusername " +
				"		FROM db_quotation_management.quotation Q " + 
				"        INNER JOIN db_quotation_management.client C " + 
				"			ON Q.clientID = C.ID " + 
				"        INNER JOIN db_quotation_management.product P " + 
				"			ON Q.productID = P.ID " + 
				"		INNER JOIN (db_quotation_management.selectedoption SO INNER JOIN db_quotation_management.option O ON SO.optionID = O.ID) " + 
				"			ON Q.ID = SO.quotationID " + 
				"		INNER JOIN (db_quotation_management.management M INNER JOIN db_quotation_management.employee E ON M.employeeID = E.ID) " + 
				"			ON Q.ID = M.quotationID " + 
				"		WHERE E.ID = ?;";
		
		// Query result structure:
		// quotationID | emplyeeusername | price | clientusername | productname | image | optionname | type
		
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, emplyeeID);
			try (ResultSet result = pstatement.executeQuery();) {
				
				//variables used in while loop
				Quotation quotationBean;
				ArrayList<Option> options;
				
				//parsing the result
				while (result.next()) { //loops on all the different quotations managed by the emplyee
					quotationBean = new Quotation();
					//employee username
					quotationBean.setEmplyeeUsername(result.getString("emplyeeusername"));
					//price
					quotationBean.setPrice(result.getFloat("price"));
					//client username
					quotationBean.setClientUsername(result.getString("username"));
					//product selected
					quotationBean.setProduct(new Product(
							result.getString("productname"),
							result.getBytes("image")
							));
					//options selected for the product
					options = new ArrayList<Option>();
					//first option
					options.add(new Option(
							result.getString("type"),
							result.getString("optionname")
							));
					int currentQuotationID = result.getInt("quotationID");
					while(result.next() && (result.getInt("quotationID") == currentQuotationID)) {//loops on all the options for the current quotation
						options.add(new Option(
								result.getString("type"),
								result.getString("optionname")
								));
					}
					quotationBean.setOptions(options);
					
					quotations.add(quotationBean);
					
					if(result.isAfterLast()) {
						break;
					}
				}
			}
		}
		
		return quotations; //might be empty
	}
	
	public int createQuote(int productID, int clientID, int[] optionsID ) throws SQLException {
		
		//TODO devo rendere il tutto un'unica transazione 
		
		String query = "INSERT into db_quotation_management.quotation (productID, clientID) VALUES (?, ?);";
		try (PreparedStatement pstatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
			
			pstatement.setInt(1, productID);
			pstatement.setInt(2, clientID);
			pstatement.executeUpdate();
			
			ResultSet generatedKeys = pstatement.getGeneratedKeys();
			if (!generatedKeys.next()) {
				throw new SQLException("Creating user failed, no ID obtained.");
			}
		}
		query = "INSERT into db_quotation_management.selectedoption (optionID, quotationID) VALUES (?, LAST_INSERT_ID());";
		try (PreparedStatement pstatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
			
			for(int i = 0; i < optionsID.length; i++) {
				pstatement.setInt(1, optionsID[i]);
				pstatement.executeUpdate();
			}
			
			ResultSet generatedKeys = pstatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				return generatedKeys.getInt(1);
			} else {
				throw new SQLException("Creating user failed, no ID obtained.");
			}
			
			
		}
	}
	
	public ArrayList<Quotation> findAllNotManaged(){
		//returned object
		ArrayList<Quotation> quotations = new ArrayList<Quotation>();
		
		//extract all quotations where price is NULL
		String query = "SELECT \r\n" + 
				"	Q.ID,\r\n" + 
				"	Q.price,\r\n" + 
				"	C.username, \r\n" + 
				"	P.name, \r\n" + 
				"	P.image\r\n" + 
				"		FROM db_quotation_management.quotation Q\r\n" + 
				"		INNER JOIN db_quotation_management.client C\r\n" + 
				"			ON Q.clientID = C.ID\r\n" + 
				"		INNER JOIN db_quotation_management.product P\r\n" + 
				"			ON Q.productID = P.ID\r\n" + 
				"		WHERE Q.price IS NULL;";
		
		//extract all options selected for a specific query
		//DO IT IN A SEPARATED METHOD, SO WE CAN REUSE CODE.
		//==> this.getOptionsSelected()
	}
	
	public ArrayList<Option> getOptionsSelected(int quotationID) {
		ArrayList<Option> options = new ArrayList<Option>();
		
		//TODO
		
		return options;
	}
	
}
