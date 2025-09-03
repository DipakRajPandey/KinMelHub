package dipak.kinmelhub.service;


import java.time.LocalDate;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable; 

import dipak.kinmelhub.dto.ReviewDTO;
import dipak.kinmelhub.exception.ResourceNotFoundException;
import dipak.kinmelhub.model.Products;
import dipak.kinmelhub.model.Review;
import dipak.kinmelhub.model.Users;
import dipak.kinmelhub.repository.ProductRepository;
import dipak.kinmelhub.repository.ReviewRepository;
import dipak.kinmelhub.repository.UserRepository;

@Service

public class ReviewService {
  
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ProductRepository productRepo;
	@Autowired 
	private ReviewRepository reviewRepo;
	public Review addReview(String id,ReviewDTO review) {
		
		 Users user=userRepo.findById(Integer.valueOf(id)).get();
		 if(user==null) {
			 throw new ResourceNotFoundException("User not found");
		 }
				 
//				 .orElseThrow(()-> new ResourceNotFoundException("User not found"));
		
		 Products product=productRepo.findById(review.getPrdouctId())
				 .orElseThrow(()->new ResourceNotFoundException("Product not found"));
		 Review newReview=new Review();
		 newReview.setComment(review.getComment());
		 newReview.setProduct(product);
		 newReview.setUser(user);
		 newReview.setRating(review.getRating());
		 newReview.setReviewDate(LocalDate.now());
		 return reviewRepo.save(newReview);
	}
	

public Review updateReview(String id,ReviewDTO review) {
	
	Review existing=reviewRepo.findById(Integer.valueOf(id))
			.orElseThrow(()->new ResourceNotFoundException("Review not found"));
	
	if(review.getRating()!=existing.getRating()) {
		existing.setRating(review.getRating());
	}
	if(review.getComment()!=null) {
		existing.setComment(review.getComment());
	}
	existing.setReviewDate(LocalDate.now());
	return reviewRepo.save(existing);
}
public Review deleteReview(String id) {
	
	Review existing=reviewRepo.findById(Integer.valueOf(id))
			.orElseThrow(()->new ResourceNotFoundException("Review not found"));
	reviewRepo.delete(existing);
		return existing;
}


public Page<Review> getReviewByProductId(String id,int page,int size) {
//	List<Review> existing=reviewRepo.findByProductId(Integer.valueOf(id));
//	if(existing.size()==0) {
//			throw new ResourceNotFoundException("Review not found");
//	}
	Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
    return reviewRepo.findByProductId(Integer.valueOf(id), pageable);
		
}
}