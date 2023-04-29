package com.example.jobs.service;

import com.example.jobs.entity.Company;
import com.example.jobs.entity.UserCompany;
import com.example.jobs.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CompanyService {

    final CompanyRepository companyRepository;

    public void saveAdmin(Company company) {
        companyRepository.save(company);
    }

    public List<Company> getEmployerByUniqueCode(String uniqueCode) {
        return companyRepository.getByUniqueCode(uniqueCode);
    }

    public Company getById(String id) {
        return companyRepository.findById(id).get();
    }


}
