package dipak.kinmelhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import dipak.kinmelhub.model.*;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Integer> {
	@Query("select o from Orders o where o.user.id=:userid")
	List<Orders> findByUserId(@Param("userid") int userid);
	List<Orders>findByItems_Product_Vendor_StorenameIgnoreCase(String storename);
	
	@Query("SELECT COUNT(o) FROM Orders o")
	long countAllOrders();

	// total sal
	@Query("Select Sum(o.totalprice) from Orders o Where o.paymentstatus='PAID'")
	float totalSal();
	
	
	
	//getting total order placed
	@Query("SELECT  o FROM Orders o JOIN o.items i WHERE i.product.vendor.id =:id")
	List<Orders> getTotalOrderOfVendor(@Param("id") Long vendorId);
	
	//getting total order
	@Query("SELECT COUNT(o) FROM Orders o JOIN o.items i WHERE i.product.vendor.id =:id")
	Double countTotalOrders(@Param("id") Long vendorId);
 
	//getting panding order

	@Query("SELECT COUNT(DISTINCT o) FROM Orders o JOIN o.items i WHERE o.orderstatus = 'PENDING' AND i.product.vendor.id = :id")
	Double countPendingOrders(@Param("id") Long vendorId);

	
	
	//getting delivered order
		@Query("SELECT COUNT(o) FROM Orders o JOIN o.items i WHERE o.orderstatus='DELIVERED' AND i.product.vendor.id = :id")
		Double countDeliverdOrders(@Param("id") Long vendorId);
		
		//getting delivered order
		@Query("SELECT COUNT(o) FROM Orders o JOIN o.items i WHERE o.orderstatus='ONTHEWAY' AND i.product.vendor.id = :id")
		Double countOnTheWayOrders(@Param("id") Long vendorId);
		
	//getting Canceled  order
	@Query("SELECT COUNT(o) FROM Orders o  JOIN o.items i WHERE o.orderstatus='CANCELED' AND i.product.vendor.id = :id")
	Double countCanceledOrders(@Param("id") Long vendorId);		
	
				
	// getting total pending order
	@Query ("SELECT sum(o.totalprice) FROM Orders o JOIN o.items i  where o.paymentstatus='PENDING' AND i.product.vendor.id = :id")
	Double totalPendingEarning(@Param("id") Long vendorId);
	
	// getting total earning 
	@Query ("SELECT sum(o.totalprice) FROM Orders o JOIN o.items i where o.paymentstatus='PAID' AND i.product.vendor.id = :id")
	Double totalEarning(@Param("id") Long vendorId);
				
}
