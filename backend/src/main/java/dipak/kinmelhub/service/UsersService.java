package dipak.kinmelhub.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dipak.kinmelhub.dto.LoginResponse;
import dipak.kinmelhub.dto.UserDTO;
import dipak.kinmelhub.exception.AccessDeniedException;
import dipak.kinmelhub.exception.DublicateResourceException;
import dipak.kinmelhub.exception.ResourceNotFoundException;
import dipak.kinmelhub.model.Users;
import dipak.kinmelhub.model.Users.Role;
import dipak.kinmelhub.model.Users.Status;
import dipak.kinmelhub.repository.OrderRepository;
import dipak.kinmelhub.repository.ProductRepository;
import dipak.kinmelhub.repository.UserRepository;
import dipak.kinmelhub.repository.VendorRepository;

@Service
public class UsersService {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private CloudinaryService cs;
	@Autowired 
	private ProductRepository productRepo;
	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private VendorRepository vendorRepo;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private CustomUserService customUserService;
	
	
	
public Users registerUser(Users user,MultipartFile imag) {
	if(userRepo.findByEmail(user.getEmail()).isPresent() ) {
		throw new DublicateResourceException("User of email id"+user.getEmail()+
				"is already present");
	}
	if (imag != null && !imag.isEmpty()) {
		Map<String,Object> profile=cs.upload(imag);
		user.setProfile(profile.get("secure_url").toString()); 
	}
//	 user.setRole(Role.ADMIN);
	user.setPassword(passwordEncoder.encode(user.getPassword()));
	user.setRole(Role.CUSTOMER);
	 user.setStatus(Status.ACTIVATE);
	return userRepo.save(user);
}


public List<Users> getAllUser() {	 
	return userRepo.findAll();
}
public Users getUserByEmail(String email) {
Users user=userRepo.findByEmail(email).orElse(null);
if(user==null) {
	throw new ResourceNotFoundException("User of the email id "+email+" is not found");
}
return  user;
}


public String deleteUser(String email) {
	Users user=userRepo.findByEmail(email).orElse(null);
	if(user==null) {
		throw new ResourceNotFoundException("Cannot delet user with id :"+email+"as it does not exist");
	}
	userRepo.delete(user);
	return "User deleted with id "+email;
}


//public Users updateUser(String email, UserDTO user,MultipartFile imag) {
//	Users dbuser=userRepo.findByEmail(email).
//			orElseThrow(() -> new NoSuchElementException("User of gmail :"+email+"  does not exist"));
//;
//
//	
//if(user.getName()!=null) {
//	dbuser.setName(user.getName());
//}
//if(user.getPassword()!=null) {
//	dbuser.setPassword(user.getPassword());
//}
//if(imag!=null && imag.isEmpty()) {
//	Map<String,Object> profile=cs.upload(imag);
//	dbuser.setProfile(profile.get("secure_url").toString());
//}
//	return userRepo.save(dbuser);
//}


public Users updateUser(String id, UserDTO user,MultipartFile imag) {
	Users dbuser=userRepo.findById(Integer.valueOf(id)).
	
			orElseThrow(() -> new NoSuchElementException("User   does not exist"));
;
// if(user.getEmail()!=null) {
//	 dbuser.setEmail(user.getEmail());
// }
	
if(user.getName()!=null && !user.getName().isBlank()) {
	dbuser.setName(user.getName());
}
if(user.getPassword()!=null && !user.getPassword().isBlank() ) {
	dbuser.setPassword(user.getPassword());
}
if(imag!=null && !imag.isEmpty()) {
	Map<String,Object> profile=cs.upload(imag);
	dbuser.setProfile(profile.get("secure_url").toString());
}
if(user.getStatus().equals("INACTIVE")) {
	dbuser.setStatus(Status.INACTIVE);
}
if(user.getStatus().equals("BAN") ) {
	dbuser.setStatus(Status.BAN);
}
if(user.getStatus().equals("ACTIVATE") ) {
	dbuser.setStatus(Status.ACTIVATE);
}


	return userRepo.save(dbuser);
}


public LoginResponse isLogin(String email, String password) {
	
	
//	UserDetails user=customUserService.loadUserByUsername(email);
	 Users user = userRepo.findByEmail(email).get();
	System.out.println(user);
	if(user==null) {
		throw new ResourceNotFoundException("Username / password invalid !!!");		
	}
	if(user.getStatus()==Users.Status.BAN) {
		throw new  AccessDeniedException("User Ban by Admin");
	}
	if(!passwordEncoder.matches(password,user.getPassword())) {
		throw new RuntimeException("Username / password invalid !!");
	}
	
  return  new LoginResponse("Login ",user);
	
}

//get  data for Admin
public Map<String,Object> getAdminData(){
	Map<String ,Object> data=new HashMap<>();
	 
	Long totaluser=userRepo.countAllUsers();
	Long customer=userRepo.countAllCustomer();
	Long vendor=userRepo.countAllVendors();
	Long totalproduct=productRepo.countAllProducts();
	Long totalorder=orderRepo.countAllOrders();
	float totalsal=orderRepo.totalSal();
	data.put("totaluser", totaluser);
	data.put("customer", customer);
	data.put("vendor", vendor);
	data.put("totalproduct", totalproduct);
	data.put("totalorder", totalorder);
	data.put("totalsal", totalsal);
			
	
	return data;
}

}
