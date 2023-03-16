package com.example.jobs.service;

import com.example.jobs.entity.Employer;
import com.example.jobs.entity.UserApp;
import com.example.jobs.repository.EmployerRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class EmployerService implements UserDetailsService {

    final EmployerRepository employerRepository;

    public void saveAdmin(Employer employer) {
        employerRepository.saveAndFlush(employer);
    }

    public Optional<Employer> getEmployerByCredentials(String email, String password) {
        return employerRepository.findByEmailAndPassword(email, password);
    }

    public List<Employer> getEmployerByUniqueCode(String uniqueCode) {
        return employerRepository.getByUniqueCode(uniqueCode);
    }

    public Employer getEmployerByUsername(String username) {
        return employerRepository.getByUsername(username);
    }

    public Employer getbyId(String id) {
        return employerRepository.getById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) employerRepository.getByUsername(username);
    }
}
