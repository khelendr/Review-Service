package in.cmss.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cmss.dto.ReviewMessage;
import in.cmss.review.entities.Review;

@Service
public class ReviewMessageProducer {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	
	public void sendMessage(Review review) {
		ReviewMessage reviewMessage = new ReviewMessage();
		reviewMessage.setId(review.getId());
		reviewMessage.setTitle(review.getTitle());
		reviewMessage.setRating(review.getRating());
		reviewMessage.setDescription(review.getDescription());
		reviewMessage.setCompanyId(review.getCompanyId());
		
		rabbitTemplate.convertAndSend("companyRatingQueue", reviewMessage);
	}
}
