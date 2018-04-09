package io.myretail.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.myretail.model.ProductName;

/**
 * This class provides unit tests for productNameService.
 * 
 * @author Rajendra Bymuthaka
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductNameServiceTest {

	@Mock
	private ProductNameService productNameService;
	Long productID = (long) 13860428;
	String name = "The Big Lebowski (Blu-ray)";

	/**
	 * This method tests getPoruductName method by creating mock PorductNameService 
	 * and response is evaluated against mock product name.
	 *  
	 * @throws Exception
	 */
	@Test
	public void getProductNameMockTest() throws Exception {
		ProductName productName = new ProductName();
		productName.setProductName(name);
		Mockito.when(productNameService.getProductName(productID)).thenReturn(productName);
		ProductName result = productNameService.getProductName(productID);
		assertEquals(result.getProductName(), productName.getProductName());
	}
}
