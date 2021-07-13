package net.ufjnet.calendar.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "CATEGORIES")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_category")
	private Integer id;
	
	@Column(name = "name_category", nullable = false)
	private String name;

	@Column(name = "color_category", nullable = false)
	private String color;
	
	@ManyToOne()
	private User user;
	
	@OneToMany(mappedBy = "category")
	private List<Event> events = new ArrayList<>();
	
	public Category(Integer id, String name, String color, User user) {
		this.id = id;
		this.name = name;
		this.color = color;
		this.user = user;
	}
}