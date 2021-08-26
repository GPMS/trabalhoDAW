package net.ufjnet.calendar.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import net.ufjnet.calendar.dtos.EventDTO;
import net.ufjnet.calendar.models.Category;
import net.ufjnet.calendar.models.Event;
import net.ufjnet.calendar.models.User;
import net.ufjnet.calendar.repositories.CategoryDAO;
import net.ufjnet.calendar.repositories.EventDAO;
import net.ufjnet.calendar.repositories.UserDAO;
import net.ufjnet.calendar.services.exceptions.BusinessException;

@AllArgsConstructor
@Service
public class EventService {
	
	private EventDAO dao;
	private CategoryDAO categoryDAO;
	private UserDAO userDAO;
	
	@Transactional(readOnly = true)
	public Page<EventDTO> FindAll(Pageable pageable) {
		return dao.findAll(pageable).map(obj -> new EventDTO(obj));
	}
	
	@Transactional(readOnly = true)
	public EventDTO FindByID(Integer id) {
		Event result = dao.findById(id).orElseThrow(() -> new BusinessException("Registros não encontrados!!!"));
		
		return new EventDTO(result);

	}
	
	@Transactional(readOnly = true)
	public EventDTO FindByName(String title) {
		Event result = dao.findByTitle(title).orElseThrow(() -> new BusinessException("Registros não encontrados!!!"));
		
		return new EventDTO(result);
		
	}
	
	@Transactional(readOnly = true)
	public Page<EventDTO> FindByUserEmail(String email, Pageable pageable) {
		return dao.findByUserEmail(email, pageable).map(obj -> new EventDTO(obj));
	}
	
	@Transactional
	public EventDTO Save(EventDTO obj) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		Event entity = new Event(obj.getId(), obj.getTitle(), obj.getDescription(), 
								 LocalDateTime.parse(obj.getTimeBegin(), formatter), LocalDateTime.parse(obj.getTimeEnd(), formatter),
								 new User(obj.getUser().getId(), obj.getUser().getName(), obj.getUser().getEmail()));
		
		Optional<User> user = userDAO.findById(obj.getUser().getId());
		entity.setUser(user.orElse(null));
		
		if (obj.getCategory() != null) {
			Optional<Category> category = categoryDAO.findById(obj.getCategory().getId());
			entity.setCategory(category.orElse(null));
		}
		
		boolean titleExists = dao.findByTitle(obj.getTitle())
				.stream()
				.anyMatch(objResult -> !objResult.equals(obj));
		
		if (titleExists) {
			throw new BusinessException("Título já existente!");
		}
		
		return new EventDTO(dao.save(entity));
	}
	
	
	@Transactional
	public EventDTO Update(EventDTO obj) {
		Event entity = dao.findById(obj.getId())
				.orElseThrow(() -> new BusinessException("User not found!"));
		
		boolean titleExists = dao.findByTitle(obj.getTitle())
				.stream()
				.anyMatch(objResult -> !objResult.equals(obj));
		if (titleExists) {
			throw new BusinessException("Nome já existente!");
		}
		
		entity.setId(obj.getId());
		entity.setTitle(obj.getTitle());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		entity.setTimeBegin(LocalDateTime.parse(obj.getTimeBegin(), formatter));
		entity.setTimeEnd(LocalDateTime.parse(obj.getTimeBegin(), formatter));
		
		return new EventDTO(dao.save(entity));
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
