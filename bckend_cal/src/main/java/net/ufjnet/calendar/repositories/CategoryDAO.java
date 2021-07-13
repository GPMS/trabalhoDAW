package net.ufjnet.calendar.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ufjnet.calendar.models.Category;
import net.ufjnet.calendar.models.User;

public interface CategoryDAO extends JpaRepository<Category, Integer> {

	public Optional<Category> findByName(String name);
	
}
