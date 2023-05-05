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
public class UserCompany {
    final NavbarService navbarService;
    final CompanyService companyService;
    final UserCompanyService userCompanyService;
    final AnnouncementService announcementService;
    final UserAppAnnouncementService employerUserAppService;
    final UserAppAnnouncementService userAppAnnouncementService;

    @GetMapping("/users-company/{id}")
    String getUsersCompanyPage(Model model, @PathVariable String id) {
        var userCompany = userCompanyService.getById(id);
        var company = userCompany.getCompany();
        log.info("Users company: " + company.getCompanyName());
        var usersCompany = userCompanyService.getUserByCompany(company);

        model.addAttribute("userCompany", userCompany);
        model.addAttribute("usersCompany", usersCompany);

        navbarService.activateNavbarTab(Page.USERS_COMPANY, model);
        return "users-company";
    }
}
