package com.example.jobs.controller;

import com.example.jobs.entity.*;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ResumeController {

    final NavbarService navbarService;
    final CompanyService companyService;
    final UserAppService userAppService;
    final DocumentService documentService;
    final EducationService educationService;
    final AnnouncementService announcementService;
    final PersonalInfoService personalInfoService;
    final CertificationService certificationService;
    final WorkExperienceService workExperienceService;
    final UserAppAnnouncementService employerUserAppService;
    final UserAppAnnouncementService userAppAnnouncementService;

    @GetMapping("/resume/{id}")
    String getResumePage(Model model, @PathVariable String id, @RequestParam(required = false) final Boolean errors) {
        navbarService.activateNavbarTab(Page.RESUME, model);
        var userApp = userAppService.getById(id);
        Util.extractRole(model, userApp);

        String docName;
        if (documentService.getDocumentByUserApp(userApp).isEmpty()) {
            docName = "";
        } else {
            docName = documentService.getDocumentByUserApp(userApp).get().getName();
        }

        PersonalInfo personalInfo;
        if (personalInfoService.getUserInfoByUserApp(userApp).isEmpty()) {
            personalInfo = new PersonalInfo();
            personalInfo.setId(UUID.randomUUID().toString());
            personalInfo.setLocation("");
            personalInfo.setLanguage("");
            personalInfo.setSkills("");
            personalInfo.setMainAreas("");
            personalInfo.setUserApp(userApp);
        } else {
            personalInfo = personalInfoService.getUserInfoByUserApp(userApp).get();
        }

        var workExperiences = workExperienceService.getWorkExperienceModelList(userApp);

        var certification = certificationService.getAllByUserApp(userApp);

        var education = educationService.getEducationModelList(userApp);

        model.addAttribute("userApp", userApp);
        model.addAttribute("documentName", docName);
        model.addAttribute("isDocument", documentService.getDocumentByUserApp(userApp).isPresent());
        model.addAttribute("personalInfo", personalInfo);
        model.addAttribute("workExperiences", workExperiences);
        model.addAttribute("certification", certification);
        model.addAttribute("education", education);
        model.addAttribute("errors", errors);

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

    @PostMapping("/resume/{fileName}")
    public ResponseEntity<String> deleteDocument(@PathVariable String fileName) {
        try {
            documentService.deleteDocumentById(documentService.getDocumentByName(fileName).getId());

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error deleting the resume document with name: " + fileName, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/edit-personal-info/{user_id}/{id}")
    public String editPersonalInfo(@ModelAttribute("userInfo") PersonalInfo personalInfo, @PathVariable("id") final String id,
                                   @PathVariable("user_id") final String userId) {
        var user = userAppService.getById(userId);

        personalInfo.setId(id);
        personalInfo.setUserApp(user);
        personalInfoService.save(personalInfo);

        return "redirect:/resume/" + userId;
    }

    @PostMapping("/add-work-experience/{user_id}")
    public String addWorkExperience(@ModelAttribute("workExperiences") WorkExperience workExperience,
                                   @PathVariable("user_id") final String userId) {
        var user = userAppService.getById(userId);

        if (workExperience.getEndDate() != null && workExperience.getStartDate().isAfter(workExperience.getEndDate())) {
            return "redirect:/resume/" + userId + "?errors=true";
        }

        workExperience.setUserApp(user);
        workExperienceService.save(workExperience);

        return "redirect:/resume/" + userId;
    }

    @PostMapping("/edit-work-experience/{user_id}/{id}")
    public String editWorkExperience(@ModelAttribute("workExperiences") WorkExperience workExperience, @PathVariable("id") final String id,
                                   @PathVariable("user_id") final String userId) {
        var user = userAppService.getById(userId);

        if (workExperience.getEndDate() != null && workExperience.getStartDate().isAfter(workExperience.getEndDate())) {
            return "redirect:/resume/" + userId + "?errors=true";
        }

        workExperience.setId(id);
        workExperience.setUserApp(user);
        workExperienceService.save(workExperience);

        return "redirect:/resume/" + userId;
    }

    @PostMapping("/delete-work-experience/{user_id}/{id}")
    public String deleteWorkExperience(@PathVariable String id, @PathVariable("user_id") final String userId) {
        log.info("Try to delete a work experience");
        var workExperience = workExperienceService.getById(id);

        workExperienceService.delete(workExperience);

        return "redirect:/resume/" + userId;
    }

    @PostMapping("/add-certification/{user_id}")
    public String addCertification(@ModelAttribute("certification") Certification certification,
                                    @PathVariable("user_id") final String userId) {
        var user = userAppService.getById(userId);

        certification.setUserApp(user);
        certificationService.save(certification);

        return "redirect:/resume/" + userId;
    }

    @PostMapping("/edit-certification/{user_id}/{id}")
    public String editCertification(@ModelAttribute("certification") Certification certification, @PathVariable("id") final String id,
                                     @PathVariable("user_id") final String userId) {
        var user = userAppService.getById(userId);

        certification.setId(id);
        certification.setUserApp(user);
        certificationService.save(certification);

        return "redirect:/resume/" + userId;
    }

    @PostMapping("/delete-certification/{user_id}/{id}")
    public String deleteCertification(@PathVariable String id, @PathVariable("user_id") final String userId) {
        log.info("Try to delete a job");
        var certification = certificationService.getById(id);

        certificationService.delete(certification);

        return "redirect:/resume/" + userId;
    }

    @PostMapping("/add-education/{user_id}")
    public String addEducation(@ModelAttribute("education") Education education,
                                   @PathVariable("user_id") final String userId) {
        var user = userAppService.getById(userId);

        if (education.getEndDate() != null && education.getStartDate().isAfter(education.getEndDate())) {
            return "redirect:/resume/" + userId + "?errors=true";
        }

        education.setUserApp(user);
        educationService.save(education);

        return "redirect:/resume/" + userId;
    }

    @PostMapping("/edit-education/{user_id}/{id}")
    public String editEducation(@ModelAttribute("education") Education education, @PathVariable("id") final String id,
                                    @PathVariable("user_id") final String userId) {
        var user = userAppService.getById(userId);

        if (education.getEndDate() != null && education.getStartDate().isAfter(education.getEndDate())) {
            return "redirect:/resume/" + userId + "?errors=true";
        }

        education.setId(id);
        education.setUserApp(user);
        educationService.save(education);

        return "redirect:/resume/" + userId;
    }

    @PostMapping("/delete-education/{user_id}/{id}")
    public String deleteEducation(@PathVariable String id, @PathVariable("user_id") final String userId) {
        log.info("Try to delete a job");
        var education = educationService.getById(id);

        educationService.delete(education);

        return "redirect:/resume/" + userId;
    }
}
