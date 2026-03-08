package in.cmss.review.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import in.cmss.review.entities.Review;
import in.cmss.review.repositories.ReviewRepository;
import in.cmss.review.services.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository repository;

	@Override
	@Cacheable(value = "Review", key = "#id")
	public List<Review> getAllReviews(Long companyid) {
		List<Review> reviews = this.repository.findByCompanyId(companyid);
		return reviews;
	}

	@Override
	@CachePut(value = "Review", key = "#id")
	public boolean addReview(Long companyId, Review review) {

		try {

			if (companyId != null && review != null) {
				review.setCompanyId(companyId);
				this.repository.save(review);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	@CachePut(value = "Review", key = "#id")
	public Review getReviewById(Long reviewId) {
		Review review = this.repository.findById(reviewId).orElse(null);
		return review;
	}

	@Override
	@CachePut(value = "Review", key = "#id")
	public Review updateCompanyReview(Long reviewId, Review updateReview) {

		Review review = this.repository.findById(reviewId).orElse(null);
		if (review != null) {
//			review.setId(updateReview.getId());
			review.setTitle(updateReview.getTitle());
			review.setDescription(updateReview.getDescription());
			review.setRating(updateReview.getRating());
			review.setCompanyId(updateReview.getCompanyId());
			Review savedReview = this.repository.save(review);
			return savedReview;
		}
		return null;
	}

	@Override
	@CacheEvict(value = "Review", key = "#id")
	public boolean deleteReviewForCompany(Long reviewId) {

		Review review = this.repository.findById(reviewId).orElse(null);
		if (review != null) {
			this.repository.delete(review);
			return true;
		}
		return false;
	}

}
