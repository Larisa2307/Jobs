package com.example.jobs.controller;

import com.example.jobs.entity.Page;
import com.example.jobs.model.AnnouncementModel;
import com.example.jobs.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@Slf4j
public class JobsController {

    final JobService jobService;
    final NavbarService navbarService;
    final CompanyService companyService;
    final UserCompanyService userCompanyService;
    final AnnouncementService announcementService;
    final UserAppAnnouncementService userAppAnnouncementService;


    @GetMapping("/jobs-company/{id}")
    String getDashboardEmployerPage(Model model, @PathVariable String id, @RequestParam(required = false) final Boolean duplicate) {
        var userCompany = userCompanyService.getById(id);
        var company = userCompany.getCompany();
        log.info("Jobs company: " + company.getCompanyName());
        var jobs = jobService.getJobsByCompany(company);

        model.addAttribute("userCompany", userCompany);
        model.addAttribute("jobs", jobs);
        model.addAttribute("duplicate", duplicate);


        navbarService.activateNavbarTab(Page.JOBS_COMPANY, model);
        return "jobs-company";
    }

    @PostMapping("/add-announcement/{user_id}/{id}")
    public String addAnnouncement(@ModelAttribute("announcement") AnnouncementModel announcement,
                                  @PathVariable("user_id") final String userId,
                                  @PathVariable("id") String id) {

        var job = jobService.getById(id);
        var company = job.getCompany();
        log.info("Try to add an announcement: " + company.getEmail());

        if (Boolean.TRUE.equals(announcementService.isDuplicate(job))) {
            return "redirect:/jobs-company/" + userId + "?duplicate=true";

        } else {
            if (announcement.getBenefit() == null) {
                announcement.setBenefit("");
            }
            announcementService.saveNewAnnouncement(announcement, job);
        }

        return "redirect:/announcement/" + userId;
    }
}
