package com.agendamento.app.controller;

import com.agendamento.app.services.AvailabilityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees/{employeeId}/availability")
public class AvailabilityController {

    @Autowired
    AvailabilityService availabilityService;

    @GetMapping
    public ResponseEntity<?> getAvailability(@PathVariable @Valid String employeeId){
        try{
            return ResponseEntity.ok(availabilityService.getAvailabilityByEmployee(employeeId));
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Error on find employee");
        }
    }
}
