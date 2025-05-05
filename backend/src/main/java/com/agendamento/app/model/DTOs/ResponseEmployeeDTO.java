package com.agendamento.app.model.DTOs;

import com.agendamento.app.model.EmployeeRole;

public record ResponseEmployeeDTO(String id, String companyId, String name, String cpf, String phone, String email, EmployeeRole role) {
}
