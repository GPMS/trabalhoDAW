package net.ufjnet.calendar.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ufjnet.calendar.models.User;

public interface UserDAO extends JpaRepository<User, Integer> {

	public Optional<User> findByName(String name);
	public Optional<User> findByEmail(String email);
	
}
