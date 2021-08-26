package net.ufjnet.calendar.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	@Column(name = "category_id")
	private Integer id;
	
	@Column(name = "category_name", nullable = false)
	private String name;

	@Column(name = "category_color", nullable = false)
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