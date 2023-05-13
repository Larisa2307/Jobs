package com.example.jobs.controller;

import com.example.jobs.entity.Company;
import com.example.jobs.entity.UserApp;
import com.example.jobs.service.CompanyService;
import com.example.jobs.service.UserAppService;
import com.example.jobs.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ProfileController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    final CompanyService companyService;
    final UserAppService userAppService;

    @GetMapping("/profile/{id}")
    String getProfileIdPage(Model model, @PathVariable String id,
                            @RequestParam(required = false) final boolean errorEmail) {
        var userApp = userAppService.getById(id);
        Util.extractRole(model, userApp);
        var company = userApp.getCompany();
        log.info("Profile company: " + company.getEmail());

        if (company.getDescription() == null) {
            company.setDescription("");
        }

        model.addAttribute("errorEmail", errorEmail);
        model.addAttribute("company", company);
        model.addAttribute("userApp", userApp);
        return "profile";
    }

    @PostMapping("/edit/{user_id}/{id}")
    public String postUpdateCustomer(@ModelAttribute("company") Company company, @PathVariable("id") String id,
                                     @PathVariable("user_id") String userID) {
        var newCompany = companyService.getById(id);
        log.info("Try to edit profile of company: " + newCompany.getCompanyName());

        if (companyService.existsByEmailAndDifferentId(company.getEmail(), id)) {
            return "redirect:/profile/" + userID + "?errorEmail=true";
        } else {
            company.setId(id);
            company.setUniqueCode(newCompany.getUniqueCode());
            companyService.saveAdmin(company);
        }

        return "redirect:/profile/" + userID;
    }

    @GetMapping("/user-profile/{id}")
    String getSettingsPage(Model model, @RequestParam(required = false) final boolean errorEmail,
                           @RequestParam(required = false) final boolean errorPassword,
                           @RequestParam(required = false) final boolean errorSamePassword,
                           @RequestParam(required = false) final boolean errorNewPassword,
                           @PathVariable("id") String id) {
        var userApp = userAppService.getById(id);
        Util.extractRole(model, userApp);

        model.addAttribute("errorEmail", errorEmail);
        model.addAttribute("errorPassword", errorPassword);
        model.addAttribute("errorSamePassword", errorSamePassword);
        model.addAttribute("errorNewPassword", errorNewPassword);
        model.addAttribute("userApp", userApp);

        return "user-profile";
    }

    @PostMapping("/edit-profile/{id}")
    String editUserProfile(@ModelAttribute("userCompany") UserApp user,
                           @PathVariable("id") String id) {
        var userCompany = userAppService.getById(id);
        log.info("Try to edit profile of user: " + userCompany.getEmail());

        if (userAppService.existsByEmailAndDifferentId(user.getEmail(), id)) {
            return "redirect:/user-profile/" + id + "?errorEmail=true";
        } else {
            user.setId(id);
            user.setRole(userCompany.getRole());
            user.setPassword(userCompany.getPassword());
            user.setCompany(userCompany.getCompany());
            userAppService.saveUser(user);
        }

        return "redirect:/user-profile/" + id;
    }

    @PostMapping("/change-password/{id}")
    String getChangePassword(@ModelAttribute("password") String password,
                             @ModelAttribute("newPassword") String newPassword,
                             @ModelAttribute("confirmPassword") String confirmPassword,
                             @PathVariable("id") String id) {
        var userApp = userAppService.getById(id);
        log.info("Try to change password of user: " + userApp.getEmail());

        if (!passwordEncoder.matches(password, userApp.getPassword())) {
            return "redirect:/user-profile/" + id + "?errorPassword=true";
        }
        if (passwordEncoder.matches(newPassword, userApp.getPassword())) {
            return "redirect:/user-profile/" + id + "?errorSamePassword=true";
        }
        if (!confirmPassword.equals(newPassword)) {
            return "redirect:/user-profile/" + id + "?errorNewPassword=true";
        } else {
            userApp.setPassword(passwordEncoder.encode(confirmPassword));
            userAppService.saveUser(userApp);
        }

        return "redirect:/user-profile/" + id;
    }
}