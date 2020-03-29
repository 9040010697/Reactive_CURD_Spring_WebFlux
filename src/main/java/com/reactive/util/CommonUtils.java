package com.reactive.util;

import java.util.UUID;

import com.reactive.model.Employee;

public class CommonUtils {

	public static Employee generateEmpId(Employee e) {
		e.setId(UUID.randomUUID().toString());
		return e;
	}
}
