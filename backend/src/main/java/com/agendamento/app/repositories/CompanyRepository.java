package com.agendamento.app.repositories;

import com.agendamento.app.model.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String> {
    Page<Company> findAll(Pageable pageable);
}
