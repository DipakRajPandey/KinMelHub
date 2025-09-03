package dipak.kinmelhub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
	   private Long itemId; 
	    private Integer productId;
	    private int quantity;
}
