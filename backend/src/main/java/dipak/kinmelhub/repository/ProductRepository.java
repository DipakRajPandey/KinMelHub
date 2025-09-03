package dipak.kinmelhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dipak.kinmelhub.model.Products;
import dipak.kinmelhub.model.Products.Status;

import java.util.List;


public interface ProductRepository extends JpaRepository<Products,Integer> {
 List<Products> findByNameIgnoreCase(String name);
 List<Products> findByVendor_StorenameIgnoreCase(String name);

 @Query("SELECT p FROM Products p WHERE p.status = :status AND p.vendor.id=:id")
 List<Products> findByStatus(@Param("status") Status status,@Param("id") Long id);

 @Query("SELECT p FROM Products p WHERE p.name=:name AND p.vendor.id=:id")
 List<Products> findByProductNameAndVendorId(@Param("name") String name,@Param("id") Long id);

 
 @Query("SELECT COUNT(o) FROM Products o")
 long countAllProducts();

}
