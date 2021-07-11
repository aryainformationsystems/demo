package com.example.demo.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.demo.model.Vehicle;

public interface VehicleRepository extends PagingAndSortingRepository<Vehicle, String>{
	public Vehicle findByRegistration(String registration);
}
