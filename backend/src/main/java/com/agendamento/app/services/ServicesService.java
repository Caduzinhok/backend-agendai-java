package com.agendamento.app.services;

import com.agendamento.app.model.DTOs.DeleteServiceDTO;
import com.agendamento.app.model.DTOs.ResponseServiceDTO;
import com.agendamento.app.model.DTOs.ServiceDTO;
import com.agendamento.app.model.entity.Company;
import com.agendamento.app.model.entity.Employee;
import com.agendamento.app.model.entity.Service;
import com.agendamento.app.repositories.CompanyRepository;
import com.agendamento.app.repositories.EmployeeRepository;
import com.agendamento.app.repositories.ServiceRepository;
import com.agendamento.app.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@org.springframework.stereotype.Service
public class ServicesService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    ModelMapper modelMapper;

    public ResponseServiceDTO createService(ServiceDTO serviceDTO){
        List<Employee> employees = new ArrayList<>();
        if(!serviceDTO.employeesID().isEmpty()){
             employees = serviceDTO.employeesID().stream()
                    .map(currentID -> employeeRepository.findById(currentID)
                            .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + currentID)))
                     .collect(Collectors.toCollection(ArrayList::new));
            }

        Company company = this.companyRepository.findById   (serviceDTO.companyID()).orElseThrow(() -> new RuntimeException("Company Not Found"));
        Service service = new Service(employees, company, serviceDTO.name(), serviceDTO.description(), serviceDTO.duration(), serviceDTO.price());

        this.serviceRepository.save(service);
        return new ResponseServiceDTO(
                service.getId(),
                service.getEmployees().stream()
                        .map(Employee::getId)
                        .toList(),
                service.getName(),
                service.getDescription(),
                service.getDuration(),
                service.getPrice()
        );
    }

    public ResponseServiceDTO updateService(String id, ServiceDTO serviceDTO){
        List<Employee> employees = new ArrayList<>();
        if(!serviceDTO.employeesID().isEmpty()){
            employees = serviceDTO.employeesID().stream()
                    .map(currentID -> employeeRepository.findById(currentID)
                            .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + currentID)))
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        Service findedService = this.serviceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Service not found"));

        findedService.setName(serviceDTO.name());
        findedService.setDescription(serviceDTO.description());
        findedService.setPrice(serviceDTO.price());
        findedService.setDuration(serviceDTO.duration());
        findedService.setEmployees(employees);
        this.serviceRepository.save(findedService);

        return new ResponseServiceDTO(
                findedService.getId(),
                findedService.getEmployees().stream()
                        .map(Employee::getId)
                        .toList(),
                findedService.getName(),
                findedService.getDescription(),
                findedService.getDuration(),
                findedService.getPrice()
                );
    }

    public void deleteService(String id, DeleteServiceDTO deleteServiceDTO){
        Service findedService = this.serviceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Service not found"));

        this.serviceRepository.delete(findedService);
    }
}
