package com.example.jobs.controller;

import com.example.jobs.entity.Employer;
import com.example.jobs.service.EmployerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class RegisterEmployerController {

    final EmployerService employerService;

    @GetMapping("/register-employer")
    public String getRegisterEmployerPage(@RequestParam(required = false) final boolean error, Model model) {
        model.addAttribute("error", error);
        return "register-employer";
    }

    @PostMapping("/register-employer/submit")
    public String submitRegisterEmployer(@ModelAttribute("employer") Employer employer) {

        List<Employer> employerByUniqueCode = employerService.getEmployerByUniqueCode(employer.getUniqueCode());
        if (employerByUniqueCode != null && !employerByUniqueCode.isEmpty()) {
            return "redirect:/register-employer?error=true";
        } else {
            employerService.saveAdmin(employer);
            return "redirect:/login";
        }
    }
}
