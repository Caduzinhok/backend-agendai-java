package com.agendamento.app.controller;

import com.agendamento.app.model.DTOs.AppointmentDTO;
import com.agendamento.app.model.DTOs.EmployeeDTO;
import com.agendamento.app.model.DTOs.ResponseAppointmentDTO;
import com.agendamento.app.model.DTOs.ResponseEmployeeDTO;
import com.agendamento.app.model.entity.Appointment;
import com.agendamento.app.model.entity.Employee;
import com.agendamento.app.repositories.EmployeeRepository;
import com.agendamento.app.services.EmployeeService;
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
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;

    @PostMapping
    public ResponseEntity createEmployee(@RequestBody @Valid EmployeeDTO employeeDTO){
        ResponseEmployeeDTO newEmployee = this.employeeService.createEmployee(employeeDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteEmployee(@PathVariable String id){
        try{
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();

        }catch (RuntimeException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateEmployee(@PathVariable String id, @RequestBody @Valid EmployeeDTO dto){
        try{
            ResponseEmployeeDTO updatedEmployee = this.employeeService.updateEmployee(id, dto);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedEmployee);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployees(
            @PathVariable String id,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sortBy);

        try {
            Page<Employee> employees = employeeRepository.findByCompanyId(id, pageable);

            if (employees.isEmpty()) {
                throw new RuntimeException("Nenhum funcionário encontrado para a empresa informada.");
            }

            Page<ResponseEmployeeDTO> employeeDTOPage = employees.map(employee ->
                    new ResponseEmployeeDTO(
                            employee.getId(),
                            employee.getCompany().getId(),
                            employee.getName(),
                            employee.getCpf(),
                            employee.getPhone(),
                            employee.getEmail(),
                            employee.getRole()
                    )
            );

            return new ResponseEntity<>(employeeDTOPage, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
