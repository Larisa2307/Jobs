package com.example.jobs.controller;

import com.example.jobs.entity.Page;
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
public class MyApplicationPage {

    final NavbarService navbarService;
    final CompanyService companyService;
    final UserAppService userAppService;
    final AnnouncementService announcementService;
    final UserAppAnnouncementService employerUserAppService;
    final UserAppAnnouncementService userAppAnnouncementService;

    @GetMapping("/my-applications/{id}")
    String getMyApplicationPage(Model model, @PathVariable String id) {
        navbarService.activateNavbarTab(Page.MY_APPLICATION, model);
        var userApp = userAppService.getById(id);
        Util.extractRole(model, userApp);

        var announcements = userAppAnnouncementService.getCandidateModelList(userApp);

        model.addAttribute("userApp", userApp);
        model.addAttribute("announcements", announcements);

        return "my-applications";
    }
}
