package net.ufjnet.calendar.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class StandardError {
	
	@AllArgsConstructor
	@Getter
	@Setter
	public static class Fields {
		private String name;
		private String message;
	}
	
	private Integer id;
	private LocalDateTime timestamp;
	private String description;
	private List<Fields> fields;
	
}
