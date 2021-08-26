package net.ufjnet.calendar.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.ufjnet.calendar.models.User;

public interface UserDAO extends JpaRepository<User, Integer> {

	public Optional<User> findByName(String name);
	public Optional<User> findByEmail(String email);
	
	@Query("SELECT obj FROM User obj WHERE obj.email = :username")
	User findByUsername(@Param("username") String userName);
	
}
