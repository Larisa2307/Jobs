package com.example.jobs.controller;

import com.example.jobs.entity.Page;
import com.example.jobs.entity.UserCompany;
import com.example.jobs.service.CompanyService;
import com.example.jobs.service.MailSenderService;
import com.example.jobs.service.NavbarService;
import com.example.jobs.service.UserCompanyService;
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
    final MailSenderService mailSenderService;
    final UserCompanyService userCompanyService;

    static final List<String> roles = List.of("Admin", "Operator", "Decider");

    @GetMapping("/users-company/{id}")
    String getUsersCompanyPage(Model model, @PathVariable String id, @RequestParam(required = false) final boolean errorEmail) {
        var userCompany = userCompanyService.getById(id);
        var company = userCompany.getCompany();
        log.info("Users company: " + company.getCompanyName());
        var usersCompany = userCompanyService.getUserByCompany(company);

        model.addAttribute("userCompany", userCompany);
        model.addAttribute("usersCompany", usersCompany);
        model.addAttribute("errorEmail", errorEmail);
        model.addAttribute("roles", roles);

        navbarService.activateNavbarTab(Page.USERS_COMPANY, model);
        return "users-company";
    }

    @PostMapping("/add-user/{user_id}")
    public String addJob(@ModelAttribute("user") UserCompany user, @PathVariable("user_id") final String userId) {
        var userCompany = userCompanyService.getById(userId);
        var company = userCompany.getCompany();
        log.info("Try to add an user to: " + company.getEmail());

        if (!userCompanyService.getUsersByEmail(user.getEmail()).isEmpty()) {
            return "redirect:/users-company/" + userId + "?errorEmail=true";
        } else {
            user.setCompany(company);
            String password = RandomStringUtils.random(6, true, false);
            mailSenderService.sendMailResetPassword(user.getEmail(), password);
            user.setPassword(passwordEncoder.encode(password));
            user.setPhone("");
            userCompanyService.saveUserCompany(user);
        }

        return "redirect:/users-company/" + userId;
    }

    @PostMapping("/edit-user/{user_id}/{id}")
    public String editCategory(@ModelAttribute("user") UserCompany user, @PathVariable("id") final String id,
                               @PathVariable("user_id") final String userId) {
        var userCompany = userCompanyService.getById(id);
        var company = userCompany.getCompany();
        log.info("Try to edit a job: " + userCompany.getFirstName() + " " + userCompany.getLastName()
                + " company: " + company.getCompanyName());
        if (userCompanyService.existsByEmailAndDifferentId(user.getEmail(), id)) {
            return "redirect:/users-company/" + userId + "?errorEmail=true";
        } else {
            user.setId(id);
            user.setCompany(company);
            user.setPhone(userCompany.getPhone());
            user.setPassword(userCompany.getPassword());
            userCompanyService.saveUserCompany(user);
        }
        return "redirect:/users-company/" + userId;
    }

    @PostMapping("/delete-user/{user_id}/{id}")
    public String deleteCategory(@PathVariable String id, @PathVariable("user_id") final String userId) {
        log.info("Try to delete an announcement");
        userCompanyService.deleteUserCompany(userCompanyService.getById(id));

        return "redirect:/users-company/" + userId;
    }
}
