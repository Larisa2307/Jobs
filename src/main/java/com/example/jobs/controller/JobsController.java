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
public class JobsController {
    final NavbarService navbarService;
    final CompanyService companyService;
    final AnnouncementService announcementService;
    final UserAppAnnouncementService employerUserAppService;
    final JobService jobService;

    @GetMapping("/jobs-company/{id}")
    String getDashboardEmployerPage(Model model, @PathVariable String id) {
        var company = companyService.getById(id);
        log.info("Jobs company: " + company.getEmail());
        var jobs = jobService.getJobModelList(company);

        model.addAttribute("company", company);
        model.addAttribute("jobs", jobs);
        navbarService.activateNavbarTab(Page.JOBS_COMPANY, model);
        return "jobs-company";
    }
}
