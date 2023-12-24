package jmaster.io.notificationservice.model;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class MessageDTO {
	private String to;
	private String toName;
	private String subject;
	private String content;
	
}
