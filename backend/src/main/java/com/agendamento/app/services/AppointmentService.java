package com.agendamento.app.services;

import com.agendamento.app.model.DTOs.*;
import com.agendamento.app.model.entity.*;
import com.agendamento.app.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

@org.springframework.stereotype.Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ServiceRepository serviceRepository;

    public ResponseAppointmentDTO createAppointment(AppointmentDTO dto){
        User user = this.userRepository.findById(dto.userID()).orElseThrow(() -> new RuntimeException("User not found"));
        Service service = this.serviceRepository.findById(dto.serviceID()).orElseThrow(() -> new RuntimeException("Service not found"));
        Employee employee = this.employeeRepository.findById(dto.employeeID()).orElseThrow(() -> new RuntimeException("Employee not found"));

        Appointment appointment = new Appointment(user, employee, service, dto.startTime(), dto.endTime(), dto.status(), dto.notes());

        this.appointmentRepository.save(appointment);

        return new ResponseAppointmentDTO(
                appointment.getId(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                appointment.getStatus(),
                appointment.getNotes()
        );
    }


    public ResponseAppointmentDTO updateAppointment(String id, AppointmentDTO dto){

        User user = this.userRepository.findById(dto.userID()).orElseThrow(() -> new RuntimeException("User not found"));
        Service service = this.serviceRepository.findById(dto.serviceID()).orElseThrow(() -> new RuntimeException("Service not found"));
        Employee employee = this.employeeRepository.findById(dto.employeeID()).orElseThrow(() -> new RuntimeException("Employee not found"));
        Appointment findedAppointment = this.appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));

        findedAppointment.setService(service);
        findedAppointment.setEmployee(employee);
        findedAppointment.setUser(user);
        findedAppointment.setStatus(dto.status());
        findedAppointment.setNotes(dto.notes());
        findedAppointment.setStartTime(dto.startTime());
        findedAppointment.setEndTime(dto.endTime());

        this.appointmentRepository.save(findedAppointment);

        return new ResponseAppointmentDTO(
                    findedAppointment.getId(),
                    findedAppointment.getStartTime(),
                    findedAppointment.getEndTime(),
                    findedAppointment.getStatus(),
                    findedAppointment.getNotes()
                );
    }

    public void deleteAppointment(String id) throws EntityNotFoundException{
            Appointment findedAppointment = this.appointmentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Service not found"));

            this.appointmentRepository.delete(findedAppointment);
    }
}
