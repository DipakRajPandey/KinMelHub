package dipak.kinmelhub.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dipak.kinmelhub.model.*;
public interface CartRepository extends JpaRepository<Carts,Integer>{
	 List<Carts> findByUser_Id(int id );

}
