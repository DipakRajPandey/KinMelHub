package dipak.kinmelhub.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="ORDERS")
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "order_sequence", allocationSize = 1)
	private int id;
	@ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
    private Users user;
	@NotNull(message="Address is required")
	private  String address;
	private int totalproduct; //for quantity of product
	private float totalprice;
	private String orderstatus="PENDING";
	private String paymentstatus="PENDING";
	@NotNull(message="COntact number is requied")
	private String phone;
	@DateTimeFormat(iso=ISO.DATE)
	private LocalDate orderdate;
	
	@DateTimeFormat(iso=ISO.DATE)
	private LocalDate deliverydate;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	@JsonManagedReference
	
	
    private List<OrderItem> items;
	
}
