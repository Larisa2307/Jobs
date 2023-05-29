package com.example.jobs.controller;

import com.example.jobs.entity.Document;
import com.example.jobs.entity.Page;
import com.example.jobs.service.*;
import com.example.jobs.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ResumeController {

    final DocumentService documentService;
    final NavbarService navbarService;
    final CompanyService companyService;
    final UserAppService userAppService;
    final AnnouncementService announcementService;
    final UserAppAnnouncementService employerUserAppService;
    final UserAppAnnouncementService userAppAnnouncementService;

    @GetMapping("/resume/{id}")
    String getDashboardPage(Model model, @PathVariable String id) {
        navbarService.activateNavbarTab(Page.RESUME, model);
        var userApp = userAppService.getById(id);
        Util.extractRole(model, userApp);


        String docName;
        if (documentService.getDocumentByUserApp(userApp).isEmpty()) {
            docName = "";
        } else {
            docName = documentService.getDocumentByUserApp(userApp).get().getName();
        }

        model.addAttribute("userApp", userApp);
        model.addAttribute("documentName", docName);
        model.addAttribute("isDocument", documentService.getDocumentByUserApp(userApp).isPresent());

        return "resume";
    }

    @PostMapping("/upload/{userId}")
    public String handleFileUpload(@PathVariable String userId, @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            long fileSize = file.getSize();
            if (fileSize < 10485760 && "".equals(file.getOriginalFilename())) {
                return "redirect:/resume/" + userId + "?error=true";
            }
            try {
                var userApp = userAppService.getById(userId);

                documentService.getDocumentByUserApp(userApp).ifPresent(
                        document -> documentService.deleteDocumentById(document.getId()));

                documentService.save(file, userApp);

                return "redirect:/resume/" + userId;
            } catch (Exception e) {
                e.printStackTrace();
                return "redirect:/resume/" + userId + "?error=true";
            }
        }
        return "redirect:/resume/" + userId + "?error=true";
    }
    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            Document document = documentService.getDocumentByName(fileName);

            if (document != null) {
                ByteArrayResource resource = new ByteArrayResource(document.getContent());

                String contentType = "application/octet-stream";

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getName() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/resumes/{fileName}")
    public ResponseEntity<String> deleteDocument(@PathVariable String fileName) {
        try {
            documentService.deleteDocumentById(documentService.getDocumentByName(fileName).getId());

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error deleting the resume document with name: " + fileName, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
