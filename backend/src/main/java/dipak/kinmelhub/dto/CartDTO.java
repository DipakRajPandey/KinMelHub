package dipak.kinmelhub.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
     private Integer userid;
     private List<CartItemDTO> items;
}
