package dipak.kinmelhub.service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dipak.kinmelhub.dto.ProductsDTO;
import dipak.kinmelhub.exception.ResourceNotFoundException;
import dipak.kinmelhub.model.Products;
import dipak.kinmelhub.model.Vendors;
import dipak.kinmelhub.repository.OrderItemRepository;
import dipak.kinmelhub.repository.OrderRepository;
import dipak.kinmelhub.repository.ProductRepository;
import dipak.kinmelhub.repository.VendorRepository;
import jakarta.transaction.Transactional;

@Service
public class ProductService {
@Autowired
private ProductRepository productRepo;
@Autowired
private CloudinaryService cs;
@Autowired
private OrderItemRepository orderitemRepo;
@Autowired
private VendorRepository vendorRepo;
@Transactional
public Products addProduct(String id,ProductsDTO product, MultipartFile image) {
	Products product1=new Products();
	Map<String,Object> profile=cs.upload(image);
product1.setImage(profile.get("secure_url").toString());

product1.setStatus(Products.Status. AVAILABLE);
product1.setCategory(product.getCategory());
product1.setDescription(product.getDescription());
product1.setName(product.getName());
product1.setPrice(product.getPrice());
// Vendors vendor=vendorRepo.findById(product.getVendor().getId()).get();
Vendors vendor=vendorRepo.findById(Integer.valueOf(id)).get();

 if(vendor==null) {
	 throw new ResourceNotFoundException("Vendor not found ");
 }
 product1.setVendor(vendor);
	return productRepo.save(product1);
}
public List<Products> getAllProduct() {
	List<Products> products=productRepo.findAll();
	if(products.size()==0) {
		throw new ResourceNotFoundException("No any products present ");
	}
	return products ;
}
public List<Products> getProductByName(String name) {
	List<Products> products=productRepo.findByNameIgnoreCase(name);
	if(products.size()==0) {
		throw new ResourceNotFoundException("No any products present ");
	}
	return products ;
}

public Products getProductById(String id) {
 Products  products=productRepo.findById(Integer.valueOf(id)).get();
	if(products==null) {
		throw new ResourceNotFoundException("No any products present ");
	}
	return products ;
}

public List<Products> getProductByStoreName(String storename) {
  Vendors vendor=vendorRepo.findByStorenameIgnoreCase(storename);
  if(vendor==null) {
	  throw new ResourceNotFoundException("Vendor Not present ");
  }
  List<Products> products=productRepo.findByVendor_StorenameIgnoreCase(vendor.getStorename());

  return products;
}
public Products updatProduct(String id, ProductsDTO product, MultipartFile image) {
	Products p=productRepo.findById(Integer.valueOf(id))
			.orElseThrow(()->new NoSuchElementException("Product of id  "+id+" does not present"));
	
	if(product.getCategory()!=null){
		p.setCategory(product.getCategory());
	}
	if(product.getDescription()!=null) {
		p.setDescription(product.getDescription());
	}
	if(image!=null && !image.isEmpty()) {
		System.out.println(image);
		Map<String,Object> profile=cs.upload(image);
		p.setImage(profile.get("secure_url").toString());
	}
	if(product.getName()!=null) {
		p.setName(product.getName());
	}
	if(product.getPrice()!=null) {
	p.setPrice(product.getPrice());
	}
	return productRepo.save(p);
}

public Products deleteProduct(String id) {
	
//	if(!orderRepo.findById(Integer.valueOf(id)).isEmpty()) {
//		throw new IllegalStateException("Cannot delete product: associated orders exist ");
//	}
	   if (!orderitemRepo.findByProduct_Id(Integer.valueOf(id)).isEmpty()) {
	        throw new IllegalStateException("Cannot delete product: associated order items exist.");
	    }

	Products product=productRepo.findById(Integer.valueOf(id)).
			orElseThrow(()->new NoSuchElementException("Product not found"));

    productRepo.delete(product);
    return product;
}
public List<Products> getProductByNameAndStoreName(String id, String name) {
	List<Products> product=productRepo.findByProductNameAndVendorId(name,Long.valueOf(id));
	return product;
}
}

