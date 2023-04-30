package com.example.jobs.controller;

import com.example.jobs.entity.Company;
import com.example.jobs.entity.UserCompany;
import com.example.jobs.service.CompanyService;
import com.example.jobs.service.UserCompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class RegisterEmployerController {

    final CompanyService companyService;
    final UserCompanyService userCompanyService;

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
                                         @ModelAttribute("userCompany") UserCompany userCompany,
                                         @ModelAttribute("confirmPassword") String confirmPassword) {
        log.info("Try to register user: " + userCompany.getEmail());
        if ("".equals(userCompany.getPhone())) {
            userCompany.setPhone(null);
        }

        List<UserCompany> usersByEmail = userCompanyService.getUsersByEmail(userCompany.getEmail());
        if (usersByEmail != null && !usersByEmail.isEmpty()) {
            return "redirect:/register?errorEmail=true";
        } else if (!confirmPassword.equals(userCompany.getPassword())) {
            return "redirect:/register?errorPassword=true";
        } else {
            var company = companyService.getById(id);
            userCompany.setCompany(company);
            userCompany.setRole("ADMIN");
            userCompanyService.saveUserCompany(userCompany);
            return "redirect:/login";
        }
    }

    @GetMapping("/register-company")
    public String getRegisterEmployerPage(@RequestParam(required = false) final boolean error, Model model) {
        model.addAttribute("error", error);
        return "register-company";
    }

    @PostMapping("/register-company/submit")
    public String submitRegisterEmployer(@ModelAttribute("company") Company company) {
        log.info("Try to register employer: " + company.getEmail());
        List<Company> companyByUniqueCode = companyService.getEmployerByUniqueCode(company.getUniqueCode());
        if (companyByUniqueCode != null && !companyByUniqueCode.isEmpty()) {
            return "redirect:/register-company?error=true";
        } else {
            companyService.saveAdmin(company);
            return "redirect:/register/" + company.getId();
        }
    }
}
