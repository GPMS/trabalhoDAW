package net.ufjnet.calendar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ufjnet.calendar.models.User;

public interface UserDAO extends JpaRepository<User, Integer> {

}
