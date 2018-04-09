package io.myretail.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.myretail.model.PriceInfo;
import io.myretail.model.Product;
import io.myretail.model.ProductName;
import io.myretail.model.ProductPrice;
import io.myretail.repositroy.ProductRepository;

/**
 * This class is a middle layer between Spring JPA service which connects to data store 
 * and RESTful API controllers. It retrieves all product ID's from data store, it retrieves 
 * product information for any GET request with valid Product ID and also updates price of 
 * the product for a valid Product ID with PUT Request.
 * 
 * @author Rajendra Bymuthaka
 * @version 1.0
 * @date 04/08/2018
 */

@Service
public class ProductService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductNameService productNameService;

	/**
	 * This method makes a call to data store using JPA to fetch all product numbers available,
	 * and returns to controller class.
	 * @return
	 */
	public List<Long> getAllProducts() {
		logger.info("Querying for All products!");
		List<ProductPrice> products = new ArrayList<>();
		productRepository.findAll().forEach(products::add);

		List<Long> productIds = products.stream()
				.map(p -> new Long(p.getId()))
				.collect(Collectors.toList());

		logger.info("Found products in database are " + productIds.toString());
		Collections.sort(productIds);
		return productIds;
	}

	/**
	 * This method makes a call to data store using JPA to fetch product price information 
	 * from data store, and makes another call to product name service to get product name. 
	 * Then calls build product method to build product response using product name and price 
	 * information and returns to controller class.
	 * 
	 * @param productId
	 * @return
	 */
	public Product getProduct(Long productId) {
		logger.info("Querying product details for the given product number -> " + productId);
		ProductPrice productPrice = productRepository.findById(productId).orElse(null);
		ProductName productName = productNameService.getProductName(productId);
		Product product = productBuilder(productId, productPrice, productName);
		return product;

	}

	/**
	 * This method makes a call to data store using JPA to update product price information.
	 * 
	 * @param productId
	 * @param price
	 * @param currencyCode
	 * @return
	 */
	public int updateProduct(Long productId, float price, String currencyCode) {
		logger.info("Updating product price details!");
		int updateStatus = productRepository.updatePriceInfo(price, currencyCode, productId);
		logger.info("Product price update status is  " + updateStatus);
		return updateStatus;
	}

	/**
	 * This method builds product information using product price and product name.
	 * 
	 * @param productID
	 * @param productPrice
	 * @param productName
	 * @return
	 */
	public Product productBuilder(Long productID, ProductPrice productPrice, ProductName productName) {

		Product product = new Product();
		logger.info("Building produt response to return!");
		product.setProductName(productName.getProductName());
		product.setProductID(productID);

		PriceInfo priceInfo = new PriceInfo();
		if (productPrice == null) {
			priceInfo.setCurrencyCode("Price information not available for this product!");
			priceInfo.setPrice(null);
		} else {
			priceInfo.setCurrencyCode(productPrice.getCurrencyCode());
			priceInfo.setPrice(productPrice.getPrice());
		}
		product.setPriceInfo(priceInfo);
		return product;
	}
}
