package com.example.jobs.controller;

import com.example.jobs.entity.Page;
import com.example.jobs.entity.UserApp;
import com.example.jobs.service.*;
import com.example.jobs.util.Util;
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
    final UserAppService userAppService;
    final AnnouncementService announcementService;
    final UserAppAnnouncementService userAppAnnouncementService;

    @GetMapping("/candidate-announcement/{user_id}/{id}")
    String getDashboardEmployerPage(Model model,  @PathVariable("user_id") String userId, @PathVariable("id") String id) {

        var announcement = announcementService.getAnnouncementById(id);
        var announcementModel = announcementService.getAnnouncementModelById(id);
        var userApp = userAppService.getById(userId);
        Util.extractRole(model, userApp);
        log.info("Candidates for announcement " + announcement.getJob().getName() +
                " for company: " + announcement.getJob().getCompany().getCompanyName());

        var candidates = userAppAnnouncementService.getUserAppByAnnouncement(announcement);
        model.addAttribute("announcement", announcementModel);
        model.addAttribute("candidates", candidates);
        model.addAttribute("userApp", userApp);

        return "candidate-announcement";
    }


}
