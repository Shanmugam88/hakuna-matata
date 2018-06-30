package org.tiaa.warehouse;

public class Product {

	private String productDesc;
	private int timeConsumed;
	public Product(String productDesc, int timeConsumed) {
		super();
		this.productDesc = productDesc;
		this.timeConsumed = timeConsumed;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public int getTimeConsumed() {
		return timeConsumed;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Product [productDesc=");
		builder.append(productDesc);
		builder.append(", timeConsumed=");
		builder.append(timeConsumed);
		builder.append("]");
		return builder.toString();
	}

	
	
	
}
