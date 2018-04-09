package io.myretail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Product ID's in the request url and request body are not same!")
public class ProductsNotMatchedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
