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
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
 List<CartItem> findByCart_User_Id(int id );
 List<CartItem> findByProduct_Vendor_StorenameIgnoreCase(String storename);
 @Modifying
 @Transactional
 @Query("DELETE FROM CartItem c WHERE c.product = :product")
 void deleteAllByProduct(@Param("product") Products product);
}
