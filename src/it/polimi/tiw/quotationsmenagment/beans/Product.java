package it.polimi.tiw.quotationsmenagment.beans;

import java.util.ArrayList;

public class Product {
	private String name;
	private byte[] image;
	private ArrayList<Option> options;
	
	public Product() {
		//sets attributes with setters
	}
	
	public Product(String name, byte[] image){
		this.setName(name);
		this.setImage(image);
	}
	
	public Product(String name, byte[] image, ArrayList<Option> options){
		this.setName(name);
		this.setImage(image);
		this.setOptions(options);
	}

	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Option> getOptions() {
		return options;
	}
	public void setOptions(ArrayList<Option> options) {
		this.options = options;
	}
	
	public String toString() {
		String s = "Product: [name: " + this.name + ", image: " + this.image + "]";
		for (int i = 0; i < this.options.size(); i++) {
			s += "\n  -";
			s += this.options.get(i).toString();
		}
		return s;
	}
	
}
