package dipak.kinmelhub.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dipak.kinmelhub.dto.CartDTO;
import dipak.kinmelhub.dto.CartItemDTO;
import dipak.kinmelhub.exception.ResourceNotFoundException;
import dipak.kinmelhub.model.CartItem;
import dipak.kinmelhub.model.Carts;
import dipak.kinmelhub.model.Products;
import dipak.kinmelhub.model.Users;
import dipak.kinmelhub.model.Vendors;
import dipak.kinmelhub.repository.CartItemRepository;
import dipak.kinmelhub.repository.CartRepository;
import dipak.kinmelhub.repository.ProductRepository;
import dipak.kinmelhub.repository.UserRepository;
import dipak.kinmelhub.repository.VendorRepository;
import jakarta.validation.Valid;

@Service
public class CartService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private CartItemRepository cartitemRepo;
    @Autowired
    private VendorRepository vendorRepo;
    @Autowired
    private CartRepository cartRepo;
	public Carts addToCart(String id,CartDTO cart) {
		Users user=userRepo.findById(Integer.parseInt(id))
				 .orElseThrow(()-> new ResourceNotFoundException("User not found")); 
		Carts cart1=new Carts();
		cart1.setUser(user);
		 List<CartItem>items =new ArrayList<>();
		 float totalprice=0;
		 for(CartItemDTO dto:cart.getItems()) {
			 Products product=productRepo.findById(dto.getProductId())
					 .orElseThrow(()->new ResourceNotFoundException("Product not found"));
			 CartItem cartItem=new CartItem();
			 cartItem.setProduct(product);
			 cartItem.setCart(cart1);
			 cartItem.setQuantity(dto.getQuantity());
			 cartItem.setPrice(product.getPrice().floatValue()*dto.getQuantity());
			 totalprice +=cartItem.getPrice();
			 items.add(cartItem);
		 }
		 cart1.setItems(items);
 //     	 cart1.setTotalproduct(items.size());
//		 cart1.setTotalprice(totalprice);
					return cartRepo.save(cart1);
		
	}
	
	//select cartitem by user id 
	public List<CartItem> getAllCartItemByUsrId(String id){
		Users user=userRepo.findById(Integer.valueOf(id))
				 .orElseThrow(()-> new ResourceNotFoundException("User not found"));
		List<CartItem> cart=cartitemRepo.findByCart_User_Id(Integer.valueOf(id));
//		if(cart.size()==0) {
//			throw new ResourceNotFoundException("No any item present in cart present ");
//
//		}
		return cart;
	}
   //select cartitem by storename
	public List<CartItem>getCartItemByStoreName(String storename){
		 Vendors vendor=vendorRepo.findByStorenameIgnoreCase(storename);
		  if(vendor==null) {
			  throw new ResourceNotFoundException("Vendor Not present ");
		  }
		List<CartItem> cart=cartitemRepo.findByProduct_Vendor_StorenameIgnoreCase(storename);
//		if(cart.size()==0) {
//			throw new ResourceNotFoundException("No any item present in cart present ");
//
//		}
		return cart;
	}
	//updating  cartiteam 
	public Carts updateCartItem(String id ,CartItemDTO cartitemdto) {

		CartItem oldcartitem=cartitemRepo.findById(Integer.valueOf(id))
				.orElseThrow(()->new ResourceNotFoundException("Cart item  not present "));
		
		Carts cart1=cartRepo.findById(oldcartitem.getCart().getId())
				.orElseThrow(()->new ResourceNotFoundException("Cart not present "));
		
		Users user=userRepo.findById(cart1.getUser().getId())
				 .orElseThrow(()-> new ResourceNotFoundException("User not found"));

	    List<CartItem> items = cart1.getItems();
	    Optional<CartItem> existingitem=items.stream().filter(product->product.getProduct().getId()==cartitemdto.getProductId()).findFirst();
	
	if(existingitem.isPresent()) {
		 CartItem cartItem = existingitem.get();
	        cartItem.setQuantity(cartitemdto.getQuantity());
			cartItem.setPrice(existingitem.get().getProduct().getPrice().floatValue()*cartitemdto.getQuantity());

	}else {
        throw new RuntimeException("Product not found in cart");
    }
	return cartRepo.save(cart1);
}
	//delete  cart
	public Carts deleteCart(String id ) {
		CartItem oldcartitem=cartitemRepo.findById(Integer.valueOf(id))
				.orElseThrow(()->new ResourceNotFoundException("Cart item  not present "));
		
		Carts cart1=cartRepo.findById(oldcartitem.getCart().getId())
				.orElseThrow(()->new ResourceNotFoundException("Cart not present "));
		cartRepo.delete(cart1);
		return cart1;
	}
	
}
