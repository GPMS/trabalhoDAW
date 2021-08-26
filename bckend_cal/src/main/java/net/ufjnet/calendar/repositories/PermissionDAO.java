package net.ufjnet.calendar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ufjnet.calendar.models.Permission;

public interface PermissionDAO extends JpaRepository<Permission, Integer> {

}
