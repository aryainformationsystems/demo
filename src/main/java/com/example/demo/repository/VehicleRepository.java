package com.example.demo.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Vehicle;

@Repository
public interface VehicleRepository extends PagingAndSortingRepository<Vehicle, String>{
	public Vehicle findByRegistration(String registration);
}
