package augusto108.ces.advhibernate.controllers;

import augusto108.ces.advhibernate.domain.entities.Person;
import augusto108.ces.advhibernate.services.PersonService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructors")
public class InstructorController {
    private final PersonService service;

    public InstructorController(PersonService service) {
        this.service = service;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Person>> getInstructors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int max
    ) {
        return ResponseEntity.ok(service.getInstructors(page, max));
    }

    @GetMapping(value = "/{instructorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> getInstructorById(@PathVariable("instructorId") Integer id) {
        return ResponseEntity.ok(service.getPersonById(id));
    }
}
