package in.cmss.review.controllers;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import in.cmss.review.entities.Review;
import in.cmss.review.services.ReviewService;

@ExtendWith(MockitoExtension.class)
class ReviewControllerTest {

	@InjectMocks
	private ReviewController controller;
		
	@Mock
	private ReviewService reviewService;

	private long companyId;

	@BeforeEach
	void setUp() {
		companyId = 1;
	}

	@Test
	void getAllReviewTest_Success() throws Exception {

		List<Review> list = new ArrayList<>();
		list.add(new Review(1l, "first", "first review", 1));
		
		
		when(this.reviewService.getAllReviews(companyId)).thenReturn(list);
		
		
	}

}
