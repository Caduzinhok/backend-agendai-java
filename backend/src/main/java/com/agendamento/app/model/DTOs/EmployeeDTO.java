package com.agendamento.app.model.DTOs;

import com.agendamento.app.model.EmployeeRole;

import java.util.List;

public record EmployeeDTO(String companyId, List<String> servicesId, String name, String cpf, String phone, String email, EmployeeRole role) {
}
