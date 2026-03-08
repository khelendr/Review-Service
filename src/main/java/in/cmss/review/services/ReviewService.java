package in.cmss.review.services;

import java.util.List;

import in.cmss.review.entities.Review;

public interface ReviewService {

	public List<Review> getAllReviews(Long companyid);

	public boolean addReview(Long companyId, Review review);

	public Review getReviewById(Long reviewId);

	public Review updateCompanyReview(Long reviewId, Review review);

	public boolean deleteReviewForCompany(Long reviewId);
}
