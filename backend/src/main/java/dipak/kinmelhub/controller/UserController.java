package dipak.kinmelhub.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dipak.kinmelhub.dto.LoginRequest;
import dipak.kinmelhub.dto.LoginResponse;
import dipak.kinmelhub.dto.UserDTO;
import dipak.kinmelhub.model.Users;
import dipak.kinmelhub.service.UsersService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name="User apis")

@RestController
public class UserController {
	@Autowired
	private UsersService userService;
@PostMapping("/register")
public ResponseEntity<Users> registerUser(
		@RequestParam("user") String userJson,
        @RequestPart(value = "image", required = false) MultipartFile imag) throws JsonMappingException, JsonProcessingException {
	
	  ObjectMapper mapper = new ObjectMapper();
	    Users user = mapper.readValue(userJson, Users.class);
	return new ResponseEntity<>(userService.registerUser(user, imag),HttpStatus.CREATED);
}
@GetMapping("/alluser")
public ResponseEntity<List<Users>> getAllUsers(){
	return new ResponseEntity<>(userService.getAllUser(),HttpStatus.OK);
	
}
@GetMapping("/userbyemail/{email}")
public ResponseEntity<Users> getUserByEmail(@PathVariable String email){
	return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
	
}
@DeleteMapping("/delete/{email}")
public ResponseEntity<String> deleteUser(@PathVariable String email){
	return new ResponseEntity<>(userService.deleteUser(email),HttpStatus.OK);
	
}
//@PutMapping("/update/{email}")
//public ResponseEntity<Users> updateUser(@PathVariable String email,
//		@RequestParam("user") String userJson,
//        @RequestPart(value = "image", required = false) MultipartFile imag) throws JsonMappingException, JsonProcessingException {
//	
//	  ObjectMapper mapper = new ObjectMapper();
//	    UserDTO user = mapper.readValue(userJson, UserDTO.class);
//	return new ResponseEntity<>(userService.updateUser(email,user,imag),HttpStatus.OK);
//
//}
@PutMapping("/update/{id}")
public ResponseEntity<Users> updateUser(@PathVariable String id,
		@RequestParam("user") String userJson,
        @RequestPart(value = "image", required = false) MultipartFile imag) throws JsonMappingException, JsonProcessingException {
	
	  ObjectMapper mapper = new ObjectMapper();
	    UserDTO user = mapper.readValue(userJson, UserDTO.class);
	return new ResponseEntity<>(userService.updateUser(id,user,imag),HttpStatus.OK);

}
//mapping for login 
@PostMapping("/login")
public ResponseEntity<?> isLogin(@RequestBody LoginRequest login){
	
	
	
	return new ResponseEntity<>(userService.isLogin(login.getEmail(),login.getPassword()),HttpStatus.OK);
}

//getting data for admin deshboard

@GetMapping("/admindata")
public ResponseEntity<Map<String ,Object>> getAdminData(){
	return new ResponseEntity<>(userService.getAdminData(),HttpStatus.OK);
}





}
