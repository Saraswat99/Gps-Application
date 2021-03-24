package com.veichle.app.controller;

import com.veichle.app.entity.Vehicle;
import com.veichle.app.service.VeichleService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Data
@Slf4j
@RestController
@RequestMapping("/veichles")
public class VeichleController {
    @Autowired
    private VeichleService veichleService;

    @RequestMapping(method= RequestMethod.POST,value="/save")
    public Vehicle save(@RequestBody Vehicle vehicle)
    {
        log.info(vehicle.toString());
        return veichleService.save(vehicle);
    }

    @GetMapping(value="/getveichles")
    public List<Vehicle> getVeichles()
    {
        return veichleService.getVeichles();
    }

    @GetMapping(value="/getveichle/{id}")
    public Vehicle getVeichleById(@PathVariable long id) {
        Optional<Vehicle> veichleOptional=veichleService.getVeichleById(id);
        if(veichleOptional.isPresent())
            return veichleOptional.get();
        return null;
    }

    @PutMapping(value="/update")
    public Vehicle update(@RequestBody Vehicle vehicle) {
        log.info(vehicle.toString());
        return veichleService.update(vehicle);
    }

    @DeleteMapping(value="/delete/{id}")
    public void delete(@PathVariable long id)
    {
        veichleService.delete(id);
    }

}
