package dipak.kinmelhub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dipak.kinmelhub.dto.CartDTO;
import dipak.kinmelhub.dto.CartItemDTO;
import dipak.kinmelhub.model.CartItem;
import dipak.kinmelhub.model.Carts;
import dipak.kinmelhub.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name="Cart apis")

@RestController
public class CartController {
	@Autowired
	private CartService cartService;
     @PostMapping("/addcart/{id}")
     public ResponseEntity<Carts> addToCart(@Valid @RequestBody CartDTO cart,@PathVariable String id){
    	 return new ResponseEntity<>(cartService.addToCart(id,cart),HttpStatus.CREATED);
     }
    // get cartitem  by userid 
     @GetMapping("/getallcartitembyuserid/{id}")
     public ResponseEntity<List<CartItem>> getAllCartItemByUsrId(@PathVariable String id){
    	 return new ResponseEntity<>(cartService.getAllCartItemByUsrId(id),HttpStatus.OK);
     }
     @GetMapping("/getallcartitembystorename/{storename}")
     public ResponseEntity<List<CartItem>>getCartItemByStoreName(@PathVariable String storename){
    	 return new ResponseEntity<>(cartService.getCartItemByStoreName(storename),HttpStatus.OK);
     }
     
     //updating the cart
     @PutMapping("/updatecart/{id}")
     public ResponseEntity<Carts> updateCarts(@PathVariable String id,@RequestBody CartItemDTO cartitemdto){
    	 return new ResponseEntity<>(cartService.updateCartItem(id, cartitemdto),HttpStatus.OK);
     }
     // delete the cart 
     @DeleteMapping("/deletecart/{id}")
     public ResponseEntity<Carts>deleteCart(@PathVariable String id ){
    	 return new ResponseEntity<>(cartService.deleteCart(id),HttpStatus.OK);
     }
}
