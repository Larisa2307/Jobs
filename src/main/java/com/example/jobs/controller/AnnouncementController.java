package com.example.jobs.controller;

import com.example.jobs.entity.Job;
import com.example.jobs.entity.Page;
import com.example.jobs.model.AnnouncementModel;
import com.example.jobs.service.AnnouncementService;
import com.example.jobs.service.CompanyService;
import com.example.jobs.service.JobService;
import com.example.jobs.service.NavbarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@Slf4j
public class AnnouncementController {

    final JobService jobService;
    final NavbarService navbarService;
    final CompanyService companyService;
    final AnnouncementService announcementService;

    @GetMapping("/announcement/{id}")
    String getAnnouncementPage(Model model, @PathVariable String id, @RequestParam(required = false) final Boolean duplicate) {
        var company = companyService.getById(id);
        log.info("Announcement page: " + company.getEmail());
        var announcements = announcementService.getAnnouncementModelList(company);

        model.addAttribute("company", company);
        model.addAttribute("announcements", announcements);
        model.addAttribute("duplicate", duplicate);

        navbarService.activateNavbarTab(Page.ANNOUNCEMENT, model);

        return "announcement";
    }

    @PostMapping("/edit-announcement/{id}")
    public String editCategory(@ModelAttribute("announcement") AnnouncementModel announcement, @PathVariable("id") final String id) {
        Job job = announcementService.getAnnouncementById(id).getJob();
        var company = job.getCompany();
        log.info("Try to edit an announcement: " + announcement.getJobName() + " company: " + company.getEmail());

        if (announcement.getBenefit() == null) {
            announcement.setBenefit("");
        }

        announcementService.saveNewAnnouncement(announcement, job);

        return "redirect:/announcement/" + company.getId();
    }

    @PostMapping("/delete-announcement/{announcementId}")
    public String deleteCategory(@PathVariable String announcementId) {
        var company = announcementService.getAnnouncementById(announcementId).getJob().getCompany();
        log.info("Try to delete an announcement");
        announcementService.deleteAnnouncement(announcementId);

        return "redirect:/announcement/" + company.getId();
    }
}
