package net.ufjnet.calendar.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ufjnet.calendar.models.User;
import net.ufjnet.calendar.services.UserService;

@RestController
@RequestMapping("/cal/users")
public class UserController {

	@Autowired
	private UserService service;
	
	@GetMapping
	public List<User> FindAll() {
		return service.FindAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> FindOne(@PathVariable Integer id) {
		return service.FindByID(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<User> Save(@RequestBody User obj) {
		obj = service.Save(obj);
		return ResponseEntity.created(null).body(obj);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> Update(@PathVariable Integer id, @RequestBody User obj) {
		if (!service.ExistsByID(id)) {
			return ResponseEntity.notFound().build();
		}
		obj.setId(id);
		obj = service.Save(obj);
		return ResponseEntity.ok(obj);
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
