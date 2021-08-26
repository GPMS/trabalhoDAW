package net.ufjnet.calendar.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import net.ufjnet.calendar.models.User;
import net.ufjnet.calendar.repositories.UserDAO;
import net.ufjnet.calendar.security.UserDTO;
import net.ufjnet.calendar.services.exceptions.BusinessException;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {
	
	private UserDAO dao;
	private SendEmailService mailService;
	private BCryptPasswordEncoder bCrypt;
	
	@Transactional(readOnly = true)
	public Page<UserDTO> FindAll(Pageable pageable) {
		return dao.findAll(pageable).map(obj -> new UserDTO(obj));
	}
	
	@Transactional(readOnly = true)
	public UserDTO FindByID(Integer id) {
		User result = dao.findById(id).orElseThrow(() -> new BusinessException("Registros não encontrados!!!"));
		
		return new UserDTO(result);

	}
	
	@Transactional(readOnly = true)
	public UserDTO FindByName(String name) {
		User result = dao.findByName(name).orElseThrow(() -> new BusinessException("Registros não encontrados!!!"));
		
		return new UserDTO(result);
		
	}
	
	@Transactional(readOnly = true)
	public UserDTO FindByEmail(String email) {
		User result = dao.findByEmail(email)
				.orElseThrow(() -> new BusinessException("Registros não encontrados!!!"));
		
		return new UserDTO(result);
		
	}
	
	@Transactional
	public UserDTO Save(UserDTO obj) {
		User entity = new User(obj.getId(), obj.getName(), obj.getEmail(), bCrypt.encode(obj.getPassword()));
		
		boolean emailExists = dao.findByEmail(obj.getEmail())
								 .stream()
								 .anyMatch(objResult -> !objResult.getEmail().equals(obj.getEmail()));
		if (emailExists) {
			throw new BusinessException("E-mail já existente!");
		}
		
		try {
			String mailText = "Your data was registrered in the calendar App!\nData:"
							  + "\n\tName: " + obj.getName() 
							  + "\n\tEmail: " + obj.getEmail() 
							  + "\n\tPassword: " + obj.getPassword();
			mailService.Send(obj.getEmail(), "Registration Complete!", mailText);
		} catch (Exception e) {
			throw new BusinessException("Erro no envio do email! " + e.getMessage());
		}
		
		return new UserDTO(dao.save(entity));
	}
	
	@Transactional
	public UserDTO Update(UserDTO obj) {
		User entity = dao.findById(obj.getId())
				.orElseThrow(() -> new BusinessException("User not found!"));
		
		boolean emailExists = dao.findByEmail(obj.getEmail())
				.stream()
				.anyMatch(objResult -> !objResult.getEmail().equals(obj.getEmail()));
		if (emailExists) {
			throw new BusinessException("E-mail já existente!");
		}
		
		entity.setId(obj.getId());
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPassword(bCrypt.encode(obj.getPassword()));
		
		return new UserDTO(dao.save(entity));
	}
	
	@Transactional
	public void DeleteByID(Integer id) {
		dao.deleteById(id);
	}
	
	@Transactional
	public boolean ExistsByID(Integer id) {
		return dao.existsById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = dao.findByUsername(username);
		if (user != null) {
			return user;
		} else {
			throw new UsernameNotFoundException("Usuário com o e-mail " + username + " não foi encontrado");
		}
	}

}
