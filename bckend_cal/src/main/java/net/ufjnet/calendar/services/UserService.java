package net.ufjnet.calendar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import net.ufjnet.calendar.models.User;
import net.ufjnet.calendar.repositories.UserDAO;
import net.ufjnet.calendar.services.exceptions.BusinessException;

@AllArgsConstructor
@Service
public class UserService {

	private UserDAO dao;
	
	public List<User> FindAll() {
		return dao.findAll();
	}
	
	public Optional<User> FindByID(Integer id) {
		return dao.findById(id);
	}
	
	public Optional<User> FindByName(String name) {
		return dao.findByName(name);
	}
	
	public Optional<User> FindByEmail(String email) {
		return dao.findByEmail(email);
	}
	
	@Transactional
	public User Save(User obj) {
		boolean emailExists = dao.findByEmail(obj.getEmail())
				.stream()
				.anyMatch(objResult -> !objResult.equals(obj));
		if (emailExists) {
			throw new BusinessException("E-mail já existente!");
		}
		
		return dao.save(obj);
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
