package com.example.jobs.controller;

import com.example.jobs.service.AnnouncementService;
import com.example.jobs.service.CompanyService;
import com.example.jobs.service.NavbarService;
import com.example.jobs.service.UserAppAnnouncementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
@Slf4j
public class MainPageController {

    final NavbarService navbarService;
    final CompanyService companyService;
    final AnnouncementService announcementService;
    final UserAppAnnouncementService employerUserAppService;

    @GetMapping("/")
    String getStartPage() {
        return "redirect:/main-page";
    }

    @GetMapping("/main-page")
    String getMainPage(Model model) {

        var announcements = announcementService.getAvailableAnnouncements();

        model.addAttribute("announcements", announcements);
        return "main-page";
    }

    @GetMapping("/accounts")
    String getAccountsPage(Model model) {

        return "accounts";
    }

}
