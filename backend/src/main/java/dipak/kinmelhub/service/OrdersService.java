package dipak.kinmelhub.service;
import dipak.kinmelhub.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dipak.kinmelhub.dto.OrderItemDTO;
import dipak.kinmelhub.dto.OrdersDTO;
import dipak.kinmelhub.exception.ResourceNotFoundException;
import dipak.kinmelhub.model.Orders;
import dipak.kinmelhub.model.Products.Status;
import dipak.kinmelhub.repository.OrderItemRepository;
import dipak.kinmelhub.repository.OrderRepository;
import dipak.kinmelhub.repository.ProductRepository;
import dipak.kinmelhub.repository.UserRepository;
import dipak.kinmelhub.repository.VendorRepository;
import jakarta.transaction.Transactional;

@Service
public class OrdersService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private VendorRepository vendorRepo;
	@Autowired
	private OrderItemRepository orderitemRepo;
	@Autowired
	private OrderRepository orderRepo;
	
	@Transactional
	public Orders placeOrder(String id ,OrdersDTO orderdto) {
	 Users user=userRepo.findById(Integer.valueOf(id))
			 .orElseThrow(()-> new ResourceNotFoundException("User not found"));
	 Orders order=new Orders();
	 order.setUser(user);
	 order.setAddress(orderdto.getAddress());
	 order.setPhone(orderdto.getPhone());
	 order.setOrderstatus("PENDING");
	 order.setPaymentstatus("PENDING");
	 order.setOrderdate(LocalDate.now());
	 order.setDeliverydate(LocalDate.now().plusDays(7));
	 List<OrderItem>items =new ArrayList<>();
	 float totalprice=0;
	 for(OrderItemDTO dto:orderdto.getItems()) {
		 Products product=productRepo.findById(dto.getProductId())
				 .orElseThrow(()->new ResourceNotFoundException("Product not found"));
		 OrderItem orderItem=new OrderItem();
		
		 orderItem.setProduct(product);
		 orderItem.setOrder(order);
		 orderItem.setQuantity(dto.getQuantity());
		 orderItem.setPrice(product.getPrice().floatValue()*dto.getQuantity());
		 totalprice +=orderItem.getPrice();
		 items.add(orderItem);
	 }
	 order.setItems(items);
	 order.setTotalproduct(items.size());
	 order.setTotalprice(totalprice);
	 
				return orderRepo.save(order);
		
	}

	public Orders deleteOrder(String id) {
		Orders order=orderRepo.findById(Integer.valueOf(id))
		.orElseThrow(()->new ResourceNotFoundException("Order not found"));
		order.setOrderstatus("CANCELED");
		order.setPaymentstatus("FAILED");
		orderRepo.save(order);
		return order;
	}

	public List<Orders>getOrderByUserId(String userid) {
		 Users user=userRepo.findById(Integer.valueOf(userid))
				 .orElseThrow(()-> new ResourceNotFoundException("User not found"));
		 
		List<Orders> order=orderRepo.findByUserId(Integer.valueOf(userid));
		if(order.size()==0) {
			throw new ResourceNotFoundException("Order not present to this store");
		}
		return order;
	}
	public List<Orders>getOrderByStoreName(String storename) {
		Vendors vendor=vendorRepo.findByStorenameIgnoreCase(storename);
		if(vendor==null) {
			  throw new ResourceNotFoundException("Vendor Not present ");
		  }
		List<Orders> order=orderRepo.findByItems_Product_Vendor_StorenameIgnoreCase(storename);
		if(order.size()==0) {
			throw new ResourceNotFoundException("Order not present to this store");
		}
		return order;
	}
	
	

	@Transactional
	public Orders updateOrder(String id,OrdersDTO orderdto ) {
		Orders order=orderRepo.findById(Integer.valueOf(id))
				.orElseThrow(()->new ResourceNotFoundException("Order not present "));
		if(orderdto.getPaymentStatus() !=null) {
			order.setPaymentstatus(orderdto.getPaymentStatus());
		}
	
		
//		if(orderdto.getPaymentStatus().equalsIgnoreCase("PAID")) {
//			order.setOrderstatus("DELIVERED");
//		}
//		else {
//			
//		}
		if(orderdto.getOrderStatus()!=null) {
			order.setOrderstatus(orderdto.getOrderStatus());	
		}
			
	
	if(orderdto.getAddress() !=null) {
		order.setAddress(orderdto.getAddress());
	}
	if(orderdto.getPhone()!=null) {
		order.setPhone(orderdto.getPhone());
	}
	if(orderdto.getItems()!=null) {
	  List<OrderItem> items = new ArrayList<>();
		 float totalprice=0;
		 for(OrderItemDTO dto:orderdto.getItems()) {
			 OrderItem orderItem;
			 if (dto.getItemId() != null) {
	                orderItem = orderitemRepo.findById(dto.getItemId())
	                    .orElseThrow(() -> new ResourceNotFoundException("Order item not found"));
	            } else {
	                orderItem = new OrderItem();
	                orderItem.setOrder(order);
	            }			 
			 Products product=productRepo.findById(dto.getProductId())
					 .orElseThrow(()->new ResourceNotFoundException("Product not found"));
			
			 orderItem.setProduct(product);
			 orderItem.setOrder(order);
			 orderItem.setQuantity(dto.getQuantity());
			 orderItem.setPrice(product.getPrice().floatValue()*dto.getQuantity());
			 totalprice +=orderItem.getPrice();
			items.add(orderItem);
		 }
		 order.setItems(items);
		 order.setTotalproduct(items.size());
		 order.setTotalprice(totalprice);
	}
	
	return orderRepo.save(order);
	
	}
	
	
	public Map<String,Double>getAllData(String id){
		Map<String,Double> newdata=new HashMap<>();
		
		Double pendingOrder=orderRepo.countPendingOrders(Long.valueOf(id));
		Double deliveredOrder=orderRepo.countDeliverdOrders(Long.valueOf(id));
		Double onTheWayOrder=orderRepo.countOnTheWayOrders(Long.valueOf(id));
		Double canceledOrder=orderRepo.countCanceledOrders(Long.valueOf(id));
		Double totalorder=orderRepo.countTotalOrders(Long.valueOf(id));
		Double totalPendingEarning=orderRepo.totalPendingEarning(Long.valueOf(id));
		if(totalPendingEarning==null) {
			totalPendingEarning=0.0;
		}
		Double totalEarning=orderRepo.totalEarning(Long.valueOf(id));
		if(totalEarning==null) {
			totalEarning=0.0;
		}
		Double availableProduct	= (double)productRepo.findByStatus(Status.AVAILABLE,Long.valueOf(id)).size();

		
		newdata.put("pendingOrder", pendingOrder);
		newdata.put("delivredOrder", deliveredOrder);
		newdata.put("onTheWayOrder", onTheWayOrder);
		newdata.put("canceledOrder", canceledOrder);
		newdata.put("totalorder", totalorder);
		newdata.put("totalPendingEarning",  totalPendingEarning);
		newdata.put("totalEarning", totalEarning);
		newdata.put("availableProduct",  availableProduct);
		
		return newdata;
	}

	public List<Orders>getAllOrderByVendorId(String id) {
	List<Orders> order=orderRepo.getTotalOrderOfVendor(Long.valueOf(id));
	
		return order;
	}

	public List<Orders> getAllOrders() {
		// TODO Auto-generated method stub
		return orderRepo.findAll();
	}
	
}
