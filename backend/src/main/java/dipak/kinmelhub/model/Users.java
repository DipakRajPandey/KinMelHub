package dipak.kinmelhub.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@ToString(exclude = {"vendors"})
@Entity
@Table(name="USERS")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Users {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)

	private int id;
	@NotBlank(message="Email is required")
	@Email(message="Invalid mail format")
	private String email;
	@NotBlank(message="Name is required")
	private String name;
	@NotBlank(message="Password is required")
	private String password;
	private String profile;
	@Enumerated(EnumType.STRING)
	private Role role;
	public enum Role {
    ADMIN,
    VENDOR,
    CUSTOMER,
    SUPER_ADMIN
}
@Enumerated(EnumType.STRING)
	private Status status=Status.ACTIVATE;
	public enum Status{
		ACTIVATE,
		INACTIVE,
		BAN
	}
	  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//	  @JsonIgnore
	    private Vendors vendor;
}
