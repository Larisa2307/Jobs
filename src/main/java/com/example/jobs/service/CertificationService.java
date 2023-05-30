package com.example.jobs.service;

import com.example.jobs.entity.Certification;
import com.example.jobs.entity.UserApp;
import com.example.jobs.repository.CertificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CertificationService {
    final CertificationRepository certificationRepository;

    public void save(Certification certification) {
        certificationRepository.save(certification);
    }

    public void delete(Certification certification) {
        certificationRepository.delete(certification);
    }

    public Certification getById(String id) {
        return certificationRepository.findById(id).get();
    }

    public List<Certification> getAllByUserApp(UserApp userApp) {
        return certificationRepository.findAllByUserApp(userApp);
    }
}
