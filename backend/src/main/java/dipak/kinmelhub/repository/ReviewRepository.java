package dipak.kinmelhub.repository;

import org.springframework.data.domain.Pageable; // ✅ CORRECT

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import dipak.kinmelhub.model.*;
import jakarta.transaction.Transactional;
@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer>{
	Page<Review> findByProductId(int id,Pageable pageable);
@Modifying
@Transactional
@Query("DELETE FROM Review r WHERE r.product = :product")
void deleteAllByProduct(@Param("product") Products product);
}
