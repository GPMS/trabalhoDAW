package net.ufjnet.calendar.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import net.ufjnet.calendar.dtos.UserDTO;
import net.ufjnet.calendar.models.User;
import net.ufjnet.calendar.repositories.UserDAO;
import net.ufjnet.calendar.services.exceptions.BusinessException;

@AllArgsConstructor
@Service
public class UserService {

	private UserDAO dao;
	
	@Transactional(readOnly = true)
	public Page<UserDTO> FindAll(Pageable pageable) {
		return dao.findAll(pageable).map(obj -> new UserDTO(obj));
	}
	
	@Transactional(readOnly = true)
	public Optional<UserDTO> FindByID(Integer id) {
		return dao.findById(id).map(obj -> new UserDTO(obj));
	}
	
	@Transactional(readOnly = true)
	public Optional<UserDTO> FindByName(String name) {
		return dao.findByName(name).map(obj -> new UserDTO(obj));
	}
	
	@Transactional(readOnly = true)
	public Optional<UserDTO> FindByEmail(String email) {
		return dao.findByEmail(email).map(obj -> new UserDTO(obj));
	}
	
	@Transactional
	public UserDTO Save(User obj) {
		boolean emailExists = dao.findByEmail(obj.getEmail())
				.stream()
				.anyMatch(objResult -> !objResult.equals(obj));
		if (emailExists) {
			throw new BusinessException("E-mail já existente!");
		}
		
		return new UserDTO(dao.save(obj));
	}
	
	@Transactional
	public void DeleteByID(Integer id) {
		dao.deleteById(id);
	}
	
	@Transactional
	public boolean ExistsByID(Integer id) {
		return dao.existsById(id);
	}
	
}
