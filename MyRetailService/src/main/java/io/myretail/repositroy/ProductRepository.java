package io.myretail.repositroy;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import io.myretail.model.ProductPrice;

/**
 *  
 * @author Rajendra Bymuthaka
 * @version 1.0
 * @date 04/08/2018
 */

public interface ProductRepository extends CrudRepository<ProductPrice, Long>{
	
	@Modifying
	@Query("UPDATE ProductPrice p SET p.price=?1, p.currencyCode=?2 where p.id=?3")
	@Transactional
	int updatePriceInfo(float price, String currencyCode, Long productId);

}
