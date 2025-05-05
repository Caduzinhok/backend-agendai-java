package com.agendamento.app.controller;

import com.agendamento.app.model.DTOs.DeleteServiceDTO;
import com.agendamento.app.model.DTOs.ResponseServiceDTO;
import com.agendamento.app.model.entity.Employee;
import com.agendamento.app.model.entity.Service;
import com.agendamento.app.model.DTOs.ServiceDTO;
import com.agendamento.app.repositories.ServiceRepository;
import com.agendamento.app.services.ServicesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    ServicesService service;

    @Autowired
    private ServiceRepository serviceRepository;

    @PostMapping
    public ResponseEntity createService(@RequestBody @Valid ServiceDTO serviceDTO){
        try {
            ResponseServiceDTO createdService = service.createService(serviceDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdService);
        } catch (RuntimeException exception){
            return ResponseEntity.status(500).body("Invalid data on create service: " + exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateService(@PathVariable String id, @RequestBody @Valid ServiceDTO serviceDTO){
        try{
            ResponseServiceDTO updatedService = service.updateService(id, serviceDTO);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedService);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data on update service, verify data or id");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteService(@PathVariable String id, @RequestBody @Valid DeleteServiceDTO deleteServiceDTO){
        try{
            service.deleteService(id, deleteServiceDTO);
            return ResponseEntity.noContent().build();

        }catch (RuntimeException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data on delete service");
        }
    }

    @GetMapping
    public ResponseEntity<Page<ResponseServiceDTO>> getServices(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction){

        // Configuração da paginação
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sortBy);

        // Buscando paginas de serviços
        Page<Service> services = serviceRepository.findAll(pageable);

        // Converter para DTO
        Page<ResponseServiceDTO> serviceDTOPage = services.map(service ->
                new ResponseServiceDTO(
                        service.getId(),
                        service.getEmployees().stream().map(Employee::getId).toList(),
                        service.getName(),
                        service.getDescription(),
                        service.getDuration(),
                        service.getPrice()
                )
                );

        return new ResponseEntity<>(serviceDTOPage, HttpStatus.OK);
    }
}
