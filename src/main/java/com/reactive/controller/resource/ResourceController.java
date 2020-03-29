package com.reactive.controller.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactive.model.Employee;
import com.reactive.repository.EmployeeService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/rest/employee")
public class ResourceController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/save")	
	public Mono<ResponseEntity<Employee>> save(@RequestBody Employee emp) {
		return employeeService.save(emp)
				.flatMap(employee->
						Mono.just(ResponseEntity.ok().body(employee)));
	}

	@GetMapping("/all")
	public Mono<ResponseEntity<List<Employee>>> getAll() {
		return employeeService.findAll()
				.collectList()
				.flatMap(list->Mono.just(ResponseEntity.ok().body(list)));
	}

	@GetMapping("/get/{id}")
	public Mono<ResponseEntity<Employee>> getId(@PathVariable String id) {
		return employeeService.findById(id)
				.flatMap(emp-> 
						Mono.just(ResponseEntity.ok().body(emp)));
	}
	
	@PutMapping("/update")
	public Mono<ResponseEntity<Employee>> update(@RequestBody Employee emp) {
		return employeeService.update(emp)
				.flatMap(employee-> 
						Mono.just(ResponseEntity.ok().body(employee)));
	}
	
	@DeleteMapping("/delete/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
		return employeeService.delete(id)
				.then(Mono.just(ResponseEntity.accepted().build()));
	}

}
