package com.reactive.controller.client;

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
import com.reactive.util.RestClient;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/rest/employee/client")
public class ClientController {
	

	@Autowired
	private RestClient restClient;
	
	@PostMapping("/save")	
	public Mono<ResponseEntity<Employee>> save(@RequestBody Employee emp) {
		return restClient.saveEmp(Mono.just(emp))
				.flatMap(employee->
						Mono.just(ResponseEntity.ok().body(employee)));
	}

	@GetMapping("/all")
	public Mono<ResponseEntity<List<Employee>>> getAll() {
		return restClient.getAllEmp()
				.collectList()
				.flatMap(list->Mono.just(ResponseEntity.ok().body(list)));
	}

	@GetMapping("/get/{id}")
	public Mono<ResponseEntity<Employee>> getId(@PathVariable String id) {
		return restClient.getEmpById(id)
				.flatMap(emp-> 
						Mono.just(ResponseEntity.ok().body(emp)));
	}
	
	@PutMapping("/update")
	public Mono<ResponseEntity<Employee>> update(@RequestBody Employee emp) {
		return restClient.updateEmp(Mono.just(emp))
				.flatMap(employee-> 
						Mono.just(ResponseEntity.ok().body(employee)));
	}
	
	@DeleteMapping("/delete/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
		return restClient.deleteEmpById(id)
				.then(Mono.just(ResponseEntity.accepted().build()));
	}

}
