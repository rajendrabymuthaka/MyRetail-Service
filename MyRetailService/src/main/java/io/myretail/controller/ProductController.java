package io.myretail.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import io.myretail.exception.InvalidProductNumberException;
import io.myretail.exception.ProductNotFoundException;
import io.myretail.exception.ProductsNotMatchedException;
import io.myretail.exception.SuccessResponse;
import io.myretail.model.Product;
import io.myretail.service.ProductService;

/**
 * This class provides RESTful services to get all products available in database,
 * to get single product details(product name, price, currency) and to update 
 * product price information.
 * 
 * @author Rajendra Bymuthaka
 * @version 1.0
 * @date 04/08/2018
 */

@RestController
public class ProductController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProductService productService;

	/**
	 * @return
	 */
	@RequestMapping("/products")
	public List<Long> getProducts() {
		return productService.getAllProducts();
	}

	/**
	 * This method receives product ID from client and performs validations to 
	 * determine if the Product ID is valid, if so it makes call to productService to 
	 * get product details. Also returns product information to client in JSON format.
	 * 
	 * @param productId
	 * @return
	 * @throws InvalidProductNumberException
	 * @throws ProductNotFoundException
	 */
	@RequestMapping("/products/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable Long productId)
			throws InvalidProductNumberException, ProductNotFoundException {
		Product product = new Product();

		if (productId <= 0) {
			logger.info("Invalid product ID has been entered!");
			throw new InvalidProductNumberException();
		} else {
			product = productService.getProduct(productId);
		}

		if (product.getProductID() == null || product.getProductName().equals("")) {
			logger.info("Product ID or Product Name is not found and response is being returned!");
			throw new ProductNotFoundException();
		} else {
			logger.info("Product response successfully built and is being returned!");
			return new ResponseEntity<Product>(product, HttpStatus.OK);
		}

	}

	/**
	 * This method receives product ID and JSON message with updated product price information 
	 * from client and performs validations to determine if the Product ID and parameters in the 
	 * JSON message are valid, if so it makes call to productService to update product price details.
	 * Also returns success/failure response to client
	 * 
	 * @param product
	 * @param productId
	 * @return
	 * @throws ProductsNotMatchedException
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/products/{productId}")
	public ResponseEntity<SuccessResponse> updateProduct(@RequestBody Product product, @PathVariable Long productId)
			throws ProductsNotMatchedException {
		int updateStatus = 0;
		if (productId <= 0) {
			logger.info("Invalid product ID has been entered!");
			throw new InvalidProductNumberException();
		} else if (product.getProductID().longValue() != productId.longValue()) {
			logger.info("Product ID's entered in url and message body are not same and response is being returned!");
			throw new ProductsNotMatchedException();
		} else {
			try {
				logger.info("Product price information update request for product number -> " + productId);
				updateStatus = productService.updateProduct(productId, product.getPriceInfo().getPrice(),
						product.getPriceInfo().getCurrencyCode());
			} catch (Exception e) {
				logger.debug("Something went wrong while updating product price information!");
				e.printStackTrace();
			}
		}
		if (updateStatus == 1) {
			logger.info("Product price has been updated succesfully and response is being sent!");
			return new ResponseEntity<SuccessResponse>(new SuccessResponse(200, "Price has been updated sucessfully"),
					HttpStatus.OK);
		} else {
			logger.info("Product not found in the database and response is being sent!");
			throw new ProductNotFoundException();
		}
	}
}
