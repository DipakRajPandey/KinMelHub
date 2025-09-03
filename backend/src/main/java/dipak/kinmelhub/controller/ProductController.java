package dipak.kinmelhub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

import dipak.kinmelhub.dto.ProductsDTO;
import dipak.kinmelhub.model.Products;
import dipak.kinmelhub.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name="Product api")

@RestController
public class ProductController {
	@Autowired
	private ProductService productService;

	@PostMapping("/addproduct/{id}")
	public ResponseEntity<Products> addProduct(@Valid @RequestPart("product")String ptext,
			@PathVariable("id") String id,
        @RequestPart(value = "image") MultipartFile image) throws JsonMappingException, JsonProcessingException 
        		{
	ObjectMapper mapper = new ObjectMapper();
	ProductsDTO product = mapper.readValue(ptext,ProductsDTO.class);
		return new ResponseEntity<>(productService.addProduct(id,product,image),HttpStatus.CREATED);
}
	
// select all products
	@GetMapping("/allproduct")
	public ResponseEntity<List<Products>>getAllProducts(){
		return new ResponseEntity<>(productService.getAllProduct(),HttpStatus.OK);
	}
//select product by productname
	@GetMapping("/getproductbyname/{name}")
	public ResponseEntity<List<Products>> getProductByName(@PathVariable String name){
		return new ResponseEntity<>(productService.getProductByName(name),HttpStatus.OK);

	}
	
	//select product by storename
	@GetMapping("/getproductbystorename/{storename}")
	public ResponseEntity<List<Products>> getProductByStoreName(@PathVariable String storename){
		return new ResponseEntity<>(productService.getProductByStoreName(storename),HttpStatus.OK);

	}
	//select product byid
		@GetMapping("/getproductbyid/{id}")
		public ResponseEntity<Products> getProductByStoreId(@PathVariable String id){
			return new ResponseEntity<>(productService.getProductById(id),HttpStatus.OK);

		}
	//updating product
	@PutMapping("/updateproduct/{id}")
	public ResponseEntity<Products> updateProduct(@PathVariable String id,
			@RequestPart("product")String ptext,
	        @RequestPart(value = "image",required=false) MultipartFile image) throws JsonMappingException, JsonProcessingException 
	        		{
		ObjectMapper mapper = new ObjectMapper();
		ProductsDTO product = mapper.readValue(ptext,ProductsDTO.class);
			return new ResponseEntity<>(productService.updatProduct(id,product,image),HttpStatus.OK);
	}
// deleting product 
	@DeleteMapping("/deleteproduct/{id}")
	public ResponseEntity<?>deleteProduct(@PathVariable String id ){
		Products p=productService.deleteProduct(id);
		
			return new ResponseEntity<>(p,HttpStatus.OK);	
		
	}
	//
	@GetMapping("/getproductforvendor/{id}/{name}")
	public ResponseEntity<List<Products> > getProductByNameAndStoreName(@PathVariable("id") String id,@PathVariable("name") String name)
	{
		return new ResponseEntity<>(productService.getProductByNameAndStoreName(id,name),HttpStatus.OK);
	}
	
}