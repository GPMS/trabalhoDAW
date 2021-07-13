package net.ufjnet.calendar.dtos;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
import net.ufjnet.calendar.dtos.ValidationGroups.UserID;
import net.ufjnet.calendar.models.Category;
import net.ufjnet.calendar.models.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@JsonPropertyOrder({"id_category", "name_category", "color_category"})
public class CategoryDTO extends RepresentationModel<CategoryDTO> implements Serializable {
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@JsonProperty("id_category")
	private Integer id;
	
	@NotBlank
	@Size(max=255)
	@JsonProperty("name_category")
	private String name;
	
	@NotBlank
	@Size(max=7)
	@JsonProperty("color_category")
	private String color;
	
	@ConvertGroup(from = Default.class, to = ValidationGroups.UserID.class)
	@NotNull
	@Valid
	private UserDTO user;
	
	public CategoryDTO(Category obj) {
		this.id = obj.getId();
		this.name = obj.getName();
		this.color = obj.getColor();
		user = new UserDTO(obj.getUser());
	}
}