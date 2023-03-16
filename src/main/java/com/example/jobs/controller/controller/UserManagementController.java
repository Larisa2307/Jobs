package com.example.jobs.controller.controller;

import com.example.jobs.entity.Page;
import com.example.jobs.service.NavbarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class UserManagementController {

    final NavbarService navbarService;

    @GetMapping("/users")
    String getUserManagementPage(Model model) {
        navbarService.activateNavbarTab(Page.USER_MANAGEMENT, model);
        return "user-management";
    }
}
