package com.agendamento.app.services;

import com.agendamento.app.model.entity.Availability;
import com.agendamento.app.repositories.AvailabilityRepository;
import com.agendamento.app.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailabilityService {
    @Autowired
    AvailabilityRepository availabilityRepository;
    EmployeeRepository employeeRepository;

    public List<Availability> getAvailabilityByEmployee(String employeeId){
        return availabilityRepository.findByEmployeeId(employeeId);
    }
}
