package com.reactive.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reactive.exception.EmployeeIdNotFoundException;
import com.reactive.model.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository repo;

	public Mono<Employee> save(Employee employee) {
		return repo.save(employee);
	}

	public Flux<Employee> findAll() {
		return repo.findAll();
	}

	public Mono<Employee> findById(String id) {
		return Mono.just(id)	
			    .flatMap(repo::findById)
			    .switchIfEmpty(Mono.error(new EmployeeIdNotFoundException()));
	}

	public Mono<Employee> update(Employee emp) {
		Mono<Employee> existingEmp = findById(emp.getId());
		return existingEmp.flatMap(e-> repo.save(emp));
	}

	public Mono<Void> delete(String id) {
		return findById(id)
			.switchIfEmpty(Mono.error(new EmployeeIdNotFoundException()))
			.flatMap(repo::delete);
	}

}
