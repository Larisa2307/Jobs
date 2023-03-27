package com.example.jobs.controller;

import com.example.jobs.entity.Page;
import com.example.jobs.service.AnnouncementService;
import com.example.jobs.service.EmployerService;
import com.example.jobs.service.UserAppAnnouncementService;
import com.example.jobs.service.NavbarService;
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
    final EmployerService employerService;
    final AnnouncementService announcementService;
    final UserAppAnnouncementService employerUserAppService;

    @GetMapping("/dashboard")
    String getDashboardPage(Model model) {
        navbarService.activateNavbarTab(Page.DASHBOARD, model);

        return "dashboard";
    }


    @GetMapping("/dashboard-employer/{id}")
    String getDashboardEmployerPage(Model model, @PathVariable String id) {
        var employer = employerService.getbyId(id);
        log.info("Dashboard employer: " + employer.getEmail());
        var announcements = announcementService.getAnnouncementModelList(employer);
        model.addAttribute("employer", employer);
        model.addAttribute("announcements", announcements);
        navbarService.activateNavbarTab(Page.DASHBOARD_EMPLOYER, model);
        return "dashboard-employer";
    }

}
