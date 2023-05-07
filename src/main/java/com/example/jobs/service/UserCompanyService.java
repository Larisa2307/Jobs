package com.example.jobs.service;

import com.example.jobs.entity.Company;
import com.example.jobs.entity.UserCompany;
import com.example.jobs.repository.UserCompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserCompanyService {

    final UserCompanyRepository userCompanyRepository;

    public void saveUserCompany(UserCompany userCompany) {
        userCompanyRepository.saveAndFlush(userCompany);
    }

    public void deleteUserCompany(UserCompany userCompany) {
        userCompanyRepository.delete(userCompany);
    }

    public UserCompany getById(String id) {
        return userCompanyRepository.findById(id).get();
    }

    public Optional<UserCompany> getUserCompanyByCredentials(String email, String password) {
        return userCompanyRepository.findByEmailAndPassword(email, password);
    }

    public List<UserCompany> getUsersByEmail(String email) {
        return userCompanyRepository.getByEmail(email);
    }

    public boolean existsByEmailAndDifferentId(String email, String id) {
        return userCompanyRepository.getByEmailAndDifferentId(email, id).isPresent();
    }

    public List<UserCompany> getUserByCompany(Company company) {
        return userCompanyRepository.findByCompany(company);
    }

}
