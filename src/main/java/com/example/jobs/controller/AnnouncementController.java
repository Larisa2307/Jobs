package com.example.jobs.controller;

import com.example.jobs.entity.Page;
import com.example.jobs.model.AnnouncementModel;
import com.example.jobs.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class AnnouncementController {
    final NavbarService navbarService;
    final EmployerService employerService;
    final AnnouncementService announcementService;
    final IndustryService industryService;
    final SkillService skillService;
    final AnnouncementSkillService announcementSkillService;

    @GetMapping("/announcement/{id}")
    String getAnnouncementPage(Model model, @PathVariable String id, @RequestParam(required = false) final Boolean duplicate) {
        var employer = employerService.getbyId(id);
        var announcements = announcementService.getAnnouncementModelList(employer);
        var industry = industryService.findAll();
        var skills = skillService.findAll();

        model.addAttribute("employer", employer);
        model.addAttribute("industry", industry);
        model.addAttribute("skill", skills);
        model.addAttribute("announcements", announcements);
        model.addAttribute("duplicate", duplicate);
        navbarService.activateNavbarTab(Page.ANNOUNCEMENT, model);

        return "announcement";
    }

    @PostMapping("/add-announcement/{id}")
    public String addAnnouncement(@ModelAttribute("announcement") AnnouncementModel announcementModel,
                                  @PathVariable String id) {
        var employer = employerService.getbyId(id);
        if (Boolean.TRUE.equals(announcementService.isDuplicate(announcementModel.getJobName()))) {
            return "redirect:/announcement/" + id + "?duplicate=true";
        } else {
            if (announcementModel.getBenefit() == null) {
                announcementModel.setBenefit("");
            }
            announcementService.saveNewAnnouncement(announcementModel, employer);
        }

        return "redirect:/announcement/" + id;
    }

    @PostMapping("/edit-announcement/{id}")
    public String editCategory(@ModelAttribute("announcement") AnnouncementModel announcementModel, @PathVariable("id") final String id) {
        var employer = announcementService.getAnnouncementById(id).getEmployer();

        if (Boolean.TRUE.equals(announcementService.isDuplicateName(announcementModel.getJobName(), announcementModel.getId()))) {
            return "redirect:/announcement/" + employer.getId() + "?duplicate=true";
        } else {
            if (announcementModel.getBenefit() == null) {
                announcementModel.setBenefit("");
            }
            announcementService.saveNewAnnouncement(announcementModel, employer);
        }

        return "redirect:/announcement/" + employer.getId();
    }

    @PostMapping("/delete-announcement/{announcementId}")
    public String deleteCategory(@PathVariable String announcementId) {
        var employee = announcementService.getAnnouncementById(announcementId).getEmployer();
        announcementSkillService.getSkillsByAnnouncement(announcementService.getAnnouncementById(announcementId))
                .forEach(announcementSkillService::deleteAnnouncementSkills);
        announcementService.deleteAnnouncement(announcementId);

        return "redirect:/announcement/" + employee.getId();
    }
}
