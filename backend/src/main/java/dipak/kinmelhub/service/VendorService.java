package dipak.kinmelhub.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dipak.kinmelhub.dto.UserDTO;
import dipak.kinmelhub.dto.VendorDTO;
import dipak.kinmelhub.exception.DublicateResourceException;
import dipak.kinmelhub.exception.ResourceNotFoundException;
import dipak.kinmelhub.model.Products;
import dipak.kinmelhub.model.Users;
import dipak.kinmelhub.model.Users.Role;
import dipak.kinmelhub.model.Users.Status;
import dipak.kinmelhub.model.Vendors;
import dipak.kinmelhub.repository.CartItemRepository;
import dipak.kinmelhub.repository.OrderItemRepository;
import dipak.kinmelhub.repository.ProductRepository;
import dipak.kinmelhub.repository.ReviewRepository;
import dipak.kinmelhub.repository.UserRepository;
import dipak.kinmelhub.repository.VendorRepository;
import jakarta.transaction.Transactional;

@Service
public class VendorService {
@Autowired
private UserRepository userRepo;
@Autowired 
private VendorRepository vendorRepo;
@Autowired
private  CloudinaryService cs;
@Autowired
private ProductRepository productRepo;
@Autowired
private OrderItemRepository orderitemRepo;
@Autowired
private ReviewRepository reviewRepo;
@Autowired
private CartItemRepository cartitemRepo;
@Autowired
private UsersService userService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

private String uploadFile(MultipartFile file) {
	if (file != null && !file.isEmpty()) {
		Map<String, Object> result = cs.upload(file);
		return result.get("secure_url").toString();
	}
	return null;
}
@Transactional
	public Vendors registerVendor(VendorDTO vendor, MultipartFile profile, MultipartFile logo) {
		if(userRepo.findByEmail(vendor.getEmail()).isPresent()) {
			throw new DublicateResourceException("User of email id"+vendor.getEmail()+
					"is already present");
		}
	 Vendors v1= vendorRepo.findByStorenameIgnoreCase(vendor.getStorename());
	 if(v1!=null) {
			throw new DublicateResourceException("Vendor of store name"+vendor.getStorename()+
					"is already present");
	 }if (vendor.getStorename() == null || vendor.getStorename().isBlank()) {
		    throw new IllegalArgumentException("Store name must not be null or empty");
	 }
	 Users u=new Users();
	 
	 u.setEmail(vendor.getEmail());
	 u.setPassword(passwordEncoder.encode(vendor.getPassword()));//password encoder add later
	 u.setName(vendor.getName());
	 u.setProfile(uploadFile(profile));
	 u.setRole(Role.VENDOR);
	 u.setStatus(Status.ACTIVATE);

//	Users savedUser = userRepo.save(u);
	Vendors v=new Vendors();
	v.setStorename(vendor.getStorename());
	v.setAddress(vendor.getAddress());
	v.setContactnumber(vendor.getContactnumber());
	v.setBio(vendor.getBio());
	v.setLogo(uploadFile(logo));
//	v.setUser(savedUser);
	v.setUser(u);
	u.setVendor(v);
	return  vendorRepo.save(v);
			
			
	}

public Vendors getVendorByStoreName( String storename){
	Vendors vendor=vendorRepo.findByStorenameIgnoreCase(storename);
	if(vendor==null) {
		throw new ResourceNotFoundException("Store not present");
	}
	return vendor;
	
}
public List<Vendors>getAllVendors(){
List<Vendors> vendors=vendorRepo.findAll();
if(vendors.size()==0) {
	throw new ResourceNotFoundException("Stores not present at");

}
return vendors;
}
//delete vendor by storename
@Transactional
public Vendors deleteVendorByStoreName( String storename){
    Vendors vendor=getVendorByStoreName(storename);
    
  List<Products> productlist=vendor.getProducts();
    for(Products product:productlist) {
    	orderitemRepo.deleteAllByProduct(product);
    	reviewRepo.deleteAllByProduct(product);
    	cartitemRepo.deleteAllByProduct(product);
    	
    	
    }
    productRepo.deleteAll(productlist);
    vendorRepo.delete(vendor);
    
    return vendor;
}

//updating vendors  
@Transactional
public Vendors updateVendor( String id, VendorDTO vendordto,MultipartFile logo,MultipartFile profile ){
  Vendors vendor=vendorRepo.findById(Integer.valueOf(id))
		  .orElseThrow(()->new ResourceNotFoundException("Store not exist"));
  	

  if(vendordto.getAddress()!=null && !vendordto.getAddress().isBlank()) {
	  vendor.setAddress(vendordto.getAddress());
  }
  if(vendordto.getBio()!=null && !vendordto.getBio().isBlank()) {
	  
	  vendor.setBio(vendordto.getBio());
  }
  if(vendordto.getContactnumber()!=null && !vendordto.getContactnumber().isBlank()) {
	  vendor.setContactnumber(vendordto.getContactnumber());
  }
  if(vendordto.getStorename()!=null && !vendordto.getStorename().isBlank()) {
	  vendor.setStorename(vendordto.getStorename());
  }
  if(logo!=null && !logo.isEmpty()) {
		Map<String,Object> lo=cs.upload(logo);
		vendor.setLogo(lo.get("secure_url").toString());
	}
 
  
  //
  UserDTO userdto=new UserDTO();
  userdto.setEmail(vendordto.getEmail());
  userdto.setName(vendordto.getName());
  userdto.setPassword(vendordto.getPassword());
  userdto.setStatus(vendordto.getStatus());
 
  
  String userid=String.valueOf(vendor.getUser().getId());

  Users u=userService.updateUser(userid, userdto, profile);


  vendor.setUser(u);
  
  return vendorRepo.save(vendor);
}












}
