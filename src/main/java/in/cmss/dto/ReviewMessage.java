package in.cmss.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewMessage {
	private Long id;
	private String title;
	private String description;
	private double rating;
	private Long companyId;
	
}
