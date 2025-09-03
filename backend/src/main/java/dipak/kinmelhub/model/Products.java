package dipak.kinmelhub.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(exclude = {"vendor"})
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="PRODUCTS")
public class Products {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
	@SequenceGenerator(name = "product_seq", sequenceName = "product_sequence", allocationSize = 1)
	private int id;
	@NotBlank(message="product name is requireed")
	private String name;
	@NotNull(message="price is required")
	@DecimalMin(value="0.0", inclusive=false ,message="Price must be positive")
	private BigDecimal price;
	@NotBlank(message="Category is required")
	private String category;
	
	private String image;
//	@Lob
    @Column(length = 10000)
	private String description;
    private Status status=Status. AVAILABLE;
    public enum Status{
    	 AVAILABLE,
    	NOTAVAILIABLE
    }
    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
//  @JsonBackReference
    
    private Vendors vendor;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();
    
}
