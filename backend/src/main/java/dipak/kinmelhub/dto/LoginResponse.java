package dipak.kinmelhub.dto;

import dipak.kinmelhub.model.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
private String message;
private Users user;
}
