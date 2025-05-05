package com.agendamento.app.model.DTOs;

import java.util.Optional;

public record ResponseCompanyDTO(String id, String cnpj, String name, String address, String phone, String email) {

}
