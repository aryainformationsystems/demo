package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.dto.GenericResponse;
import com.example.demo.exception.VehicleNotFoundException;

@RestController
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
	@ExceptionHandler(VehicleNotFoundException.class)
	public ResponseEntity<GenericResponse> handleVehicleNotFoundException(VehicleNotFoundException ex,
			WebRequest request) {
		GenericResponse response = new GenericResponse(null, ex.getMessage(), ex, 0);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
}
