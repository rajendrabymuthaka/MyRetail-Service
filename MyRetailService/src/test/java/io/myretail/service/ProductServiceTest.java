package io.myretail.service;
import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import io.myretail.model.PriceInfo;
import io.myretail.model.Product;
import io.myretail.model.ProductPrice;
import io.myretail.model.ProductName;
import io.myretail.repositroy.ProductRepository;
import io.myretail.service.ProductService;

/**
 * This class is for unit test cases to test getPorudct information use case.
 * @author Rajendra Bymuthaka
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

	@InjectMocks
	private ProductService productService;
	@Mock
	private ProductNameService productNameService;
	@Mock
	private ProductRepository productRepository;

	Long productID = (long) 13860428;
	PriceInfo priceInfo = new PriceInfo((float) 14.99, "USD");
	Product mockProduct = new Product(productID, "The Big Lebowski (Blu-ray)", priceInfo);

	
	/**
	 * This method performs unit testing for getProduct method with the help of productNameService
	 * and ProductRepository services. Builds mock product and gets response from both the services 
	 * and evaluates against mock product.
	 * @throws Exception
	 */
	@Test
	public void getProductMockTest() throws Exception {

		ProductPrice productPrice = new ProductPrice();
		productPrice.setCurrencyCode(priceInfo.getCurrencyCode());
		productPrice.setPrice(priceInfo.getPrice());
		productPrice.setId(productID);

		ProductName productName = new ProductName();
		productName.setProductName(mockProduct.getProductName());

		Mockito.when(productNameService.getProductName(productID)).thenReturn(productName);
		Mockito.when(productRepository.findById(productID)).thenReturn(Optional.of(productPrice));

		Product result = productService.getProduct(productID);
		assertEquals(result.getPriceInfo().getCurrencyCode(), mockProduct.getPriceInfo().getCurrencyCode());
		assertEquals(result.getPriceInfo().getPrice(), mockProduct.getPriceInfo().getPrice());
		assertEquals(result.getProductID(), mockProduct.getProductID());
		assertEquals(result.getProductName(), mockProduct.getProductName());

	}

}
