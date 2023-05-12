package com.example.jobs.controller;

import com.example.jobs.entity.Page;
import com.example.jobs.entity.UserCompany;
import com.example.jobs.service.*;
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
    final UserCompanyService userCompanyService;
    final AnnouncementService announcementService;
    final UserAppAnnouncementService employerUserAppService;
    final UserAppAnnouncementService userAppAnnouncementService;

    @GetMapping("/dashboard")
    String getDashboardPage(Model model) {
        navbarService.activateNavbarTab(Page.DASHBOARD, model);

        return "dashboard";
    }


    @GetMapping("/dashboard-company/{id}")
    String getDashboardEmployerPage(Model model, @PathVariable String id) {
        var userCompany = userCompanyService.getById(id);
        var company = userCompany.getCompany();
        log.info("Dashboard company: " + company.getCompanyName() + ", user: " +userCompany.getLastName());
        var announcements = announcementService.getAnnouncementModelList(company);

        model.addAttribute("userCompany", userCompany);
        model.addAttribute("announcements", announcements);

        navbarService.activateNavbarTab(Page.DASHBOARD_COMPANY, model);
        return "dashboard-company";
    }

    @PostMapping("/delete-announcement-dashboard/{user_id}/{announcementId}")
    public String deleteCategory(@PathVariable String announcementId, @PathVariable("user_id") final String userId) {
        log.info("Try to delete an announcement");
        announcementService.deleteAnnouncement(announcementId);

        return "redirect:/dashboard-company/" + userId;
    }

}
