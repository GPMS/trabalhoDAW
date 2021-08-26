package net.ufjnet.calendar.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import net.ufjnet.calendar.dtos.CategoryDTO;
import net.ufjnet.calendar.models.Category;
import net.ufjnet.calendar.models.User;
import net.ufjnet.calendar.repositories.CategoryDAO;
import net.ufjnet.calendar.repositories.UserDAO;
import net.ufjnet.calendar.services.exceptions.BusinessException;

@AllArgsConstructor
@Service
public class CategoryService {
	
	private CategoryDAO dao;
	private UserDAO userDAO;
	
	@Transactional(readOnly = true)
	public Page<CategoryDTO> FindAll(Pageable pageable) {
		return dao.findAll(pageable).map(obj -> new CategoryDTO(obj));
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO FindByID(Integer id) {
		Category result = dao.findById(id).orElseThrow(() -> new BusinessException("Registros não encontrados!!!"));
		
		return new CategoryDTO(result);

	}
	
	@Transactional(readOnly = true)
	public CategoryDTO FindByName(String name) {
		Category result = dao.findByName(name).orElseThrow(() -> new BusinessException("Registros não encontrados!!!"));
		
		return new CategoryDTO(result);
		
	}
	
	@Transactional(readOnly = true)
	public Page<CategoryDTO> FindByUserEmail(String email, Pageable pageable) {
		return dao.findByUserEmail(email, pageable).map(obj -> new CategoryDTO(obj));
	}
	
	@Transactional
	public CategoryDTO Save(CategoryDTO obj) {
		Category entity = new Category(obj.getId(), obj.getName(), obj.getColor(), 
				new User(obj.getUser().getId(), obj.getUser().getName(), obj.getUser().getEmail(), obj.getUser().getPassword()));
		
		Optional<User> user = userDAO.findById(obj.getUser().getId());
		entity.setUser(user.orElse(null));
		
		boolean nameExists = dao.findByName(obj.getName())
				.stream()
				.anyMatch(objResult -> !objResult.getName().equals(obj.getName()));
		
		if (nameExists) {
			throw new BusinessException("Nome já existente!");
		}
		
		return new CategoryDTO(dao.save(entity));
	}
	
	
	@Transactional
	public CategoryDTO Update(CategoryDTO obj) {
		Category entity = dao.findById(obj.getId())
				.orElseThrow(() -> new BusinessException("User not found!"));
		
		boolean nameExists = dao.findByName(obj.getName())
				.stream()
				.anyMatch(objResult -> !objResult.getName().equals(obj.getName()));
		if (nameExists) {
			throw new BusinessException("Nome já existente!");
		}
		
		entity.setId(obj.getId());
		entity.setName(obj.getName());
		entity.setColor(obj.getColor());
		
		return new CategoryDTO(dao.save(entity));
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
