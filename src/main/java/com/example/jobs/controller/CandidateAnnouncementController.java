package com.example.jobs.controller;

import com.example.jobs.entity.Page;
import com.example.jobs.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
@Slf4j
public class CandidateAnnouncementController {
    final CompanyService companyService;
    final UserCompanyService userCompanyService;
    final AnnouncementService announcementService;
    final UserAppAnnouncementService userAppAnnouncementService;

    @GetMapping("/candidate-announcement/{user_id}/{id}")
    String getDashboardEmployerPage(Model model,  @PathVariable("user_id") String userId, @PathVariable("id") String id) {

        var announcement = announcementService.getAnnouncementById(id);
        var userCompany = userCompanyService.getById(userId);
        log.info("Candidates for announcement " + announcement.getJob().getName() +
                " for company: " + announcement.getJob().getCompany().getCompanyName());

        var candidates = userAppAnnouncementService.getUserAppByAnnouncement(announcement);

        model.addAttribute("candidates", candidates);
        model.addAttribute("userCompany", userCompany);

        return "candidate-announcement";
    }
}
