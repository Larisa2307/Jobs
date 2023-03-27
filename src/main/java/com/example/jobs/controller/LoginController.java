package com.example.jobs.controller;

import com.example.jobs.service.EmployerService;
import com.example.jobs.service.UserAppService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@Slf4j
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
        log.info("Try to login with email: " + email);
        var userApp = userAppService.getUserAppByCredentials(email, password);
        var employer = employerService.getEmployerByCredentials(email, password);

        if (userApp.isPresent()) {
            return "redirect:/dashboard";
        } else
            return employer.map(value -> "redirect:/dashboard-employer/" + value.getId())
                    .orElse("redirect:/login?error=true");
    }

}
