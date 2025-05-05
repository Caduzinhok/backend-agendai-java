package com.agendamento.app.model.entity;

import jakarta.persistence.*;
import jdk.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToMany(mappedBy = "services")
    private List<Employee> employees;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private BigDecimal price;

    public Service(List<Employee> employees, Company company, String name, String description, Integer duration, BigDecimal price) {
        this.employees = employees;
        this.company = company;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.price = price;
    }
}
