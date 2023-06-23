package com.example.jobs.controller;

import com.example.jobs.entity.Job;
import com.example.jobs.entity.Page;
import com.example.jobs.model.AnnouncementModel;
import com.example.jobs.service.*;
import com.example.jobs.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping
public class AnnouncementController {

    final JobService jobService;
    final NavbarService navbarService;
    final CompanyService companyService;
    final UserAppService userAppService;
    final AnnouncementService announcementService;

    @GetMapping("/announcement/{id}")
    String getAnnouncementPage(Model model, @PathVariable String id) {
        navbarService.activateNavbarTab(Page.ANNOUNCEMENT, model);

        var userApp = userAppService.getById(id);
        log.info("Announcement page: " + userApp.getEmail());
        Util.extractRole(model, userApp);

        var company = userApp.getCompany();
        var announcements = announcementService.getAnnouncementModelList(company);

        model.addAttribute("userApp", userApp);
        model.addAttribute("announcements", announcements);

        return "announcement";
    }

    @PostMapping("/edit-announcement/{user_id}/{id}")
    public String editCategory(@ModelAttribute("announcement") AnnouncementModel announcement,
                               @PathVariable("id") final String id,
                               @PathVariable("user_id") final String userId) {
        Job job = announcementService.getAnnouncementById(id).getJob();
        var company = job.getCompany();
        log.info("Try to edit an announcement: " + announcement.getJobName() + " company: " + company.getCompanyName());

        if (announcement.getBenefit() == null) {
            announcement.setBenefit("");
        }
        announcementService.saveNewAnnouncement(announcement, job);

        return "redirect:/announcement/" + userId;
    }

    @PostMapping("/delete-announcement/{user_id}/{announcementId}")
    public String deleteCategory(@PathVariable String announcementId, @PathVariable("user_id") final String userId) {
        log.info("Try to delete an announcement");
        announcementService.deleteAnnouncement(announcementId);

        return "redirect:/announcement/" + userId;
    }
}

