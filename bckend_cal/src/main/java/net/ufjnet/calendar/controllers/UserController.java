package net.ufjnet.calendar.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ufjnet.calendar.dtos.UserDTO;
import net.ufjnet.calendar.models.User;
import net.ufjnet.calendar.services.UserService;

@RestController
@RequestMapping("/cal/users")
public class UserController {

	@Autowired
	private UserService service;
	
	@GetMapping
	public ResponseEntity<Page<UserDTO>> FindAll(Pageable pageable) {
		Page<UserDTO> result = service.FindAll(pageable);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> FindOne(@PathVariable Integer id) {
		return service.FindByID(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<UserDTO> FindName(@PathVariable String name) {
		return service.FindByName(name)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<UserDTO> FindEmail(@PathVariable String email) {
		return service.FindByEmail(email)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> Save(@Valid @RequestBody User obj) {
		UserDTO objDTO = service.Save(obj);
		return ResponseEntity.created(null).body(objDTO);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> Update(@PathVariable Integer id, @Valid @RequestBody User obj) {
		if (!service.ExistsByID(id)) {
			return ResponseEntity.notFound().build();
		}
		obj.setId(id);
		UserDTO objDTO = service.Save(obj);
		return ResponseEntity.ok(objDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> Update(@PathVariable Integer id) {
		if (!service.ExistsByID(id)) {
			return ResponseEntity.notFound().build();
		}
		service.DeleteByID(id);
		return ResponseEntity.noContent().build();
	}
	
}
