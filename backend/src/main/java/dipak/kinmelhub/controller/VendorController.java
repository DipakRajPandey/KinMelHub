package dipak.kinmelhub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dipak.kinmelhub.model.*;
import dipak.kinmelhub.service.VendorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import dipak.kinmelhub.dto.*;
@RestController
@RequestMapping("/vendor")
@Tag(name="Vendor apis",description="This  is vendor apis")
@CrossOrigin(origins = "http://localhost:5173")

public class VendorController {
@Autowired
private VendorService vendorService;
@PostMapping("/register")
public ResponseEntity<Vendors>registerVendor(@RequestPart("vendor")String
		vendordto
		,@RequestPart(value="profile", required=false) MultipartFile profile,
		@RequestPart (value="logo",required=false)MultipartFile logo ) throws JsonMappingException, JsonProcessingException{
	ObjectMapper mapper = new ObjectMapper();

	VendorDTO vendor=mapper.readValue(vendordto, VendorDTO.class);
	return new ResponseEntity<>(vendorService.registerVendor(vendor,profile,logo),HttpStatus.CREATED);
}
//get vendor by Strorename
@GetMapping("/getvendorbystorename/{storename}")
public ResponseEntity<Vendors>getVendorByStoreName(@PathVariable String storename){
		
		return new ResponseEntity<>(vendorService.getVendorByStoreName(storename),HttpStatus.OK);
}
//get All vendors
@GetMapping("/getallvendors")
public ResponseEntity<List<Vendors>>getAllVendors(){
	
	return new ResponseEntity<>(vendorService.getAllVendors(),HttpStatus.OK);
}
//delete vendor by storename
@DeleteMapping("/deletevendorbystorename/{storename}")
public ResponseEntity<Vendors>deleteVendorBYStoreName(@PathVariable String storename){
	
	return new ResponseEntity<>(vendorService.deleteVendorByStoreName(storename),HttpStatus.OK);
}
//updating vendors delete 

@PutMapping("/updatevendor/{id}")
public ResponseEntity<Vendors> updateVendor(@PathVariable String id,
		@RequestPart("vendor")String vendordto,
		@RequestPart (value="logo",required=false)MultipartFile logo,
		@RequestPart (value="profile",required=false)MultipartFile profile )
				throws JsonMappingException, JsonProcessingException{
	ObjectMapper mapper = new ObjectMapper();

	VendorDTO vendor=mapper.readValue(vendordto, VendorDTO.class);
	return new ResponseEntity<>(vendorService.updateVendor(id,vendor,logo,profile),HttpStatus.OK);

}







}
