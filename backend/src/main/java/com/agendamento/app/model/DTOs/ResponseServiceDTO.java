package com.agendamento.app.model.DTOs;


import java.math.BigDecimal;
import java.util.List;

public record ResponseServiceDTO (String id, List<String> employeesID, String name, String description, Integer duration, BigDecimal price){
}
