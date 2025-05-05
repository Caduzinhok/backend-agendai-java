package com.agendamento.app.controller;

import com.agendamento.app.model.DTOs.*;
import com.agendamento.app.model.entity.Appointment;
import com.agendamento.app.model.entity.Employee;
import com.agendamento.app.model.entity.Service;
import com.agendamento.app.repositories.AppointmentRepository;
import com.agendamento.app.services.AppointmentService;
import jakarta.persistence.EntityNotFoundException;
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
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    AppointmentRepository appointmentRepository;

    @PostMapping
    public ResponseEntity createAppointment(@RequestBody @Valid AppointmentDTO appointmentDTO){
        try {
            ResponseAppointmentDTO newAppointment = appointmentService.createAppointment(appointmentDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(newAppointment);

        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAppointment(@PathVariable String id){
        try{
            appointmentService.deleteAppointment(id);
            return ResponseEntity.noContent().build();

        }catch (RuntimeException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAppointment(@PathVariable String id, AppointmentDTO dto){
        try{
            ResponseAppointmentDTO updatedAppointment = this.appointmentService.updateAppointment(id, dto);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedAppointment);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data on update appointment, verify data or id");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAppointments(
            @PathVariable String id,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "startTime") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sortBy);

        try {
            Page<Appointment> appointments = appointmentRepository.findByUserId(id, pageable);

            if (appointments.isEmpty()) {
                throw new RuntimeException("Nenhum agendamento encontrado para o usuário informado.");
            }

            Page<ResponseAppointmentDTO> appointmentDTOPage = appointments.map(appointment ->
                    new ResponseAppointmentDTO(
                            appointment.getId(),
                            appointment.getStartTime(),
                            appointment.getEndTime(),
                            appointment.getStatus(),
                            appointment.getNotes()
                    )
            );

            return new ResponseEntity<>(appointmentDTOPage, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}


