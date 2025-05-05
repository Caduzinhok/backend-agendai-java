package com.agendamento.app.services;

import com.agendamento.app.model.DTOs.CompanyDTO;
import com.agendamento.app.model.DTOs.DeleteCompanyDTO;
import com.agendamento.app.model.DTOs.ResponseCompanyDTO;
import com.agendamento.app.model.entity.Company;
import com.agendamento.app.repositories.CompanyRepository;
import com.agendamento.app.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    public ResponseCompanyDTO createCompany(CompanyDTO companyDTO){
        /// Vou pensar no futuro se quero fazer alguma regra de negócio.


        ///  Fim da Regra de Negócio
        Company company = new Company(companyDTO.cnpj(), companyDTO.name(), companyDTO.address(), companyDTO.phone(), companyDTO.email());

        this.companyRepository.save(company);

        return new ResponseCompanyDTO(
                company.getId(),
                company.getCnpj(),
                company.getName(),
                company.getAddress(),
                company.getPhone(),
                company.getEmail()
        );
    }

    public void deleteCompany(String id){


        Company company = this.companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Failed on find the company"));


        this.companyRepository.delete(company);
    }

    public ResponseCompanyDTO updateCompany(CompanyDTO companyDTO){
        String companyId = companyDTO.id()
                .orElseThrow(() -> new RuntimeException("Company ID is missing"));
        Company company = this.companyRepository.findById(companyId).orElseThrow(() -> new RuntimeException("Failed on find the the company"));

        // Atualiza apenas os campos que não são nulos
        if (companyDTO.cnpj() != null) company.setCnpj(companyDTO.cnpj());
        if (companyDTO.name() != null) company.setName(companyDTO.name());
        if (companyDTO.address() != null) company.setAddress(companyDTO.address());
        if (companyDTO.phone() != null) company.setPhone(companyDTO.phone());
        if (companyDTO.email() != null) company.setEmail(companyDTO.email());

        return new ResponseCompanyDTO(
                company.getId(),
                company.getCnpj(),
                company.getName(),
                company.getAddress(),
                company.getPhone(),
                company.getEmail()
        );
    }

}
