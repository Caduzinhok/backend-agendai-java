package com.agendamento.app.model.DTOs;

import java.time.LocalDateTime;

public record AppointmentDTO(String userID, String employeeID, String serviceID, LocalDateTime startTime, LocalDateTime endTime, String status, String notes) {
}
