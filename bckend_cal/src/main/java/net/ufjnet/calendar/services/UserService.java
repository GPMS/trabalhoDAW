package net.ufjnet.calendar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import net.ufjnet.calendar.models.User;
import net.ufjnet.calendar.repositories.UserDAO;

@AllArgsConstructor
@Service
public class UserService {

	private UserDAO dao;
	
	@Transactional
	public List<User> FindAll() {
		return dao.findAll();
	}
	
	@Transactional
	public Optional<User> FindByID(Integer id) {
		return dao.findById(id);
	}
	
	@Transactional
	public User Save(User obj) {
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
