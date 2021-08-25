package net.ufjnet.calendar.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.ufjnet.calendar.models.Category;

public interface CategoryDAO extends JpaRepository<Category, Integer> {

	public Optional<Category> findByName(String name);
	

	@Query("FROM Category e WHERE e.user.id = :findID")
	public Page<Category> findByUserID(@Param("findID") Integer id, Pageable pageable);
}
