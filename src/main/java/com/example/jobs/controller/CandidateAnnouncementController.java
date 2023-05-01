package com.example.jobs.controller;

import com.example.jobs.entity.Page;
import com.example.jobs.service.AnnouncementService;
import com.example.jobs.service.NavbarService;
import com.example.jobs.service.UserAppAnnouncementService;
import com.example.jobs.service.UserCompanyService;
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
    final UserCompanyService userCompanyService;
    final AnnouncementService announcementService;
    final UserAppAnnouncementService userAppAnnouncementService;

    @GetMapping("/candidate-announcement/{id}")
    String getDashboardEmployerPage(Model model, @PathVariable("id") String id) {

        var announcement = announcementService.getAnnouncementById(id);
        var company = announcement.getJob().getCompany();
        log.info("Candidates for announcement " + announcement.getJob().getName() +
                " for company: " + company.getCompanyName());

        var candidates = userAppAnnouncementService.getCandidateModelList(announcement);

        model.addAttribute("candidates", candidates);
        model.addAttribute("company", company);

        return "candidate-announcement";
    }
}
