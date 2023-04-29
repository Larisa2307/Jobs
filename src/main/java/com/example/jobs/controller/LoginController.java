package com.example.jobs.controller;

import com.example.jobs.service.CompanyService;
import com.example.jobs.service.UserAppService;
import com.example.jobs.service.UserCompanyService;
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
    final UserCompanyService userCompanyService;
    final CompanyService companyService;


    @GetMapping("/login")
    String getLoginPage(@RequestParam(required = false) final boolean error, Model model) {
        model.addAttribute("error", error);
        return "login";
    }

    @PostMapping("/login/submit")
    String submitLogin(@RequestParam String email, @RequestParam String password) {
        log.info("Try to login with email: " + email);
        var userApp = userAppService.getUserAppByCredentials(email, password);
        var userCompany = userCompanyService.getUserCompanyByCredentials(email, password);

        if (userApp.isPresent()) {
            return "redirect:/dashboard";
        } else
            return userCompany.map(value -> "redirect:/dashboard-company/" + value.getCompany().getId())
                    .orElse("redirect:/login?error=true");
    }

}
