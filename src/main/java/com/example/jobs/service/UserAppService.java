package com.example.jobs.service;

import com.example.jobs.entity.Company;
import com.example.jobs.entity.UserApp;
import com.example.jobs.repository.UserAppRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserAppService {

    final UserAppRepository userAppRepository;

    public void saveUser(UserApp userApp) {
        userAppRepository.saveAndFlush(userApp);
    }

    public Optional<UserApp> getUserAppByEmail(String email) {
        return userAppRepository.getUserAppByEmail(email);
    }

    public List<UserApp> getUsersByEmail(String email) {
        return userAppRepository.getByEmail(email);
    }

    public void deleteUser(UserApp userApp) {
        userAppRepository.delete(userApp);
    }

    public UserApp getById(String id) {
        return userAppRepository.findById(id).get();
    }

    public boolean existsByEmailAndDifferentId(String email, String id) {
        return userAppRepository.getByEmailAndDifferentId(email, id).isPresent();
    }

    public List<UserApp> getUserByCompany(Company company) {
        return userAppRepository.findByCompany(company);
    }
}
