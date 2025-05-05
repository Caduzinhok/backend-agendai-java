package com.agendamento.app.model.DTOs;


import java.util.Optional;

public record CompanyDTO(Optional<String> id, String cnpj, String name, String address, String phone, String email) {

}

