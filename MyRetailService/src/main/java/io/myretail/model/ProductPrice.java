package io.myretail.model;

/**
 *  
 * @author Rajendra Bymuthaka
 * @version 1.0
 * @date 04/08/2018
 */

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductPrice {
	
	@Id
	private Long id;
	private float price;
	private String currencyCode;
	
	
	
	public ProductPrice() {
		super();
	}

	public ProductPrice(Long id, float price, String currencyCode) {
		super();
		this.id = id;
		this.price = price;
		this.currencyCode = currencyCode;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	

	

}
