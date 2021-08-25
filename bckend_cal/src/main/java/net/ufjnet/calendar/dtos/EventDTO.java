package net.ufjnet.calendar.dtos;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.ufjnet.calendar.models.Event;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@JsonPropertyOrder({"id_event", "title_event", "description_event", "timeBegin_event", "timeEnd_event"})
public class EventDTO extends RepresentationModel<EventDTO> implements Serializable {
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@JsonProperty("id_event")
	private Integer id;
	
	@NotBlank
	@Size(max=255)
	@JsonProperty("title_event")
	private String title;
	
	@Size(max=255)
	@JsonProperty("description_event")
	private String description;
	
	// "13-07-2021 11:07"
	@NotNull
	@Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-((19|2[0-9])[0-9]{2}) ([01][0-9]|2[0-3]):([0-5][0-9])$")
	@JsonProperty("timeBegin_event")
	private String timeBegin;
	
	@NotNull
	@Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-((19|2[0-9])[0-9]{2}) ([01][0-9]|2[0-3]):([0-5][0-9])$")
	@JsonProperty("timeEnd_event")
	private String timeEnd;
	
	@ConvertGroup(from = Default.class, to = ValidationGroups.UserID.class)
	@NotNull
	@Valid
	private UserDTO user;
	
	@ConvertGroup(from = Default.class, to = ValidationGroups.CategoryID.class)
	@Valid
	private CategoryDTO category;
	
	public EventDTO(Event obj) {
		this.id = obj.getId();
		this.title = obj.getTitle();
		this.description = obj.getDescription();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		this.timeBegin = obj.getTimeBegin().format(formatter);
		this.timeEnd = obj.getTimeEnd().format(formatter);
		
		this.user = new UserDTO(obj.getUser());
		
		if (obj.getCategory() != null) {
			this.category = new CategoryDTO(obj.getCategory());
		}
	}
}