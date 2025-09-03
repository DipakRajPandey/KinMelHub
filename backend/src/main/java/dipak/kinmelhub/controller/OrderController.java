package dipak.kinmelhub.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dipak.kinmelhub.dto.OrdersDTO;
import dipak.kinmelhub.model.Orders;
import dipak.kinmelhub.service.OrdersService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name="Order apis",description="This  is vendor apis")

@RestController
public class OrderController {
 @Autowired
 private OrdersService orderService;
 @PostMapping("/addorder/{id}")
 public ResponseEntity<Orders> addOrder(@Valid@RequestBody OrdersDTO order,@PathVariable("id") String id){
	 return new ResponseEntity<>(orderService.placeOrder(id,order),HttpStatus.CREATED);
 }
 //deleting orders
 @DeleteMapping("/deleteorder/{id}")
 public ResponseEntity<Orders>deleteOrders(@PathVariable String id){
	 return  new ResponseEntity<>(orderService.deleteOrder( id),HttpStatus.OK);
 }
 
 //Select order by userid
 @GetMapping("/getorderbyuserid/{id}")
 public ResponseEntity<List<Orders>>getOrderByUserId(@PathVariable String id){
	 return new ResponseEntity<>(orderService.getOrderByUserId(id),HttpStatus.OK);
 }
//Select order by storename
@GetMapping("/getorderbystorename/{storename}")
public ResponseEntity<List<Orders>>getOrderByStoreName(@PathVariable String storename){
	 return new ResponseEntity<>(orderService.getOrderByStoreName(storename),HttpStatus.OK);
}
//updating order
@PutMapping("/updateorder/{id}")
public ResponseEntity<Orders> updateProduct(@PathVariable String id,@RequestBody OrdersDTO orderdto){
	return new ResponseEntity<>(orderService.updateOrder(id, orderdto),HttpStatus.OK);
}

//getting data for vendor dashbord data
@GetMapping("/getdata/{id}")
public ResponseEntity<Map<String,Double>>getAllData(@PathVariable String id){
	return new ResponseEntity<>(orderService.getAllData(id),HttpStatus.OK);

}
//getting total orders by vendorid 
@GetMapping("/totalorder/{id}")
public ResponseEntity<List<Orders>>getAllOrderByVendorId(@PathVariable("id") String id ){
	return new ResponseEntity<>(orderService.getAllOrderByVendorId(id),HttpStatus.OK);
}

//get all orders
@GetMapping("/getallorders")
public ResponseEntity<List<Orders>> getAllOrders(){
	return new ResponseEntity<>(orderService.getAllOrders(),HttpStatus.OK);
}
}
