package com.agendamento.app.services;

import com.agendamento.app.model.DTOs.*;
import com.agendamento.app.model.entity.*;
import com.agendamento.app.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ServiceRepository serviceRepository;

    public ResponseEmployeeDTO createEmployee(EmployeeDTO employeeDTO){
        Company currentCompany = this.companyRepository.findById(employeeDTO.companyId()).orElseThrow(() -> new RuntimeException("Company not found"));

        Employee newEmployee = new Employee(currentCompany, employeeDTO.cpf(), employeeDTO.name(), employeeDTO.phone(), employeeDTO.email(), employeeDTO.role());

        this.employeeRepository.save(newEmployee);

        return new ResponseEmployeeDTO(
                newEmployee.getId(),
                employeeDTO.companyId(),
                employeeDTO.name(),
                employeeDTO.cpf(),
                employeeDTO.phone(),
                employeeDTO.email(),
                employeeDTO.role()
        );
    }
    public ResponseEmployeeDTO updateEmployee(String id, EmployeeDTO dto){
        List<com.agendamento.app.model.entity.Service> services = new ArrayList<>();

        if(!dto.servicesId().isEmpty()){
            services = dto.servicesId().stream()
                    .map(currentID -> serviceRepository.findById(currentID)
                            .orElseThrow(() -> new RuntimeException("Service not found with ID: " + currentID)))
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        Company company = this.companyRepository.findById(dto.companyId()).orElseThrow(() -> new RuntimeException("Company not found"));
        Employee findedEmployee = this.employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));

        findedEmployee.setName(dto.name());
        findedEmployee.setServices(services);
        findedEmployee.setCpf(dto.cpf());
        findedEmployee.setEmail(dto.email());
        findedEmployee.setPhone(dto.phone());
        findedEmployee.setRole(dto.role());

        this.employeeRepository.save(findedEmployee);

        return new ResponseEmployeeDTO(
                findedEmployee.getId(),
                findedEmployee.getCompany().getId(),
                findedEmployee.getName(),
                findedEmployee.getCpf(),
                findedEmployee.getPhone(),
                findedEmployee.getEmail(),
                findedEmployee.getRole()
        );
    }

    public void deleteEmployee(String id) throws EntityNotFoundException {
        Employee findedEmployee = this.employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Service not found"));

        this.employeeRepository.delete(findedEmployee);
    }
}
