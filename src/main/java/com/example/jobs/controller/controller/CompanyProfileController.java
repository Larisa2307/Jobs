package com.example.jobs.controller.controller;

import com.example.jobs.service.EmployerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
//@Slf4j
public class CompanyProfileController {
    final EmployerService employerService;

    @GetMapping("/profile/{id}")
    String getProfileIdPage(Model model, @PathVariable String id) {
        var employer = employerService.getbyId(id);

        if (employer.getDescription() == null) {
            employer.setDescription("");
        }

        model.addAttribute("employer", employer);
        return "profile";
    }

}
