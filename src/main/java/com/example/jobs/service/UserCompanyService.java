package com.example.jobs.service;

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

    public void saveUserCompany(UserCompany userApp) {
        userCompanyRepository.saveAndFlush(userApp);
    }

    public Optional<UserCompany> getUserCompanyByCredentials(String email, String password) {
        return userCompanyRepository.findByEmailAndPassword(email, password);
    }

    public List<UserCompany> getUsersByEmail(String email) {
        return userCompanyRepository.getByEmail(email);
    }

}