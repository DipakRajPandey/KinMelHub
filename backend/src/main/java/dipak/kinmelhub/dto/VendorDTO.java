package dipak.kinmelhub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorDTO {
	
	private String email;
	
	private String name;
	
	private String password;
	
//	private String profile;
	
	private String storename;
	
	private String address;
	
	private String contactnumber;
	
	private String bio;
	private String status;
	
//	private String logo;
}
