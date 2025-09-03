package dipak.kinmelhub.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="ORDERSITEM")
public class OrderItem {

		@Id
		@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "orderitem_seq")
	    @SequenceGenerator(name = "orderitem_seq", sequenceName = "orderitem_sequence", allocationSize = 1)
 
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "order_id", nullable = false)
	    @JsonBackReference
	    @JsonIgnore
	    private Orders order;

	    @ManyToOne
	    @JoinColumn(name = "product_id", nullable = false)
	  
	    private Products product;

	    @NotNull
	    private Integer quantity;

	    private float price; // price at the time of order
	

}
