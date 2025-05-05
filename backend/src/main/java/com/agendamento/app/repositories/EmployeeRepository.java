package com.agendamento.app.repositories;

import com.agendamento.app.model.entity.Appointment;
import com.agendamento.app.model.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Page<Employee> findAll(Pageable pageable);

    Page<Employee> findByCompanyId(String id, Pageable pageable);
}
