package com.veichle.app.service;

import com.veichle.app.entity.Vehicle;
import com.veichle.app.model.VehicleDTO;
import com.veichle.app.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    public VehicleDTO save(VehicleDTO vehicleDTO) {
        Vehicle vehicle=vehicleRepository.save(VehicleDTO.convertToVehicle(vehicleDTO));
        System.out.println(vehicle);
        return VehicleDTO.convertToVehicleDTO(vehicle);
    }

    public List<Vehicle> getVeichles() {
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> getVeichleById(long id) {
         return vehicleRepository.findById(id);
    }

    public VehicleDTO update(VehicleDTO vehicleDTO) {
        Vehicle vehicle=vehicleDTO.convertToVehicle(vehicleDTO);
        Long id= vehicle.getId();
        if(id==null||id<0)
            throw new RuntimeException("Please Enter ID");
        Optional<Vehicle> optionalVeichle= vehicleRepository.findById(id);
        if(optionalVeichle.isPresent()) {
            Vehicle exisitingVehicle=optionalVeichle.get();
            VehicleDTO.convertToExistingVehicle(exisitingVehicle,vehicleDTO);
            exisitingVehicle=vehicleRepository.save(exisitingVehicle);
            return VehicleDTO.convertToVehicleDTO(exisitingVehicle);
        }
        throw new RuntimeException("Vehicle not found");
    }

    public void delete(long id) {
        vehicleRepository.deleteById(id);
    }
}
