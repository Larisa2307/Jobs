package com.example.jobs.controller;

import com.example.jobs.entity.Page;
import com.example.jobs.service.*;
import com.example.jobs.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@Slf4j
public class CandidateProfileController {
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

    @GetMapping("/candidate/{userId}/{annId}/{id}")
    String getResumePage(Model model, @PathVariable("id") String id, @PathVariable("userId") String userId,
                         @PathVariable("annId") String annId) {
        var userApp = userAppService.getById(userId);
        Util.extractRole(model, userApp);

        var candidate = userAppService.getById(id);

        String docName;
        if (documentService.getDocumentByUserApp(candidate).isEmpty()) {
            docName = "";
        } else {
            docName = documentService.getDocumentByUserApp(candidate).get().getName();
        }

        var personalInfo = personalInfoService.getUserInfoByUserApp(candidate);

        var workExperiences = workExperienceService.getWorkExperienceModelList(candidate);

        var certification = certificationService.getAllByUserApp(candidate);

        var education = educationService.getEducationModelList(candidate);

        model.addAttribute("userApp", userApp);
        model.addAttribute("candidate", candidate);
        model.addAttribute("announcementId", annId);
        model.addAttribute("documentName", docName);
        model.addAttribute("isDocument", documentService.getDocumentByUserApp(candidate).isPresent());
        model.addAttribute("personalInfo", personalInfo.get());
        model.addAttribute("hasPersonalInfo", personalInfo.isPresent());
        model.addAttribute("workExperiences", workExperiences);
        model.addAttribute("hasWorkExperiences", workExperiences.isEmpty());
        model.addAttribute("certification", certification);
        model.addAttribute("hasCertification", certification.isEmpty());
        model.addAttribute("education", education);
        model.addAttribute("hasEducation", education.isEmpty());

        return "candidate";
    }

    @PostMapping("/accepted-candidate/{userId}/{annId}/{id}")
    public String acceptCandidate(@PathVariable("id") String id,
                                 @PathVariable("annId") String annId,
                                 @PathVariable("userId") String userId) {
        log.info("Try to accept a candidate");
        var candidate = userAppService.getById(id);
        var announcement = announcementService.getAnnouncementById(annId);
        var annCandidate = userAppAnnouncementService
                .getUserAppAnnouncementByUserAndAnnouncement(candidate, announcement).get();

        annCandidate.setDecision("Accepted");
        userAppAnnouncementService.save(annCandidate);

        return "redirect:/candidate/" + userId + "/" + annId + "/" + id;
    }

    @PostMapping("/interview-candidate/{userId}/{annId}/{id}")
    public String interviewCandidate(@PathVariable("id") String id,
                                  @PathVariable("annId") String annId,
                                  @PathVariable("userId") String userId) {
        log.info("Try to accept a candidate");
        var candidate = userAppService.getById(id);
        var announcement = announcementService.getAnnouncementById(annId);
        var annCandidate = userAppAnnouncementService
                .getUserAppAnnouncementByUserAndAnnouncement(candidate, announcement).get();

        annCandidate.setDecision("Interview");
        userAppAnnouncementService.save(annCandidate);

        return "redirect:/candidate/" + userId + "/" + annId + "/" + id;
    }

    @PostMapping("/rejected-candidate/{userId}/{annId}/{id}")
    public String rejectCandidate(@PathVariable("id") String id,
                                 @PathVariable("annId") String annId,
                                 @PathVariable("userId") String userId) {
        log.info("Try to delete a job");
        var candidate = userAppService.getById(id);
        var announcement = announcementService.getAnnouncementById(annId);
        var annCandidate = userAppAnnouncementService
                .getUserAppAnnouncementByUserAndAnnouncement(candidate, announcement).get();

        annCandidate.setDecision("Rejected");
        userAppAnnouncementService.save(annCandidate);

        return "redirect:/candidate/" + userId + "/" + annId + "/" + id;
    }
}
