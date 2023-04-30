package com.example.jobs.controller;

import com.example.jobs.entity.Company;
import com.example.jobs.service.CompanyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
@Slf4j
public class CompanyProfileController {

    final CompanyService companyService;

    @GetMapping("/profile/{id}")
    String getProfileIdPage(Model model, @PathVariable String id) {
        var company = companyService.getById(id);
        log.info("Profile company: " + company.getEmail());

        if (company.getDescription() == null) {
            company.setDescription("");
        }

        model.addAttribute("company", company);
        return "profile";
    }

    @GetMapping("/edit/{id}")
    public String getUpdateCustomer(final Model model, @PathVariable("id") final String id) throws EntityNotFoundException {
        var company = companyService.getById(id);
        model.addAttribute("company", company);
        return "redirect:/profile/" + id;
    }

    @PostMapping("/edit/{id}")
    public String postUpdateCustomer(@ModelAttribute("company") Company company, @PathVariable("id") String id) {
        var newCompany = companyService.getById(id);
        log.info("Try to edit profile of company: " + newCompany.getEmail());

        company.setId(id);
        company.setUniqueCode(newCompany.getUniqueCode());
        companyService.saveAdmin(company);

        return "redirect:/profile/" + id;
    }

}
