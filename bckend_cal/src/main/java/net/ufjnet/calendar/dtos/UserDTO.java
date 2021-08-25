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
@JsonPropertyOrder({"id_user", "name_user", "email_user"})
public class UserDTO extends RepresentationModel<UserDTO> implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull(groups = ValidationGroups.UserID.class)
	@EqualsAndHashCode.Include
	@JsonProperty("id_user")
	private Integer id;
	
	@NotBlank
	@Size(max=255)
	@JsonProperty("name_user")
	private String name;
	
	@NotBlank
	@Email
	@Size(max=320)
	@JsonProperty("email_user")
	private String email;
	
	public UserDTO(User obj) {
		this.id = obj.getId();
		this.name = obj.getName();
		this.email = obj.getEmail();
	}
}