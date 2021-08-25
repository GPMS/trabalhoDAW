package net.ufjnet.calendar.dtos;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.ufjnet.calendar.models.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@JsonPropertyOrder({"user_id", "user_name", "user_email"})
public class UserDTO extends RepresentationModel<UserDTO> implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull(groups = ValidationGroups.UserID.class)
	@EqualsAndHashCode.Include
	@JsonProperty("user_id")
	private Integer id;
	
	@NotBlank
	@Size(max=255)
	@JsonProperty("user_name")
	private String name;
	
	@NotBlank
	@Email
	@Size(max=320)
	@JsonProperty("user_email")
	private String email;
	
	public UserDTO(User obj) {
		this.id = obj.getId();
		this.name = obj.getName();
		this.email = obj.getEmail();
	}
}