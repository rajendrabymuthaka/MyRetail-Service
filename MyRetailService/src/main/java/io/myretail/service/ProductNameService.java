package io.myretail.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import io.myretail.model.ProductName;
import io.myretail.util.Constants;

/**
 * This class makes call to My Retail service and processes the JSON response to get the 
 * product name for any given product ID.
 *  
 * @author Rajendra Bymuthaka
 * @version 1.0
 * @date 04/08/2018
 */

@Service
public class ProductNameService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private RestTemplate restTemplate = new RestTemplate();

	/**
	 * 
	 * This method makes  REST call to My Retail Service with Product ID to get corresponding 
	 * JSON message which consists of Product name information
	 * @param productID
	 * @return
	 */
	public ProductName getProductName(Long productID) {

		ProductName productName = new ProductName();
		String myRetailServiceURL = Constants.HOST + productID + Constants.EXCLUDE_PARAMS;
		String myRetailResponse = null;
		logger.info("Querying for product name from Retail server!");
		logger.info("Target URL to get product name details -> " + myRetailServiceURL);
		try {
			myRetailResponse = restTemplate.getForObject(myRetailServiceURL, String.class);
		} catch (Exception e) {
			logger.info("Product information not found in My Retail server!");
			productName.setProductName("");
			logger.debug(e.getMessage());
		}

		if (myRetailResponse != null) {
			try {
				JSONObject requestJsonObject = new JSONObject(myRetailResponse);
				productName = parseJson(requestJsonObject);
				logger.info("Product name is -> " + productName.getProductName());
			} catch (Exception e) {
				logger.info(
						"Product information found in My Retail server, but something went wrong while retreiving product name");
				productName.setProductName("");
				e.printStackTrace();
			}
		}
		return productName;
	}

	/**
	 * This message parses JSOn object received in My Retail REST service and processes
	 * to get the product name information. 
	 * 
	 * @param requestJsonObject
	 * @return
	 * @throws JSONException
	 */
	public ProductName parseJson(JSONObject requestJsonObject) throws JSONException {
		ProductName productName = new ProductName();
		JSONObject product = requestJsonObject.getJSONObject(Constants.PRODUCT);
		JSONObject item = product.getJSONObject(Constants.ITEM);
		JSONObject productDescription = item.getJSONObject(Constants.PRODUCT_DESCRIPTION);
		productName.setProductName(productDescription.getString(Constants.TITLE));
		return productName;
	}

}
