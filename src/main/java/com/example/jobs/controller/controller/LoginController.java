package com.example.jobs.controller.controller;

import com.example.jobs.service.EmployerService;
import com.example.jobs.service.UserAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class LoginController {

    final UserAppService userAppService;
    final EmployerService employerService;

    @GetMapping("/")
    String getStartPage() {
        return "redirect:/login";
    }
    @GetMapping("/login")
    String getLoginPage(@RequestParam(required = false) final boolean error, Model model) {
        model.addAttribute("error", error);
        return "login";
    }

    @PostMapping("/login/submit")
    String submitLogin(@RequestParam String email, @RequestParam String password) {
        var userApp = userAppService.getUserAppByCredentials(email, password);
        var employer = employerService.getEmployerByCredentials(email, password);

        if (userApp.isPresent()) {
            return "redirect:/dashboard";
        } else if (employer.isPresent()){
            return "redirect:/profile/"+employer.get().getId();
        } else {
            return "redirect:/login?error=true";
        }
    }

}
