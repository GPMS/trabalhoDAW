package net.ufjnet.calendar.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
import net.ufjnet.calendar.services.CategoryService;

@RestController
@RequestMapping("/v1/cal/categories")
@Tag(name = "Category's Endpoint")
public class CategoryController {

	@Autowired
	private CategoryService service;
	
	@GetMapping
	@Operation(summary = "Finds all categories")
	public ResponseEntity<CollectionModel<CategoryDTO>> Findall(@RequestParam(value = "page", defaultValue = "0") int page,
															@RequestParam(value = "limit", defaultValue = "12") int limit,
															@RequestParam(value = "direction", defaultValue = "asc") String direction) {

		Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "name"));

		Page<CategoryDTO> pages = service.FindAll(pageable);
		pages
			.stream()
			.forEach(p -> p.add(
						linkTo(methodOn(CategoryController.class).FindOne(p.getId())).withSelfRel()
					)
			);

		return ResponseEntity.ok(CollectionModel.of(pages));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Finds one category given its id")
	public ResponseEntity<CategoryDTO> FindOne(@PathVariable Integer id) {
		CategoryDTO objDTO = service.FindByID(id);
		objDTO.add(linkTo(methodOn(CategoryController.class).FindOne(id)).withSelfRel());
		return ResponseEntity.ok(objDTO);
	}

	@GetMapping("/name/{name}")
	@Operation(summary = "Finds one category given its name")
	public ResponseEntity<CategoryDTO> FindName(@PathVariable String name) {
		CategoryDTO objDTO = service.FindByName(name);
		objDTO.add(linkTo(methodOn(CategoryController.class).FindName(name)).withSelfRel());
		return ResponseEntity.ok(objDTO);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Inserts a new category in the Database")
	public ResponseEntity<CategoryDTO> Save(@Valid @RequestBody CategoryDTO objBody) {
		CategoryDTO objDTO = service.Save(objBody);
		objDTO.add(linkTo(methodOn(CategoryController.class).FindOne(objDTO.getId())).withSelfRel());
		return ResponseEntity.ok(objDTO);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Updates one category given its id")
	public ResponseEntity<CategoryDTO> Update(@PathVariable Integer id, @Valid @RequestBody CategoryDTO objBody) {
		if (!service.ExistsByID(id)) {
			return ResponseEntity.notFound().build();
		}
		CategoryDTO objDTO = service.Update(objBody);
		objDTO.add(linkTo(methodOn(CategoryController.class).FindOne(objDTO.getId())).withSelfRel());
		return ResponseEntity.ok(objDTO);
	}
 
	@DeleteMapping("/{id}")
	@Operation(summary = "Deletes one category given its id")
	public ResponseEntity<Void> Delete(@PathVariable Integer id) {
		if (!service.ExistsByID(id)) {
			return ResponseEntity.notFound().build();
		}
		
		service.DeleteByID(id);
		
		return ResponseEntity.noContent().build();
	}
	
}
