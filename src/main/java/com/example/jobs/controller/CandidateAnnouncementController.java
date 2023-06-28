package com.example.jobs.controller;

import com.example.jobs.entity.Page;
import com.example.jobs.entity.UserApp;
import com.example.jobs.service.*;
import com.example.jobs.util.DateFormat;
import com.example.jobs.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
@Slf4j
public class CandidateAnnouncementController {
    final NavbarService navbarService;
    final CompanyService companyService;
    final UserAppService userAppService;
    final MailSenderService mailSenderService;
    final AnnouncementService announcementService;
    final UserAppAnnouncementService userAppAnnouncementService;

    @GetMapping("/candidate-announcement/{user_id}/{id}")
    String getCandidatesPage(Model model, @PathVariable("user_id") String userId, @PathVariable("id") String id) {

        var announcement = announcementService.getAnnouncementById(id);
        var announcementModel = announcementService.getAnnouncementModelById(id);
        var userApp = userAppService.getById(userId);
        Util.extractRole(model, userApp);
        log.info("Candidates for announcement " + announcement.getJob().getName() +
                " for company: " + announcement.getJob().getCompany().getCompanyName());

        var candidates = userAppAnnouncementService.getCandidateModelList(announcement);

        model.addAttribute("announcement", announcementModel);
        model.addAttribute("candidates", candidates);
        model.addAttribute("userApp", userApp);

        return "candidate-announcement";
    }


    @PostMapping("/send-decision/{userId}/{annId}/{id}")
    public String sendAcceptedCandidate(@ModelAttribute("decision") String decision,
                                        @PathVariable("id") String id,
                                        @PathVariable("annId") String annId,
                                        @PathVariable("userId") String userId) {
        log.info("Try to send decision to candidate");
        var candidate = userAppService.getById(id);
        var announcement = announcementService.getAnnouncementById(annId);
        var annCandidate = userAppAnnouncementService
                .getUserAppAnnouncementByUserAndAnnouncement(candidate, announcement).get();

        annCandidate.setAccepted(decision);
        userAppAnnouncementService.save(annCandidate);
        mailSenderService.sendMailWithDecision(candidate.getEmail(), decision,
                announcement.getJob().getCompany().getCompanyName(), announcement.getJob().getName());

        return "redirect:/candidate-announcement/" + userId + "/" + annId;
    }

    @PostMapping("/schedule-interview/{userId}/{annId}/{id}")
    public String scheduleInterviewCandidate(@ModelAttribute("dateAndHour") LocalDateTime date,
                                             @PathVariable("id") String id,
                                             @PathVariable("annId") String annId,
                                             @PathVariable("userId") String userId) {
        log.info("Try to send decision to candidate");
        var candidate = userAppService.getById(id);
        var announcement = announcementService.getAnnouncementById(annId);

        mailSenderService.sendMailToScheduleInterview(candidate.getEmail(),
                announcement.getJob().getCompany().getCompanyName(), announcement.getJob().getName(),
                DateFormat.dateFormat(date), DateFormat.timeFormat(date));

        return "redirect:/candidate-announcement/" + userId + "/" + annId;
    }
}
