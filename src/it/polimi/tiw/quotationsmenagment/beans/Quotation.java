package it.polimi.tiw.quotationsmenagment.beans;

import java.util.ArrayList;

public class Quotation {
	private float price; //negative price means no price
	private String emplyeeUsername;
	private String clientUsername;
	private Product product;
	private ArrayList<Option> options;
	
	public Quotation() {
		this.price = -1;
	}
	
	public Quotation( String productName, byte[] img, float price, String clientUsername ) {
		this.setProduct(new Product(productName, img));
		this.price = price;
		this.clientUsername = clientUsername;
	}
	
	public Quotation( Product product, float price, String clientUsername) {
		this.setProduct(product);
		this.price = price;
		this.clientUsername = clientUsername;
	}
	
	public void setPrice(float price) {
		this.price = price;
	}
	public float getPrice() {
		return this.price;
	}
	
	public void setClientUsername(String clientUsername) {
		this.clientUsername = clientUsername;
	}
	public String getClientUsername() {
		return this.clientUsername;
	}
	
	public void setOptions(ArrayList<Option> options) {
		this.options = options;
	}
	public ArrayList<Option> getOptions() {
		return this.options;
	}

	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	public String getEmplyeeUsername() {
		return emplyeeUsername;
	}
	public void setEmplyeeUsername(String emplyeeUsername) {
		this.emplyeeUsername = emplyeeUsername;
	}
}
