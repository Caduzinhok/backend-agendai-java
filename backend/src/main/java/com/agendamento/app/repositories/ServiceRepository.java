package com.agendamento.app.repositories;

import com.agendamento.app.model.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, String> {

    Page<Service> findAll(Pageable pageable);
}
