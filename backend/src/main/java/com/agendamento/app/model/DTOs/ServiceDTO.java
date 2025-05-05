package com.agendamento.app.model.DTOs;

import com.agendamento.app.model.entity.Employee;

import java.math.BigDecimal;
import java.util.List;

public record ServiceDTO(List<String> employeesID, String companyID, String name, String description, Integer duration, BigDecimal price) {

}
