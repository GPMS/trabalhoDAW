package net.ufjnet.calendar.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.ufjnet.calendar.dtos.CategoryDTO;
import net.ufjnet.calendar.dtos.EventDTO;
import net.ufjnet.calendar.security.UserDTO;
import net.ufjnet.calendar.services.CategoryService;
import net.ufjnet.calendar.services.EventService;
import net.ufjnet.calendar.services.UserService;

@RestController
@RequestMapping("/v1/cal/users")
@Tag(name = "User's Endpoint")
public class UserController {

	@Autowired
	private UserService service;
	
	@Autowired 
	private EventService eventService;
	
	@Autowired 
	private CategoryService categoryService;
	
	@Autowired
	private HttpServletRequest request;
	
	@GetMapping
	@Operation(summary = "Finds all users")
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
	
	@GetMapping("/events")
	@Operation(summary = "Finds all events of current logged user")
	public ResponseEntity<CollectionModel<EventDTO>> FindAllEvents(@RequestParam(value = "page", defaultValue = "0") int page,
																   @RequestParam(value = "limit", defaultValue = "12") int limit,
																   @RequestParam(value = "direction", defaultValue = "asc") String direction) {
		Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "title"));

		Page<EventDTO> pages = eventService.FindByUserEmail(request.getUserPrincipal().getName(), pageable);
		pages
			.stream()
			.forEach(p -> p.add(
						linkTo(methodOn(EventController.class).FindOne(p.getId())).withSelfRel()
					)
			);

		return ResponseEntity.ok(CollectionModel.of(pages));
	}
	
	@GetMapping("/categories")
	@Operation(summary = "Finds all categories of current logged user")
	public ResponseEntity<CollectionModel<CategoryDTO>> FindAllCategories(@RequestParam(value = "page", defaultValue = "0") int page,
																		  @RequestParam(value = "limit", defaultValue = "12") int limit,
																		  @RequestParam(value = "direction", defaultValue = "asc") String direction) {
		Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "name"));

		Page<CategoryDTO> pages = categoryService.FindByUserEmail(request.getUserPrincipal().getName(), pageable);
		pages
			.stream()
			.forEach(p -> p.add(
						linkTo(methodOn(CategoryController.class).FindOne(p.getId())).withSelfRel()
					)
			);

		return ResponseEntity.ok(CollectionModel.of(pages));
	}
	
	@PostMapping("/category")
	@Operation(summary = "Add a new category to the current logged user")
	public ResponseEntity<CategoryDTO> FindAllCategories(@Valid @RequestBody CategoryDTO category) {
		UserDTO currentUser = service.FindByEmail(request.getUserPrincipal().getName());
		category.setUser(currentUser);
		categoryService.Save(category);
		return ResponseEntity.ok(category);
	}
	
	@PostMapping("/event")
	@Operation(summary = "Add a new event to the current logged user")
	public ResponseEntity<EventDTO> FindAllCategories(@Valid @RequestBody EventDTO event) {
		UserDTO currentUser = service.FindByEmail(request.getUserPrincipal().getName());
		event.setUser(currentUser);
		eventService.Save(event);
		return ResponseEntity.ok(event);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Finds one user given his id")
	public ResponseEntity<UserDTO> FindOne(@PathVariable Integer id) {
		UserDTO objDTO = service.FindByID(id);
		objDTO.add(linkTo(methodOn(UserController.class).FindOne(id)).withSelfRel());
		return ResponseEntity.ok(objDTO);
	}

	@GetMapping("/name/{name}")
	@Operation(summary = "Finds one user given his name")
	public ResponseEntity<UserDTO> FindName(@PathVariable String name) {
		UserDTO objDTO = service.FindByName(name);
		objDTO.add(linkTo(methodOn(UserController.class).FindName(name)).withSelfRel());
		return ResponseEntity.ok(objDTO);
	}

	@GetMapping("/email/{email}")
	@Operation(summary = "Finds one user given his e-mail")
	public ResponseEntity<UserDTO> FindEmail(@PathVariable String email) {
		UserDTO objDTO = service.FindByEmail(email);
		objDTO.add(linkTo(methodOn(UserController.class).FindEmail(email)).withSelfRel());
		return ResponseEntity.ok(objDTO);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Inserts a new user in the Database")
	public ResponseEntity<UserDTO> Save(@Valid @RequestBody UserDTO objBody) {
		UserDTO objDTO = service.Save(objBody);
		objDTO.add(linkTo(methodOn(UserController.class).FindOne(objDTO.getId())).withSelfRel());
		return ResponseEntity.ok(objDTO);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Updates one user given his id")
	public ResponseEntity<UserDTO> Update(@PathVariable Integer id, @Valid @RequestBody UserDTO objBody) {
		if (!service.ExistsByID(id)) {
			return ResponseEntity.notFound().build();
		}
		objBody.setId(id);
		UserDTO objDTO = service.Save(objBody);
		objDTO.add(linkTo(methodOn(UserController.class).FindOne(objDTO.getId())).withSelfRel());
		return ResponseEntity.ok(objDTO);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deletes one user given his id")
	public ResponseEntity<Void> Delete(@PathVariable Integer id) {
		if (!service.ExistsByID(id)) {
			return ResponseEntity.notFound().build();
		}
		
		service.DeleteByID(id);
		
		return ResponseEntity.noContent().build();
	}
	
}
