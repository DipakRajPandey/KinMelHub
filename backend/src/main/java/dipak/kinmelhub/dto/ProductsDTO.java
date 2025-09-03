package dipak.kinmelhub.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsDTO {
	private String name;
	private BigDecimal price;
	private String category;
	private String image;
	private String description;
	
}
