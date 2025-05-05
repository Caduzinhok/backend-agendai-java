package com.agendamento.app.model.DTOs;

import java.time.LocalDateTime;
import java.util.List;

public record ResponseAppointmentDTO(String id, LocalDateTime startTime, LocalDateTime endTime, String status, String notes){
}
