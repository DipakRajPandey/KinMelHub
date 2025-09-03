package dipak.kinmelhub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import dipak.kinmelhub.model.Users;
@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {
public Optional<Users> findByEmail(String email);
@Query("SELECT COUNT(o) FROM Users o")
long countAllUsers();
@Query("SELECT COUNT(o) FROM Users o Where o.role='VENDOR'")
long countAllVendors();
@Query("SELECT COUNT(o) FROM Users o Where o.role='CUSTOMER'")
long countAllCustomer();




}
