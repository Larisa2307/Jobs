package com.example.jobs.controller;

import com.example.jobs.entity.Page;
import com.example.jobs.entity.UserAppAnnouncement;
import com.example.jobs.service.*;
import com.example.jobs.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@RequiredArgsConstructor
@Controller
@Slf4j
public class AnnouncementInfoController {

    final NavbarService navbarService;
    final CompanyService companyService;
    final UserAppService userAppService;
    final DocumentService documentService;
    final PersonalInfoService personalInfoService;
    final AnnouncementService announcementService;
    final UserAppAnnouncementService userAppAnnouncementService;

    @GetMapping("/announcement-info/{user_id}/{id}")
    String getAnnouncementInfoPage(Model model, @PathVariable("user_id") String userId, @PathVariable("id") String id,
                               @RequestParam(required = false) final Boolean error,
                               @RequestParam(required = false) final Boolean duplicate) {
        navbarService.activateNavbarTab(Page.DASHBOARD, model);

        var announcementModel = announcementService.getAnnouncementModelById(id);
        log.info("Announcement info " + announcementModel.getJobName() +
                " for company: " + announcementModel.getCompanyName());

        var userApp = userAppService.getById(userId);
        Util.extractRole(model, userApp);

        boolean areBenefits = announcementModel.getBenefit() == null || "".equals(announcementModel.getBenefit());

        model.addAttribute("announcement", announcementModel);
        model.addAttribute("userApp", userApp);
        model.addAttribute("areBenefits", areBenefits);
        model.addAttribute("error", error);
        model.addAttribute("duplicate", duplicate);

        return "announcement-info";
    }

    @GetMapping("/announcement-info/{id}")
    String getMainAnnouncementInfoPage(Model model, @PathVariable("id") String id) {

        var announcementModel = announcementService.getAnnouncementModelById(id);
        log.info("Announcement info " + announcementModel.getJobName() +
                " for company: " + announcementModel.getCompanyName());


        boolean areBenefits = announcementModel.getBenefit() == null || "".equals(announcementModel.getBenefit());

        model.addAttribute("announcement", announcementModel);
        model.addAttribute("areBenefits", areBenefits);

        return "main-announcement-info";
    }


    @PostMapping("/apply/{user_id}/{id}")
    public String applyPost(@PathVariable("id") String id, @PathVariable("user_id") final String userId) {
        var user = userAppService.getById(userId);
        var announcementModel = announcementService.getAnnouncementModelById(id);
        var announcement = announcementService.getAnnouncementById(id);
        log.info("User" + user.getFirstName() + " " + user.getLastName() +
                " try to apply an announcement: " + announcementModel.getJobName() +
                " at company " + announcementModel.getCompanyName());

        var document = documentService.getDocumentByUserApp(user);
        var personalInfo = personalInfoService.getUserInfoByUserApp(user);
        var application = userAppAnnouncementService.getUserAppAnnouncementByUserAndAnnouncement(user, announcement);

        if (document.isEmpty() && personalInfo.isEmpty()) {
            return "redirect:/announcement-info/" + userId + "/" + id + "?error=true";
        }

        if (application.isPresent()) {
            return "redirect:/announcement-info/" + userId + "/" + id + "?duplicate=true";
        }

        UserAppAnnouncement userAppAnnouncement = new UserAppAnnouncement();
        userAppAnnouncement.setId(UUID.randomUUID().toString());
        userAppAnnouncement.setUserApp(user);
        userAppAnnouncement.setAnnouncement(announcement);

        userAppAnnouncementService.save(userAppAnnouncement);

        return "redirect:/announcement-info/" + userId + "/" + id;
    }

    @PostMapping("/apply")
    public String applyPost() {


        return "main-announcement-info";
    }

}
