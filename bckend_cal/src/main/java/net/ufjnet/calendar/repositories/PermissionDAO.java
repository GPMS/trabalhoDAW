package net.ufjnet.calendar.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.ufjnet.calendar.models.Category;
import net.ufjnet.calendar.models.Permission;

public interface PermissionDAO extends JpaRepository<Permission, Integer> {

}
