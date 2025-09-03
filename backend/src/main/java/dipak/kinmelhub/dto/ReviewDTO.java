package dipak.kinmelhub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {


	private Integer prdouctId;
	private int rating;
	private String comment;
	
	
}
