package net.ufjnet.calendar.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
@Table(name = "EVENTS")
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_event")
	private Integer id;
	
	@Column(name = "title_event", nullable = false)
	private String title;

	@Column(name = "description_event")
	private String description;
	
	@Column(name = "timeBegin_event", nullable = false)
	private String timeBegin;
	
	@Column(name = "timeEnd_event", nullable = false)
	private String timeEnd;
	
	@ManyToOne()
	private User user;
	
	@ManyToOne()
	private Category category;
	
	public Event(Integer id, String title, String timeBegin, String timeEnd, User user) {
		this.id = id;
		this.title = title;
		this.description = "";
		this.timeBegin = timeBegin;
		this.timeEnd = timeEnd;
		this.user = user;
		this.category = null;
	}
	
	public Event(Integer id, String title, String description, String timeBegin, String timeEnd, User user) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.timeBegin = timeBegin;
		this.timeEnd = timeEnd;
		this.user = user;
		this.category = null;
	}
	
	public Event(Integer id, String title, String timeBegin, String timeEnd, User user, Category category) {
		this.id = id;
		this.title = title;
		this.description = "";
		this.timeBegin = timeBegin;
		this.timeEnd = timeEnd;
		this.user = user;
		this.category = category;
	}
}