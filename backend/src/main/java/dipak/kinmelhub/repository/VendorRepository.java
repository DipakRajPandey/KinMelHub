package dipak.kinmelhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dipak.kinmelhub.model.Products;
import dipak.kinmelhub.model.Vendors;

@Repository
public interface VendorRepository extends JpaRepository<Vendors,Integer>{
  Vendors findByStorenameIgnoreCase(String storename);
  
   Vendors findByUser_Id(int id);
  
 
}
