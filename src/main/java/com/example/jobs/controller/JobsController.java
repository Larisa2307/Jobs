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

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class JobsController {

    final JobService jobService;
    final NavbarService navbarService;
    final CompanyService companyService;
    final UserAppService userAppService;
    final AnnouncementService announcementService;
    final UserAppAnnouncementService userAppAnnouncementService;

    private static List<String> jobTypes = List.of("Internship", "Part-time", "Full-time");
    private static List<String> jobLevels = List.of("Junior", "Mid level", "Senior", "Manager");


    @GetMapping("/jobs-company/{id}")
    String getJobsPage(Model model, @PathVariable String id, @RequestParam(required = false) final Boolean duplicate) {
        navbarService.activateNavbarTab(Page.JOBS_COMPANY, model);

        var userApp = userAppService.getById(id);
        Util.extractRole(model, userApp);
        var company = userApp.getCompany();
        log.info("Jobs company: " + company.getCompanyName());
        var jobs = jobService.getJobsByCompany(company);

        model.addAttribute("userApp", userApp);
        model.addAttribute("jobs", jobs);
        model.addAttribute("duplicate", duplicate);
        model.addAttribute("jobTypes", jobTypes);
        model.addAttribute("jobLevels", jobLevels);

        return "jobs-company";
    }

    @PostMapping("/add-job/{user_id}")
    public String addJob(@ModelAttribute("job") Job job, @PathVariable("user_id") final String userId) {
        var user = userAppService.getById(userId);
        var company = user.getCompany();
        log.info("Try to add an announcement: " + company.getEmail());

        job.setCompany(company);
        jobService.save(job);

        return "redirect:/jobs-company/" + userId;
    }

    @PostMapping("/edit-job/{user_id}/{id}")
    public String editCategory(@ModelAttribute("job") Job job, @PathVariable("id") final String id,
                               @PathVariable("user_id") final String userId) {
        var user = userAppService.getById(userId);
        var company = user.getCompany();
        log.info("Try to edit a job: " + job.getName() + " company: " + company.getCompanyName());

        job.setId(id);
        job.setCompany(company);
        jobService.save(job);

        return "redirect:/jobs-company/" + userId;
    }


    @PostMapping("/delete-job/{user_id}/{id}")
    public String deleteCategory(@PathVariable String id, @PathVariable("user_id") final String userId) {
        log.info("Try to delete a job");
        var job = jobService.getById(id);

        announcementService.deleteAnnouncement(announcementService.getAnnouncementByJob(job).getId());
        jobService.delete(job);

        return "redirect:/jobs-company/" + userId;
    }

    @PostMapping("/add-announcement/{user_id}/{id}")
    public String addAnnouncement(@ModelAttribute("announcement") AnnouncementModel announcement,
                                  @PathVariable("user_id") final String userId,
                                  @PathVariable("id") String id) {

        var job = jobService.getById(id);
        var company = job.getCompany();
        log.info("Try to add an announcement: " + company.getEmail());

        if (Boolean.TRUE.equals(announcementService.isDuplicate(job))) {
            return "redirect:/jobs-company/" + userId + "?duplicate=true";

        } else {
            if (announcement.getBenefit() == null) {
                announcement.setBenefit("");
            }
            announcementService.saveNewAnnouncement(announcement, job);
        }

        return "redirect:/announcement/" + userId;
    }
}
