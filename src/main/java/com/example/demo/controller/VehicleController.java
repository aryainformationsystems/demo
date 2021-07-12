package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CreateVehicleRequest;
import com.example.demo.dto.GenericResponse;
import com.example.demo.dto.VehicleResponse;
import com.example.demo.service.VehicleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class VehicleController {
	@Autowired
	private VehicleService vehicleService;

	@GetMapping("/api/v1/vehicle")
	@PreAuthorize("hasAuthority('Administrator')")
	public GenericResponse getAllVehicles(@RequestParam("page") int page, @RequestParam("size") int size,
			@RequestParam("sort") String sort) throws JsonMappingException, JsonProcessingException {
		Sort sortOptions = Sort.by(sort);
		Page<VehicleResponse> vehicles = vehicleService.getPage(page, size, sortOptions);
		long count = vehicles.getTotalElements();
		return new GenericResponse(vehicles.toList(), "SUCCESS", null, count);
	}

	@GetMapping("/api/v1/vehicle/{reg}")
	@PreAuthorize("hasAnyAuthority('Administrator', 'User')")
	public GenericResponse getVehiclesByegistration(@PathVariable("reg") String registration)
			throws JsonMappingException, JsonProcessingException {
		VehicleResponse result = vehicleService.getByRegistrationNumber(registration);
		return new GenericResponse(result, "SUCCESS", null, result != null ? 1 : 0);
	}

	@PostMapping("/api/v1/vehicle")
	@PreAuthorize("hasAuthority('Administrator')")
	public GenericResponse createVehicle(@RequestBody CreateVehicleRequest vehicleRequest)
			throws JsonMappingException, JsonProcessingException {
		VehicleResponse result = vehicleService.createVehicle(vehicleRequest);
		return new GenericResponse(result, "SUCCESS", null, 1);
	}

	@PutMapping("/api/v1/vehicle/{registration}")
	@PreAuthorize("hasAuthority('Administrator')")
	public GenericResponse updateVehicle(@PathVariable("registration") String registration,
			@RequestBody CreateVehicleRequest vehicleRequest) throws JsonMappingException, JsonProcessingException {
		VehicleResponse result = vehicleService.updateVehicle(registration, vehicleRequest);
		return new GenericResponse(result, "SUCCESS", null, 1);
	}

	@DeleteMapping("/api/v1/vehicle/{registration}")
	@PreAuthorize("hasAuthority('Administrator')")
	public GenericResponse deleteVehicle(@PathVariable("registration") String registration)
			throws JsonMappingException, JsonProcessingException {
		vehicleService.deleteVehicle(registration);
		return new GenericResponse(null, "SUCCESS", null, 1);
	}
}
