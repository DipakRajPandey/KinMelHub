package dipak.kinmelhub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailEntity {
	private String productname;
	private double productprice;
	@Override
	public String toString() {
	    return "Product Name: " + productname + "\nPrice: " + productprice;
	}

	
}
