package net.ufjnet.calendar.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
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
