package com.reactive.util;

import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.reactive.exception.ClientErrorException;
import com.reactive.exception.ServerErrorException;
import com.reactive.model.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class RestClient {
	
	private static final String baseUrl= "http://localhost:8085/rest/employee";
	private WebClient webClient;

	public RestClient() {
		this.webClient = WebClient.create(baseUrl);
	}
	
	public Mono<Employee> saveEmp(Mono<Employee> bodyToMono) {
		return webClient.post()
		.uri("/save")
		.body(bodyToMono, Employee.class)
		.retrieve()
		.onStatus(HttpStatus::is4xxClientError, handleClientError())
		.onStatus(HttpStatus::is5xxServerError, handleServerError())
		.bodyToMono(Employee.class);
	}

	public Flux<Employee> getAllEmp() {
		return webClient
			.get()
			.uri("/all")
			.retrieve()
			.onStatus(HttpStatus::is4xxClientError, handleClientError())
			.onStatus(HttpStatus::is5xxServerError, handleServerError())
			.bodyToFlux(Employee.class);
	}

	public Mono<Employee> getEmpById(String empId) {
		return webClient.get()
		.uri("/get/{id}", empId)
		.retrieve()
		.onStatus(HttpStatus::is4xxClientError, handleClientError())
		.onStatus(HttpStatus::is5xxServerError, handleServerError())
		.bodyToMono(Employee.class);
	}
	
	public Mono<Employee> updateEmp(Mono<Employee> bodyToMono) {
		return webClient.put()
		.uri("/update")
		.body(bodyToMono, Employee.class)
		.retrieve()
		.onStatus(HttpStatus::is4xxClientError, handleClientError())
		.onStatus(HttpStatus::is5xxServerError, handleServerError())
		.bodyToMono(Employee.class);
	}
	
	public Mono<Void> deleteEmpById(String empId) {
		return webClient.delete()
		.uri("/delete/{id}", empId)
		.retrieve()
		.onStatus(HttpStatus::is4xxClientError, handleClientError())
		.onStatus(HttpStatus::is5xxServerError, handleServerError())
		.bodyToMono(Void.class);
	}

	private Function<ClientResponse, Mono<? extends Throwable>> handleClientError() {
		return response -> response.bodyToMono(String.class).map(ClientErrorException:: new);
	}
	
	private Function<ClientResponse, Mono<? extends Throwable>> handleServerError() {
		return response -> response.bodyToMono(String.class).map(ServerErrorException:: new);
	}

	
}
