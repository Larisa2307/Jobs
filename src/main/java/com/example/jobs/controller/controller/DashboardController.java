package com.example.jobs.controller.controller;

import com.example.jobs.config.LoginSecurityConfig;
import com.example.jobs.entity.Employer;
import com.example.jobs.entity.Page;
import com.example.jobs.entity.UserApp;
import com.example.jobs.service.EmployerService;
import com.example.jobs.service.NavbarService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class DashboardController<IAuthenticationFacade> {

    final NavbarService navbarService;
    final EmployerService employerService;

    private IAuthenticationFacade authenticationFacade;

    @GetMapping("/dashboard")
    String getDashboardPage(Model model) {
        navbarService.activateNavbarTab(Page.DASHBOARD, model);

        return "dashboard";
    }



    @GetMapping("/dashboard-employer/{id}")
    String getDashboardEmployerPage(Model model, @PathVariable String id) {
        var employer = employerService.getbyId(id);

        model.addAttribute("employer", employer);
        navbarService.activateNavbarTab(Page.DASHBOARDEMPLOYER, model);
        return "dashboard-employer";
    }


//    @PostMapping("/dashboard-employer/{id}")
//    String postDashboardEmployerPage(@ModelAttribute("employer") Employer employer) {
//
//        return "user-management";
//    }
}
