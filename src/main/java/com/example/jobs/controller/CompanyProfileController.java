package com.example.jobs.controller;

import com.example.jobs.entity.Employer;
import com.example.jobs.service.EmployerService;
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
    final EmployerService employerService;

    @GetMapping("/profile/{id}")
    String getProfileIdPage(Model model, @PathVariable String id) {
        var employer = employerService.getbyId(id);
        log.info("Profile employer: " + employer.getEmail());

        if (employer.getDescription() == null) {
            employer.setDescription("");
        }

        model.addAttribute("employer", employer);
        return "profile";
    }

    @GetMapping("/edit/{id}")
    public String getUpdateCustomer(final Model model, @PathVariable("id") final String id) throws EntityNotFoundException {
        var company = employerService.getbyId(id);
        model.addAttribute("employer", company);
        return "redirect:/profile/" + id;
    }

    @PostMapping("/edit/{id}")
    public String postUpdateCustomer(@ModelAttribute("employer") Employer employer, @PathVariable("id") String id) {
        var company = employerService.getbyId(id);
        log.info("Try to edit profile of employer: " + company.getEmail());

        employer.setId(id);
        employer.setPassword(company.getPassword());
        employer.setUniqueCode(company.getUniqueCode());
        employer.setUsername(company.getUsername());
        employerService.saveAdmin(employer);

        return "redirect:/profile/" + id;
    }

}
