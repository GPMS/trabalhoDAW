package net.ufjnet.calendar.controllers;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.ufjnet.calendar.dtos.UserDTO;
import net.ufjnet.calendar.models.User;
import net.ufjnet.calendar.services.UserService;

@RestController
@RequestMapping("/v1/cal/users")
public class UserController {

	@Autowired
	private UserService service;
	
	@GetMapping
	public ResponseEntity<CollectionModel<UserDTO>> Findall(@RequestParam(value = "page", defaultValue = "0") int page,
															@RequestParam(value = "limit", defaultValue = "12") int limit,
															@RequestParam(value = "direction", defaultValue = "asc") String direction) {

		Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "name"));

		Page<UserDTO> pages = service.FindAll(pageable);
		pages
			.stream()
			.forEach(p -> p.add(
						linkTo(methodOn(UserController.class).FindOne(p.getId())).withSelfRel()
					)
			);

		return ResponseEntity.ok(CollectionModel.of(pages));
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> FindOne(@PathVariable Integer id) {
		UserDTO objDTO = service.FindByID(id);
		objDTO.add(linkTo(methodOn(UserController.class).FindOne(id)).withSelfRel());
		return ResponseEntity.ok(objDTO);
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<UserDTO> FindName(@PathVariable String name) {
		UserDTO objDTO = service.FindByName(name);
		objDTO.add(linkTo(methodOn(UserController.class).FindName(name)).withSelfRel());
		return ResponseEntity.ok(objDTO);
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<UserDTO> FindEmail(@PathVariable String email) {
		UserDTO objDTO = service.FindByEmail(email);
		objDTO.add(linkTo(methodOn(UserController.class).FindEmail(email)).withSelfRel());
		return ResponseEntity.ok(objDTO);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<UserDTO> Save(@RequestBody User objBody) {
		UserDTO objDTO = service.Save(objBody);
		objDTO.add(linkTo(methodOn(UserController.class).FindOne(objDTO.getId())).withSelfRel());
		return ResponseEntity.ok(objDTO);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> Update(@PathVariable Integer id, @RequestBody User objBody) {
		if (!service.ExistsByID(id)) {
			return ResponseEntity.notFound().build();
		}
		objBody.setId(id);
		UserDTO objDTO = service.Save(objBody);
		objDTO.add(linkTo(methodOn(UserController.class).FindOne(objDTO.getId())).withSelfRel());
		return ResponseEntity.ok(objDTO);
	}		

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> Delete(@PathVariable Integer id) {
		if (!service.ExistsByID(id)) {
			return ResponseEntity.notFound().build();
		}
		
		service.DeleteByID(id);
		
		return ResponseEntity.noContent().build();
	}
	
}
