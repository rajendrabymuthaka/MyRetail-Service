package io.myretail.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import io.myretail.controller.ProductController;
import io.myretail.model.PriceInfo;
import io.myretail.model.Product;
import io.myretail.service.ProductService;

/**
 * This class is for performing unit tests for GET and PUT methods which are for 
 * getting product information and update product price information respectively.
 * 
 * @author Rajendra Bymuthaka
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = ProductController.class, secure = false)
public class ProductControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private ProductService productService;
	PriceInfo priceInfo = new PriceInfo((float) 13.99,"USD");
	Long productID = (long) 13860428;
	String expected = "{\"productID\":13860428,\"productName\":\"The Big Lebowski (Blu-ray)\",\"priceInfo\":{\"price\":13.99,\"currencyCode\":\"USD\"}}";
	Product mockProduct = new Product(productID, "The Big Lebowski (Blu-ray)", priceInfo);
	
	
	/**
	 * This method performs mock HTTP GET request unit test to get the product details for given
	 * product ID and evaluates against corresponding mock product.
	 * 
	 * @throws Exception
	 */
	@Test
	public void getProductMockTest() throws Exception {
				
		Mockito.when(productService.getProduct(Mockito.anyLong())).thenReturn(mockProduct);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/" + productID)
				.accept(org.springframework.http.MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	/**
	 * This method performs mock HTTP PUT request unit test to update the product details
	 * based on the mock response value and HTTP status code.
	 * 
	 * @throws Exception
	 */
	@Test
	public void updatePriceMockTest() throws Exception {
		Mockito.when(productService.updateProduct(Mockito.anyLong(), Mockito.anyFloat(), Mockito.anyString())).thenReturn(1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/products/13860428")
				.accept(MediaType.APPLICATION_JSON).content(expected).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

}
