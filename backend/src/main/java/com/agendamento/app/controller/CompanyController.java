package com.agendamento.app.controller;

import com.agendamento.app.model.DTOs.CompanyDTO;
import com.agendamento.app.model.DTOs.DeleteCompanyDTO;
import com.agendamento.app.model.DTOs.ResponseCompanyDTO;
import com.agendamento.app.model.entity.Company;
import com.agendamento.app.repositories.CompanyRepository;
import com.agendamento.app.services.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyService company;

    @Autowired
    CompanyRepository companyRepository;

    @PostMapping
    public ResponseEntity createCompany(@RequestBody @Valid CompanyDTO companyDTO){
        ResponseCompanyDTO newCompany = company.createCompany(companyDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(newCompany);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCompany(@PathVariable String id){
        company.deleteCompany(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity updateCompany(@RequestBody @Valid CompanyDTO companyDTO){
        ResponseCompanyDTO updatedCompany = company.updateCompany(companyDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedCompany);
    }

    @GetMapping
    public ResponseEntity<Page<ResponseCompanyDTO>> getCompanies(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
        @RequestParam(value = "direction", defaultValue = "asc") String direction){

        // Configuração da paginação
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sortBy);

        Page<Company> companies = this.companyRepository.findAll(pageable);

        Page<ResponseCompanyDTO> companyDTOPage = companies.map(currentCompany ->
                new ResponseCompanyDTO(
                    currentCompany.getId(),
                    currentCompany.getCnpj(),
                    currentCompany.getName(),
                    currentCompany.getAddress(),
                    currentCompany.getPhone(),
                    currentCompany.getEmail()
            )
        );

        return new ResponseEntity<>(companyDTOPage, HttpStatus.OK);
    }

}
