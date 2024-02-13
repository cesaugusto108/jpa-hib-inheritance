package augusto108.ces.advhibernate.controllers;

import augusto108.ces.advhibernate.domain.entities.Employee;
import augusto108.ces.advhibernate.domain.entities.Person;
import augusto108.ces.advhibernate.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final PersonService service;

    @Autowired
    public EmployeeController(PersonService service) {
        this.service = service;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Employee>> getEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int max
    ) {
        return ResponseEntity.ok(service.getEmployees(page, max));
    }

    @GetMapping(value = "/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> getEmployeeById(@PathVariable("employeeId") Integer id) {
        return ResponseEntity.ok(service.getPersonById(id));
    }
}
