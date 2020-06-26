package it.polimi.tiw.quotationsmenagment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import it.polimi.tiw.quotationsmenagment.beans.Option;
import it.polimi.tiw.quotationsmenagment.beans.Product;
import it.polimi.tiw.quotationsmenagment.beans.Quotation;

public class QuotationDAO {
	private Connection connection;
	
	public QuotationDAO(Connection connection) {
		this.connection = connection;
	}
	
	public ArrayList<Quotation> findAllByClientID(int clientID) throws SQLException {
		//returned object
		ArrayList<Quotation> quotations = new ArrayList<Quotation>();
		
		String query = "SELECT " +
				"   Q.ID AS quotationID, " +
				"   Q.price, " + 
				"	C.username AS clientusername, " + 
				"	P.name AS productname, " + 
				"	P.image, " + 
				"		FROM db_quotation_management.quotation Q " + 
				"		INNER JOIN db_quotation_management.client C " + 
				"			ON Q.clientID = C.ID " + 
				"		INNER JOIN db_quotation_management.product P " + 
				"			ON Q.productID = P.ID " +
				"		WHERE Q.clientID = ?; ";
		
		// Query result structure:
		// quotationID | price | clientusername | productname | image 
		
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, clientID);
			try (ResultSet result = pstatement.executeQuery();) {
				
				Quotation quotationBean;
				ArrayList<Option> options;
				
				//parsing the result
				while (result.next()) { //loops on all the different quotations made by the client
					quotationBean = new Quotation();
					//price
					int price = result.getInt("price");
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
					quotationBean.setOptions(this.getOptionsSelected(result.getInt("quotationID")));
					
					quotations.add(quotationBean);
				}
			}
		}
		
		return quotations; //might be empty
		
		/*OLD VERSION - single query, the returned value is the exact same, but the process is a little bit harder and complex:
		-----------------------------------------------------------------------------------------------------------------------
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
				"		WHERE Q.clientID = ?; ";
		
		// Query result structure:
		// quotationID | price | clientusername | productname | image | optionname | type
		
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, clientID);
			try (ResultSet result = pstatement.executeQuery();) {
				
				Quotation quotationBean;
				ArrayList<Option> options;
				
				//parsing the result
				while (result.next()) { //loops on all the different quotations made by the client
					quotationBean = new Quotation();
					//price
					int price = result.getInt("price");
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
		-----------------------------------------------------------------------------------------------------------------------
		*/
	}
	
	public ArrayList<Quotation> findAllByEmplyeeID(int emplyeeID) throws SQLException {		
		//returned object
		ArrayList<Quotation> quotations = new ArrayList<Quotation>();
		
		String query = "SELECT" + 
				"	Q.ID AS quotationID, " + 
				"	Q.price, " + 
				"	C.username AS clientusername, " + 
				"	P.name AS productname, " + 
				"	P.image, " +  
				"   E.username AS employeeusername " +
				"		FROM db_quotation_management.quotation Q " + 
				"        INNER JOIN db_quotation_management.client C " + 
				"			ON Q.clientID = C.ID " + 
				"        INNER JOIN db_quotation_management.product P " + 
				"			ON Q.productID = P.ID " + 
				"		INNER JOIN (db_quotation_management.management M INNER JOIN db_quotation_management.employee E ON M.employeeID = E.ID) " + 
				"			ON Q.ID = M.quotationID " + 
				"		WHERE E.ID = ?;";
		
		// Query result structure:
		// quotationID | emplyeeusername | price | clientusername | productname | image 
		
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, emplyeeID);
			try (ResultSet result = pstatement.executeQuery();) {
				
				Quotation quotationBean;
				ArrayList<Option> options;
				
				//parsing the result
				while (result.next()) { //loops on all the different quotations managed by the employee
					quotationBean = new Quotation();
					//employee username
					quotationBean.setEmplyeeUsername(result.getString("emplyeeusername"));
					//price
					quotationBean.setPrice(result.getInt("price"));
					//client username
					quotationBean.setClientUsername(result.getString("username"));
					//product selected
					quotationBean.setProduct(new Product(
							result.getString("productname"),
							result.getBytes("image")
							));
					//options selected for the product
					quotationBean.setOptions(this.getOptionsSelected(result.getInt("quotationID")));
					
					quotations.add(quotationBean);
					
					if(result.isAfterLast()) {
						break;
					}
				}
			}
		}
		
		return quotations; //might be empty
		
		/*OLD VERSION - single query, the returned value is the exact same, but the process is a little bit harder and complex:
		-----------------------------------------------------------------------------------------------------------------------
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
				
				Quotation quotationBean;
				ArrayList<Option> options;
				
				//parsing the result
				while (result.next()) { //loops on all the different quotations managed by the emplyee
					quotationBean = new Quotation();
					//employee username
					quotationBean.setEmplyeeUsername(result.getString("emplyeeusername"));
					//price
					quotationBean.setPrice(result.getInt("price"));
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
		-----------------------------------------------------------------------------------------------------------------------
		*/
	}
	
	public void createQuote(int productID, int clientID, int[] optionsID ) throws SQLException {
		//start transaction for database integrity
		connection.setAutoCommit(false);
		
		String query = "INSERT into db_quotation_management.quotation (productID, clientID) VALUES (?, ?);";
		try (PreparedStatement pstatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
			
			pstatement.setInt(1, productID);
			pstatement.setInt(2, clientID);
			pstatement.executeUpdate();
			
			query = "INSERT into db_quotation_management.selectedoption (optionID, quotationID) VALUES (?, LAST_INSERT_ID());";
			try (PreparedStatement pstatement1 = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
				for(int i = 0; i < optionsID.length; i++) {
					pstatement1.setInt(1, optionsID[i]);
					pstatement1.executeUpdate();
				}
			}
			connection.commit();
		} 
		catch (SQLException e) {
			connection.rollback();
			throw e;
		} 
		finally {
			connection.setAutoCommit(true);
		}
		
		return;
	}
	
	public void priceQuote(int quotationID, int price) throws SQLException {
		String query = "UPDATE `db_quotation_management`.`quotation` SET `price` = '?' WHERE (`ID` = '?');";
		try (PreparedStatement pstatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
			pstatement.setInt(1, price);
			pstatement.setInt(2, quotationID);
			pstatement.executeUpdate();
		} 
	}
	
	public ArrayList<Quotation> findAllNotManaged() throws SQLException{		
		//returned object
		ArrayList<Quotation> quotations = new ArrayList<Quotation>();
			
		//extract all quotations where price is NULL
		String query = "SELECT" + 
				"	Q.ID, " +
				"	C.username AS clientusername, " + 
				"	P.name AS productname, " + 
				"	P.image " + 
				"		FROM db_quotation_management.quotation Q " + 
				"		INNER JOIN db_quotation_management.client C " + 
				"			ON Q.clientID = C.ID " + 
				"		INNER JOIN db_quotation_management.product P" + 
				"			ON Q.productID = P.ID " + 
				"		WHERE Q.price IS NULL; ";
		
		// Query result structure:
		// ID | clientusername | productname | image
				
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			try (ResultSet result = pstatement.executeQuery();) {
				
				Quotation quotationBean;
				
				//parsing the result
				while (result.next()) { //loops on all the quotations where price is NULL
					quotationBean = new Quotation();
					//price
					quotationBean.setPrice(result.getInt("price"));
					//client username
					quotationBean.setClientUsername(result.getString("username"));
					//product selected
					quotationBean.setProduct(new Product(
							result.getString("productname"),
							result.getBytes("image")
							));
										
					quotationBean.setOptions(this.getOptionsSelected(result.getInt("ID")));
					
					quotations.add(quotationBean);
				}
			}
		}
		return quotations; //could be empty
	}
	
	public Quotation findByID(int quotationID) throws SQLException {
		Quotation quotation = null;
		
		String query = "SELECT " + 
				"	Q.ID AS quoationID, " + 
				"   Q.price, " +
				"   C.username AS clientusername, " + 
				"   P.name AS productname, " + 
				"   P.image " + 
				"   FROM db_quotation_management.quotation Q " + 
				"	INNER JOIN db_quotation_management.client C " + 
				"		ON Q.clientID = C.ID " + 
				"	INNER JOIN db_quotation_management.product P " + 
				"		ON Q.productID = P.ID " + 
				"	WHERE Q.ID = ?;";
		// Query result structure:
		// quotationID | price | clientusername | productname | image
						
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			try (ResultSet result = pstatement.executeQuery();) {
				
				pstatement.setInt(1, quotationID);
				
				//parsing the result
				if (result.next()) {
					quotation = new Quotation();
					//price
					int price = result.getInt("price");
					if(!result.wasNull()) {
						quotation.setPrice(price);
					}
					//client username
					quotation.setClientUsername(result.getString("clientusername"));
					//product selected
					quotation.setProduct(new Product(
							result.getString("productname"),
							result.getBytes("image")
							));
										
					quotation.setOptions(this.getOptionsSelected(quotationID));
				}
			}
		}
		return quotation; //could be null
	}
	
	public ArrayList<Option> getOptionsSelected(int quotationID) throws SQLException {
		//returned object
		ArrayList<Option> options = new ArrayList<Option>();
		
		String query = "SELECT " + 
				"	O.name, " + 
				"    O.type " + 
				"    FROM db_quotation_management.option AS O " + 
				"    INNER JOIN db_quotation_management.selectedoption SO " + 
				"		ON O.ID = SO.optionID " + 
				"	WHERE SO.quotationID = ?;";
		
		// Query result structure:
		// name | type
		
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			
			pstatement.setInt(1, quotationID);
			
			try (ResultSet result = pstatement.executeQuery();) {
				
				Option option;
				
				//parsing the result
				while (result.next()) { //loops on all the options found, must enter at least one times because of how the database is structured
					option = new Option();
					
					//option name
					option.setName(result.getString("name"));
					//option type
					option.setType(result.getString("type"));
					
					options.add(option);
				}
			}
		}
		
		return options; //must contain at least one option, because of how the database is structured
	}
	
}
