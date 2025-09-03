package dipak.kinmelhub.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="REVIEWS")
public class Review {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "review_seq")
    @SequenceGenerator(name = "review_seq", sequenceName = "review_sequence", allocationSize = 1)
	private int id;
	@ManyToOne
	@JoinColumn(name="product_id",nullable=false)
	@JsonIgnore 
	private Products product;
	
	@ManyToOne
	@JoinColumn(name="user_id",nullable=false)
//	
	private Users user;
	
	@Min(1)
	@Max(5)
	@NotNull
	private int rating;
	@Column(name = "review_comment")
	@Size(max=1000)
	private String comment;
	@Column(name = "review_date")
	@DateTimeFormat(iso=ISO.DATE)
	private LocalDate reviewDate;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@PrePersist
	public void onCreate() {
	    this.createdAt = LocalDateTime.now();
	}

}
