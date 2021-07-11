package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.example.demo.dto.CreateVehicleRequest;
import com.example.demo.dto.VehicleResponse;
import com.example.demo.exception.VehicleNotFoundException;
import com.example.demo.model.Vehicle;
import com.example.demo.repository.VehicleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class VehicleService {
	@Autowired
	private VehicleRepository vehicleRepository;

	public VehicleResponse createVehicle(CreateVehicleRequest vehicleRequest)
			throws JsonMappingException, JsonProcessingException {
		Vehicle vehicle = new Vehicle();
		vehicle.setAddress(vehicleRequest.getAddress());
		vehicle.setChassisNumber(vehicleRequest.getChassisNumber());
		vehicle.setDateOfExpiry(vehicleRequest.getDateOfExpiry());
		vehicle.setDateOfRegistration(vehicleRequest.getDateOfRegistration());
		vehicle.setEngineNumber(vehicleRequest.getEngineNumber());
		vehicle.setOwner(vehicleRequest.getOwner());
		vehicle.setRegistration(vehicleRequest.getRegistration());
		Vehicle result = vehicleRepository.save(vehicle);
		return (new ObjectMapper().readValue(new ObjectMapper().writeValueAsString(result), VehicleResponse.class));
	}

	public VehicleResponse getByRegistrationNumber(String registrationNumber)
			throws JsonMappingException, JsonProcessingException {
		Vehicle result = vehicleRepository.findByRegistration(registrationNumber);
		if (null != result) {
			return (new ObjectMapper().readValue(new ObjectMapper().writeValueAsString(result), VehicleResponse.class));
		}
		else {
			throw new VehicleNotFoundException(registrationNumber);
		}
	}

	@SuppressWarnings("unchecked")
	public Page<VehicleResponse> getPage(int pageNo, int size, Sort sort)
			throws JsonMappingException, JsonProcessingException {
		Pageable page = PageRequest.of(pageNo, size, sort);
		Page<Vehicle> vehicles = vehicleRepository.findAll(page);
		List<Vehicle> listVehicle = vehicles.toList();
		List<VehicleResponse> result = new ObjectMapper().readValue(new ObjectMapper().writeValueAsString(listVehicle),
				List.class);
		PageImpl<VehicleResponse> resultPage = new PageImpl<VehicleResponse>(result, page, vehicles.getTotalElements());
		return resultPage;
	}
}
