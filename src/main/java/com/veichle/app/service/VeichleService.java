package com.veichle.app.service;

import com.veichle.app.entity.Vehicle;
import com.veichle.app.repository.VeichleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VeichleService {
    @Autowired
    private VeichleRepository veichleRepository;

    public Vehicle save(Vehicle vehicle) {
        return veichleRepository.save(vehicle);
    }

    public List<Vehicle> getVeichles() {
        return veichleRepository.findAll();
    }

    public Optional<Vehicle> getVeichleById(long id) {
         return veichleRepository.findById(id);
    }

    public Vehicle update(Vehicle vehicle) {
        Long id= vehicle.getId();
        Optional<Vehicle> optionalVeichle=veichleRepository.findById(id);
        if(optionalVeichle.isPresent()) {
            return veichleRepository.save(vehicle);
        }
        return null;
    }

    public void delete(long id) {
        veichleRepository.deleteById(id);
    }
}
