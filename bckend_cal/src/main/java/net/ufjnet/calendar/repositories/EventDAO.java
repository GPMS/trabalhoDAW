package net.ufjnet.calendar.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.ufjnet.calendar.models.Event;

public interface EventDAO extends JpaRepository<Event, Integer> {

	public Optional<Event> findByTitle(String title);
	
	@Query("FROM Event e WHERE e.user.email = :email")
	public Page<Event> findByUserEmail(@Param("email") String email, Pageable pageable);
}
