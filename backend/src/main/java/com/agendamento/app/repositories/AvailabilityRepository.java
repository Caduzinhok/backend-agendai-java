package com.agendamento.app.repositories;

import com.agendamento.app.model.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, String> {
    List<Availability> findByEmployeeId(String employeeId);

}
