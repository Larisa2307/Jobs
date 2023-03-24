package com.example.jobs.controller;

import com.example.jobs.entity.Page;
import com.example.jobs.service.EmployerService;
import com.example.jobs.service.NavbarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class DashboardController {

    final NavbarService navbarService;
    final EmployerService employerService;

    @GetMapping("/dashboard")
    String getDashboardPage(Model model) {
        navbarService.activateNavbarTab(Page.DASHBOARD, model);

        return "dashboard";
    }


    @GetMapping("/dashboard-employer/{id}")
    String getDashboardEmployerPage(Model model, @PathVariable String id) {
        var employer = employerService.getbyId(id);

        model.addAttribute("employer", employer);
        navbarService.activateNavbarTab(Page.DASHBOARD_EMPLOYER, model);
        return "dashboard-employer";
    }

}
