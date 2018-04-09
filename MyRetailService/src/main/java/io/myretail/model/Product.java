package io.myretail.model;

/**
 *  
 * @author Rajendra Bymuthaka
 * @version 1.0
 * @date 04/08/2018
 */

public class Product {
	
	private Long productID;
	private String productName;
	private PriceInfo priceInfo;
	
	public Product() {
		super();
	}

	public Product(Long productID, String productName, PriceInfo priceInfo) {
		super();
		this.productID = productID;
		this.productName = productName;
		this.priceInfo = priceInfo;
	}

	
	public Long getProductID() {
		return productID;
	}

	public void setProductID(Long productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public PriceInfo getPriceInfo() {
		return priceInfo;
	}

	public void setPriceInfo(PriceInfo priceInfo) {
		this.priceInfo = priceInfo;
	}
	
}
