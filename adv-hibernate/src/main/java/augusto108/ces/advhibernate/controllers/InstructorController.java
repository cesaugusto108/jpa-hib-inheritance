package augusto108.ces.advhibernate.controllers;

import augusto108.ces.advhibernate.domain.entities.Person;
import augusto108.ces.advhibernate.services.PersonService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
