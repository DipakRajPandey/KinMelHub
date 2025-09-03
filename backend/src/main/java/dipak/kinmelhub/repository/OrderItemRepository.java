package dipak.kinmelhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import dipak.kinmelhub.model.*;
import jakarta.transaction.Transactional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
	 @Modifying
	 @Transactional
	 @Query("DELETE FROM OrderItem o WHERE o.product = :product")
	 void deleteAllByProduct(@Param("product") Products product);
	 
	 
	 List<OrderItem> findByProduct_Id(int id );
}
