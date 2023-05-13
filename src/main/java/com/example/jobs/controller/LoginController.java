package com.example.jobs.controller;

import com.example.jobs.service.CompanyService;
import com.example.jobs.service.UserAppService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@Slf4j
public class LoginController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    final UserAppService userAppService;
    final CompanyService companyService;

    @GetMapping("/login")
    String getLoginPage(@RequestParam(required = false) final boolean error, Model model) {
        model.addAttribute("error", error);
        return "login";
    }

    @PostMapping("/login/submit")
    String submitLogin(@RequestParam String email, @RequestParam String password) {
        log.info("Try to login with email: " + email);

        var userApp = userAppService.getUserAppByEmail(email);

        if (userApp.isPresent() && passwordEncoder.matches(password, userApp.get().getPassword())
                && "User".equals(userApp.get().getRole())) {
            return "redirect:/dashboard/" + userApp.get().getId();
        } else if (userApp.isPresent() && passwordEncoder.matches(password, userApp.get().getPassword())) {
            return "redirect:/dashboard-company/" + userApp.get().getId();
        } else {
            return "redirect:/login?error=true";
        }
    }

}
