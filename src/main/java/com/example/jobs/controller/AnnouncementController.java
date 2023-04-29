package com.example.jobs.controller;

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
    final NavbarService navbarService;
    final CompanyService companyService;
    final AnnouncementService announcementService;
    final JobService jobService;

    @GetMapping("/announcement/{id}")
    String getAnnouncementPage(Model model, @PathVariable String id, @RequestParam(required = false) final Boolean duplicate) {
        var company = companyService.getById(id);
        log.info("Announcement page: " + company.getEmail());
        var announcements = announcementService.getAnnouncementModelList(company);
        var jobNames = jobService.getJobNameByCompany(company);
        var jobTypes = jobService.getJobTypeByCompany(company);
        var jobLevels = jobService.getJobLevelsByCompany(company);

        model.addAttribute("company", company);
        model.addAttribute("announcements", announcements);
        model.addAttribute("duplicate", duplicate);
//        model.addAttribute("jobNames", jobNames);
//        model.addAttribute("jobType", jobTypes);
//        model.addAttribute("jobLevels", jobLevels);
        navbarService.activateNavbarTab(Page.ANNOUNCEMENT, model);

        return "announcement";
    }

    @PostMapping("/add-announcement/{id}")
    public String addAnnouncement(@ModelAttribute("announcement") AnnouncementModel announcement,
                                  @PathVariable String id) {
        var company = companyService.getById(id);
        log.info("Try to add an announcement: " + company.getEmail());
        if (Boolean.TRUE.equals(announcementService.isDuplicate(announcement.getJobName()))) {
            return "redirect:/announcement/" + id + "?duplicate=true";
        } else {
            if (announcement.getBenefit() == null) {
                announcement.setBenefit("");
            }

            announcementService.saveNewAnnouncement(announcement, company);
        }

        return "redirect:/announcement/" + id;
    }

    @PostMapping("/edit-announcement/{id}")
    public String editCategory(@ModelAttribute("announcement") AnnouncementModel announcement, @PathVariable("id") final String id) {
        var company = announcementService.getAnnouncementById(id).getCompany();
        log.info("Try to edit an announcement: " + announcement.getJobName() + " company: " + company.getEmail());

        if (Boolean.TRUE.equals(announcementService.isDuplicateName(announcement.getJobName(), announcement.getId()))) {
            return "redirect:/announcement/" + company.getId() + "?duplicate=true";
        } else {
            if (announcement.getBenefit() == null) {
                announcement.setBenefit("");
            }

            announcementService.saveNewAnnouncement(announcement, company);
        }

        return "redirect:/announcement/" + company.getId();
    }

    @PostMapping("/delete-announcement/{announcementId}")
    public String deleteCategory(@PathVariable String announcementId) {
        var company = announcementService.getAnnouncementById(announcementId).getCompany();
        log.info("Try to delete an announcement");
        announcementService.deleteAnnouncement(announcementId);

        return "redirect:/announcement/" + company.getId();
    }
}
