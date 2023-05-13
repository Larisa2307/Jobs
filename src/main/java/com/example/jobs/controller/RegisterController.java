package com.example.jobs.controller;

import com.example.jobs.entity.Company;
import com.example.jobs.entity.UserApp;
import com.example.jobs.service.CompanyService;
import com.example.jobs.service.UserAppService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class RegisterController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    final CompanyService companyService;
    final UserAppService userAppService;

    @GetMapping("/register/{id}")
    public String getRegisterEmployeePage(@PathVariable String id,
                                          @RequestParam(required = false) final boolean errorEmail,
                                          @RequestParam(required = false) final boolean errorPassword, Model model) {
        model.addAttribute("errorEmail", errorEmail);
        model.addAttribute("errorPassword", errorPassword);
        model.addAttribute("id", id);
        return "register";
    }

    @PostMapping("/register/{id}/submit")
    public String submitRegisterEmployee(@PathVariable String id,
                                         @ModelAttribute("userCompany") UserApp userCompany,
                                         @ModelAttribute("confirmPassword") String confirmPassword) {
        log.info("Try to register user: " + userCompany.getEmail());
        if ("".equals(userCompany.getPhone())) {
            userCompany.setPhone(null);
        }

        List<UserApp> usersByEmail = userAppService.getUsersByEmail(userCompany.getEmail());
        if (usersByEmail != null && !usersByEmail.isEmpty()) {
            return "redirect:/register?errorEmail=true";
        } else if (!confirmPassword.equals(userCompany.getPassword())) {
            return "redirect:/register?errorPassword=true";
        } else {
            var company = companyService.getById(id);
            userCompany.setCompany(company);
            userCompany.setRole("Admin");
            userCompany.setPassword(passwordEncoder.encode(userCompany.getPassword()));
            userAppService.saveUser(userCompany);
            return "redirect:/login";
        }
    }

    @GetMapping("/register-company")
    public String getRegisterEmployerPage(@RequestParam(required = false) final boolean error,
                                          @RequestParam(required = false) final boolean errorEmail,
                                          Model model) {
        model.addAttribute("error", error);
        model.addAttribute("errorEmail", errorEmail);
        return "register-company";
    }

    @PostMapping("/register-company/submit")
    public String submitRegisterEmployer(@ModelAttribute("company") Company company) {
        log.info("Try to register employer: " + company.getEmail());
        List<Company> companyByUniqueCode = companyService.getEmployerByUniqueCode(company.getUniqueCode());

        List<Company> companies = companyService.getCompanyByEmail(company.getEmail());
        if (companies != null && !companies.isEmpty()) {
            return "redirect:/register-company?errorEmail=true";
        } else if (companyByUniqueCode != null && !companyByUniqueCode.isEmpty()) {
            return "redirect:/register-company?error=true";
        } else {
            companyService.saveAdmin(company);
            return "redirect:/register/" + company.getId();
        }
    }

    @GetMapping("/register-candidate")
    public String getRegisterEmployeePage(@RequestParam(required = false) final boolean errorEmail,
                                          @RequestParam(required = false) final boolean errorPassword, Model model) {
        model.addAttribute("errorEmail", errorEmail);
        model.addAttribute("errorPassword", errorPassword);
        return "register-candidate";
    }

    @PostMapping("/register-candidate/submit")
    public String submitRegisterEmployee(@ModelAttribute("userApp") UserApp userApp,
                                         @ModelAttribute("confirmPassword") String confirmPassword) {
        log.info("Try to register user: " + userApp.getEmail());
        if ("".equals(userApp.getPhone())) {
            userApp.setPhone(null);
        }

        List<UserApp> usersByEmail = userAppService.getUsersByEmail(userApp.getEmail());
        if (usersByEmail != null && !usersByEmail.isEmpty()) {
            return "redirect:/register-candidate?errorEmail=true";
        } else if (!confirmPassword.equals(userApp.getPassword())) {
            return "redirect:/register-candidate?errorPassword=true";
        } else {
            userApp.setPassword(passwordEncoder.encode(userApp.getPassword()));
            userApp.setRole("User");
            userAppService.saveUser(userApp);
            return "redirect:/login";
        }
    }
}
