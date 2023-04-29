package com.example.jobs.controller;

import com.example.jobs.entity.Page;
import com.example.jobs.service.AnnouncementService;
import com.example.jobs.service.CompanyService;
import com.example.jobs.service.NavbarService;
import com.example.jobs.service.UserAppAnnouncementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
@Slf4j
public class DashboardController {

    final NavbarService navbarService;
    final CompanyService companyService;
    final AnnouncementService announcementService;
    final UserAppAnnouncementService employerUserAppService;

    @GetMapping("/dashboard")
    String getDashboardPage(Model model) {
        navbarService.activateNavbarTab(Page.DASHBOARD, model);

        return "dashboard";
    }


    @GetMapping("/dashboard-company/{id}")
    String getDashboardEmployerPage(Model model, @PathVariable String id) {
        var company = companyService.getById(id);
        log.info("Dashboard company: " + company.getEmail());
        var announcements = announcementService.getAnnouncementModelList(company);
        model.addAttribute("company", company);
        model.addAttribute("announcements", announcements);
        navbarService.activateNavbarTab(Page.DASHBOARD_COMPANY, model);
        return "dashboard-company";
    }

}
