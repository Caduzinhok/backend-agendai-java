package com.agendamento.app.model.DTOs;


import com.agendamento.app.model.UserRole;

public record RegisterDTO(String name, String email, String password, UserRole role) {
}
