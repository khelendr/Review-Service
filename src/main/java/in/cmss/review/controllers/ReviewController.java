package in.cmss.review.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cmss.messaging.ReviewMessageProducer;
import in.cmss.review.entities.Review;
import in.cmss.review.services.ReviewService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private ReviewMessageProducer messageProducer;
	
	
	@GetMapping
	public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId) {
		return new ResponseEntity<List<Review>>(this.reviewService.getAllReviews(companyId), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<String> addReview(@RequestParam Long companyId, @RequestBody Review review) {
		boolean status = this.reviewService.addReview(companyId, review);
		if (status) {
			this.messageProducer.sendMessage(review);
			return new ResponseEntity<String>("Review created successfully", HttpStatus.CREATED);
		}
		return new ResponseEntity<String>("Error in creating review", HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/{reviewId}")
	public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {

		Review review = this.reviewService.getReviewById(reviewId);
		if (review != null) {
			return new ResponseEntity<Review>(review, HttpStatus.OK);
		}
		return new ResponseEntity<Review>(review, HttpStatus.NOT_FOUND);
	}

	@PutMapping("/{reviewId}")
	public ResponseEntity<Review> updateCompanyReview(@PathVariable Long reviewId, @RequestBody Review review) {
		Review updatedReview = this.reviewService.updateCompanyReview(reviewId, review);
		if (updatedReview != null) {
			return new ResponseEntity<Review>(updatedReview, HttpStatus.OK);
		}
		return new ResponseEntity<Review>(updatedReview, HttpStatus.NOT_MODIFIED);
	}

	@DeleteMapping("/{reviewId}")
	public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
		boolean deleteStatus = this.reviewService.deleteReviewForCompany(reviewId);

		if (deleteStatus) {
			return new ResponseEntity<String>("Review deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Error in deleting review for company", HttpStatus.BAD_REQUEST);

	}

	@GetMapping("/averageReview")
	public Double getMethodName(@RequestParam Long companyId) {
		List<Review> reviewList = this.reviewService.getAllReviews(companyId);
		double averageRating = reviewList.stream().mapToDouble(Review::getRating).average().getAsDouble();

		return averageRating;
	}

}

//GET /reviews?companyId={companyId}
//POST /reviews?companyId={companyId}
//GET /reviews/{reviewId}
//PUT /reviews/{reviewId}
//DELETE /reviews/{reviewId}
