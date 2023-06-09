package com.example.jobs.controller;

import com.example.jobs.entity.Page;
import com.example.jobs.entity.UserApp;
import com.example.jobs.service.CompanyService;
import com.example.jobs.service.MailSenderService;
import com.example.jobs.service.NavbarService;
import com.example.jobs.service.UserAppService;
import com.example.jobs.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class UserCompanyController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    final NavbarService navbarService;
    final CompanyService companyService;
    final UserAppService userAppService;
    final MailSenderService mailSenderService;

    static final List<String> roles = List.of("Admin", "Operator", "Decider");

    @GetMapping("/users-company/{id}")
    String getUsersCompanyPage(Model model, @PathVariable String id, @RequestParam(required = false) final boolean errorEmail) {
        var userApp = userAppService.getById(id);
        Util.extractRole(model, userApp);
        var company = userApp.getCompany();
        log.info("Users company: " + company.getCompanyName());
        var usersCompany = userAppService.getUserByCompany(company);

        model.addAttribute("userApp", userApp);
        model.addAttribute("usersCompany", usersCompany);
        model.addAttribute("errorEmail", errorEmail);
        model.addAttribute("roles", roles);

        navbarService.activateNavbarTab(Page.USERS_COMPANY, model);
        return "users-company";
    }

    @PostMapping("/add-user/{user_id}")
    public String addJob(@ModelAttribute("user") UserApp user, @PathVariable("user_id") final String userId) {
        var userCompany = userAppService.getById(userId);
        var company = userCompany.getCompany();
        log.info("Try to add an user to: " + company.getEmail());

        if (!userAppService.getUsersByEmail(user.getEmail()).isEmpty()) {
            return "redirect:/users-company/" + userId + "?errorEmail=true";
        } else {
            user.setCompany(company);
            String password = RandomStringUtils.random(6, true, false);
            mailSenderService.sendMailResetPassword(user.getEmail(), password);
            user.setPassword(passwordEncoder.encode(password));
            user.setPhone("");
            userAppService.saveUser(user);
        }

        return "redirect:/users-company/" + userId;
    }

    @PostMapping("/edit-user/{user_id}/{id}")
    public String editUser(@ModelAttribute("user") UserApp user, @PathVariable("id") final String id,
                           @PathVariable("user_id") final String userId) {
        var userCompany = userAppService.getById(id);
        var company = userCompany.getCompany();
        log.info("Try to edit a job: " + userCompany.getFirstName() + " " + userCompany.getLastName()
                + " company: " + company.getCompanyName());
        if (userAppService.existsByEmailAndDifferentId(user.getEmail(), id)) {
            return "redirect:/users-company/" + userId + "?errorEmail=true";
        } else {
            user.setId(id);
            user.setCompany(company);
            user.setPhone(userCompany.getPhone());
            user.setPassword(userCompany.getPassword());
            userAppService.saveUser(user);
        }
        return "redirect:/users-company/" + userId;
    }

    @PostMapping("/delete-user/{user_id}/{id}")
    public String deletUser(@PathVariable String id, @PathVariable("user_id") final String userId) {
        log.info("Try to delete an announcement");
        userAppService.deleteUser(userAppService.getById(id));

        return "redirect:/users-company/" + userId;
    }
}
