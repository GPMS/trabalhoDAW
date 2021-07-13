package net.ufjnet.calendar.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ufjnet.calendar.models.Category;
import net.ufjnet.calendar.models.Event;
import net.ufjnet.calendar.models.User;

public interface EventDAO extends JpaRepository<Event, Integer> {

	public Optional<Event> findByTitle(String title);
	
}
