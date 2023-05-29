package com.example.jobs.service;

import com.example.jobs.entity.Document;
import com.example.jobs.entity.UserApp;
import com.example.jobs.mapper.DocumentMapper;
import com.example.jobs.repository.DocumentRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@Getter
public class DocumentService {

    final DocumentRepository documentRepository;

    public Document getDocumentById(String id) {
        return documentRepository.findById(id).orElse(null);
    }

    public Document getDocumentByName(String name) {
        return documentRepository.findByName(name);
    }

    public byte[] getDocumentBytes(MultipartFile multipartFile) {
        try {
            return multipartFile.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(MultipartFile document, UserApp userApp) {
        Document f = toFile(userApp, document);
        documentRepository.save(f);

    }

    public Optional<Document> getDocumentByUserApp(UserApp userApp) {
        return documentRepository.findByUserApp(userApp);
    }

    private Document toFile(UserApp userApp, MultipartFile multipartFile) {

        return DocumentMapper.toFile(userApp, multipartFile.getOriginalFilename(), multipartFile.getContentType(),
                this.getDocumentBytes(multipartFile));

    }

    public void deleteDocumentById(String id) {
        documentRepository.deleteById(id);
    }

}
