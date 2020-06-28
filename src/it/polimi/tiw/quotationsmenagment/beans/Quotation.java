package it.polimi.tiw.quotationsmenagment.beans;

public class Quotation {
	private int price; //negative price means no price
	private String employeeUsername;
	private String clientUsername;
	private Product product;
	
	public Quotation() {
		this.price = -1;
	}
	
	public Quotation( String productName, byte[] img, int price, String clientUsername ) {
		this.setProduct(new Product(productName, img));
		this.price = price;
		this.clientUsername = clientUsername;
	}
	
	public Quotation( Product product, int price, String clientUsername) {
		this.setProduct(product);
		this.price = price;
		this.clientUsername = clientUsername;
	}
	
	public void setPrice(int price) {
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

	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	public String getEmployeeUsername() {
		return employeeUsername;
	}

	public void setEmployeeUsername(String employeeUsername) {
		this.employeeUsername = employeeUsername;
	}
}
