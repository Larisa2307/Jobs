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
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
@Slf4j
public class DashboardController {

    final NavbarService navbarService;
    final CompanyService companyService;
    final UserAppService userAppService;
    final AnnouncementService announcementService;
    final UserAppAnnouncementService employerUserAppService;
    final UserAppAnnouncementService userAppAnnouncementService;

    @GetMapping("/dashboard/{id}")
    String getDashboardPage(Model model, @PathVariable String id) {
        navbarService.activateNavbarTab(Page.DASHBOARD, model);
        var userApp = userAppService.getById(id);
        Util.extractRole(model, userApp);

        var announcements = announcementService.getAvailableAnnouncements();

        model.addAttribute("userApp", userApp);
        model.addAttribute("announcements", announcements);

        return "dashboard";
    }

    @GetMapping("/dashboard-company/{id}")
    String getDashboardEmployerPage(Model model, @PathVariable String id) {
        navbarService.activateNavbarTab(Page.DASHBOARD, model);

        var userApp = userAppService.getById(id);
        Util.extractRole(model, userApp);
        var company = userApp.getCompany();
        log.info("Dashboard company: " + company.getCompanyName() + ", user: " + userApp.getLastName());
        var announcements = announcementService.getAnnouncementModelList(company);

        model.addAttribute("userApp", userApp);
        model.addAttribute("announcements", announcements);

        return "dashboard-company";
    }

    @PostMapping("/delete-announcement-dashboard/{user_id}/{announcementId}")
    public String deleteCategory(@PathVariable String announcementId, @PathVariable("user_id") final String userId) {
        log.info("Try to delete an announcement");
        announcementService.deleteAnnouncement(announcementId);

        return "redirect:/dashboard-company/" + userId;
    }

}
