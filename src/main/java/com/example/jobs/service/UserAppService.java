package com.example.jobs.service;

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

    public void saveAdmin(UserApp userApp) {
        userAppRepository.saveAndFlush(userApp);
    }

    public Optional<UserApp> getUserAppByCredentials(String email, String password) {
        return userAppRepository.findByEmailAndPassword(email, password);
    }

    public List<UserApp> getUsersByEmail(String email) {
        return userAppRepository.getByEmail(email);
    }

}
