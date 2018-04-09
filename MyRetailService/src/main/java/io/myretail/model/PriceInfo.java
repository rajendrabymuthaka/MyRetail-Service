package io.myretail.model;

/**
 *  
 * @author Rajendra Bymuthaka
 * @version 1.0
 * @date 04/08/2018
 */

public class PriceInfo {

	private Float price;
	private String currencyCode;
	
	public PriceInfo() {
		super();
	}

	public PriceInfo(Float price, String currencyCode) {
		super();
		this.price = price;
		this.currencyCode = currencyCode;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	
}
