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
import net.ufjnet.calendar.dtos.EventDTO;
import net.ufjnet.calendar.models.Event;
import net.ufjnet.calendar.services.EventService;

@RestController
@RequestMapping("/v1/cal/events")
@Tag(name = "Event's Endpoint")
public class EventController {

	@Autowired
	private EventService service;
	
	@GetMapping
	@Operation(summary = "Finds all events")
	public ResponseEntity<CollectionModel<EventDTO>> Findall(@RequestParam(value = "page", defaultValue = "0") int page,
															@RequestParam(value = "limit", defaultValue = "12") int limit,
															@RequestParam(value = "direction", defaultValue = "asc") String direction) {

		Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "title"));

		Page<EventDTO> pages = service.FindAll(pageable);
		pages
			.stream()
			.forEach(p -> p.add(
						linkTo(methodOn(EventController.class).FindOne(p.getId())).withSelfRel()
					)
			);

		return ResponseEntity.ok(CollectionModel.of(pages));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Finds one event given its id")
	public ResponseEntity<EventDTO> FindOne(@PathVariable Integer id) {
		EventDTO objDTO = service.FindByID(id);
		objDTO.add(linkTo(methodOn(EventController.class).FindOne(id)).withSelfRel());
		return ResponseEntity.ok(objDTO);
	}

	@GetMapping("/title/{title}")
	@Operation(summary = "Finds one event given its title")
	public ResponseEntity<EventDTO> FindName(@PathVariable String title) {
		EventDTO objDTO = service.FindByName(title);
		objDTO.add(linkTo(methodOn(EventController.class).FindName(title)).withSelfRel());
		return ResponseEntity.ok(objDTO);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Inserts a new event in the Database")
	public ResponseEntity<EventDTO> Save(@Valid @RequestBody EventDTO objBody) {
		EventDTO objDTO = service.Save(objBody);
		objDTO.add(linkTo(methodOn(EventController.class).FindOne(objDTO.getId())).withSelfRel());
		return ResponseEntity.ok(objDTO);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Updates one event given its id")
	public ResponseEntity<EventDTO> Update(@PathVariable Integer id, @Valid @RequestBody EventDTO objBody) {
		if (!service.ExistsByID(id)) {
			return ResponseEntity.notFound().build();
		}
		EventDTO objDTO = service.Update(objBody);
		objDTO.add(linkTo(methodOn(EventController.class).FindOne(objDTO.getId())).withSelfRel());
		return ResponseEntity.ok(objDTO);
	}
 
	@DeleteMapping("/{id}")
	@Operation(summary = "Deletes one event given its id")
	public ResponseEntity<Void> Delete(@PathVariable Integer id) {
		if (!service.ExistsByID(id)) {
			return ResponseEntity.notFound().build();
		}
		
		service.DeleteByID(id);
		
		return ResponseEntity.noContent().build();
	}
	
}
