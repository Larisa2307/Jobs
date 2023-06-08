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
public class AnnouncementInfoController {

    final NavbarService navbarService;
    final CompanyService companyService;
    final UserAppService userAppService;
    final AnnouncementService announcementService;

    @GetMapping("/announcement-info/{user_id}/{id}")
    String getAnnouncementPage(Model model, @PathVariable("user_id") String userId, @PathVariable("id") String id) {
        navbarService.activateNavbarTab(Page.DASHBOARD, model);

        var announcementModel = announcementService.getAnnouncementModelById(id);
        var userApp = userAppService.getById(userId);
        Util.extractRole(model, userApp);
        log.info("Announcement info " + announcementModel.getJobName() +
                " for company: " + announcementModel.getCompanyName());

        model.addAttribute("announcement", announcementModel);
        model.addAttribute("userApp", userApp);

        return "announcement-info";
    }

}
