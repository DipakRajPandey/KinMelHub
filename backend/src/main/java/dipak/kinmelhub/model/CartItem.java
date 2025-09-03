package dipak.kinmelhub.model;

import java.math.BigDecimal;

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
@AllArgsConstructor
@NoArgsConstructor
@Table(name="CARTITEM")
public class CartItem {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "cartitem_seq")
    @SequenceGenerator(name = "cartitem_seq", sequenceName = "cartitem_sequence", allocationSize = 1)
	private int id;

	    @ManyToOne
	    @JoinColumn(name = "cart_id", nullable = false)
	    @JsonBackReference
	    private Carts cart;

	    @ManyToOne
	    @JoinColumn(name = "product_id", nullable = false)
	    private Products product;

	    @NotNull
	    private Integer quantity;


	    private float price;
}
