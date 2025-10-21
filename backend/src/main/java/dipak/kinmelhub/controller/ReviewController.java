package dipak.kinmelhub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dipak.kinmelhub.dto.ReviewDTO;
import dipak.kinmelhub.model.Review;
import dipak.kinmelhub.service.ReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
// @CrossOrigin(origins = "http://localhost:5173")
@Tag(name="Review api",description="This  is vendor apis")

@RestController
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    
    @PostMapping("/addreview/{id}")
    public ResponseEntity<Review> addReview(@Valid @RequestBody ReviewDTO review,@PathVariable String id){
    	
    	return new ResponseEntity<>(reviewService.addReview(id,review),HttpStatus.CREATED);
    }
    
    //updating review
    @PostMapping("/updatereview/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable String id,@RequestBody ReviewDTO review){
    	return new ResponseEntity<>(reviewService.updateReview(id,review),HttpStatus.OK);
    }
    //deletereview
    @DeleteMapping("/deletereview/{id}")
    public ResponseEntity<Review> deleteReview(@PathVariable String id){
    	return new ResponseEntity<>(reviewService.deleteReview(id),HttpStatus.OK);
    }
    
    //select review by  product id
    
    @GetMapping("/getreviewbyproductid/{id}")
    public ResponseEntity<Page<Review>> getReviewByProductId(@PathVariable String id,
    		 @RequestParam(defaultValue = "0") int page,
    	        @RequestParam(defaultValue = "5") int size){
    	return new ResponseEntity<>(reviewService.getReviewByProductId(id,page,size),HttpStatus.OK);
    }
}
