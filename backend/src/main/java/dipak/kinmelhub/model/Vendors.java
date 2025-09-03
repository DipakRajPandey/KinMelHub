package dipak.kinmelhub.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@ToString(exclude = {"products", "user"})
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="VENDORS")
public class Vendors {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "vendor_seq")
    @SequenceGenerator(name = "vendor_seq", sequenceName = "vendor_sequence", allocationSize = 1)

	private int id;
	@NotBlank(message="store name is required")
	@Column(unique = true)
	private String storename;
	@NotBlank(message="store address is required")
	private String address;
	@NotBlank(message="Contact number is required")
	private String contactnumber;
	@Lob
    @Column(length = 10000)
	private String bio;
	private String logo;
	@OneToOne(cascade = CascadeType.ALL) 
	@JoinColumn(name="user_id", unique=true)
	@JsonIgnore
	private Users user;
		
	@OneToMany(mappedBy="vendor" ,cascade = CascadeType.ALL, orphanRemoval = true )
//	@JsonManagedReference
	@JsonIgnore
	private List<Products> products;
	
}
