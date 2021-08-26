package net.ufjnet.calendar.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "EVENTS")
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "event_id")
	private Integer id;
	
	@Column(name = "event_title", nullable = false)
	private String title;

	@Column(name = "event_description")
	private String description;
	
	@Column(name = "event_timeBegin", nullable = false)
	private LocalDateTime timeBegin;
	
	@Column(name = "event_timeEnd", nullable = false)
	private LocalDateTime timeEnd;

	@ManyToOne()
	private User user;
	
	@ManyToOne()
	private Category category;
	
	public Event(Integer id, String title, LocalDateTime timeBegin, LocalDateTime timeEnd, User user) {
		this.id = id;
		this.title = title;
		this.description = "";
		this.timeBegin = timeBegin;
		this.timeEnd = timeEnd;
		this.user = user;
		this.category = null;
	}
	
	public Event(Integer id, String title, String description, LocalDateTime timeBegin, LocalDateTime timeEnd, User user) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.timeBegin = timeBegin;
		this.timeEnd = timeEnd;
		this.user = user;
		this.category = null;
	}
	
	public Event(Integer id, String title, LocalDateTime timeBegin, LocalDateTime timeEnd, User user, Category category) {
		this.id = id;
		this.title = title;
		this.description = "";
		this.timeBegin = timeBegin;
		this.timeEnd = timeEnd;
		this.user = user;
		this.category = category;
	}
}