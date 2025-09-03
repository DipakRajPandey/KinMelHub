package dipak.kinmelhub.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDTO {
	private String address;
	private String phone;
	private String paymentStatus;
	private String orderStatus;
    private List<OrderItemDTO> items;

}
