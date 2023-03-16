package com.example.jobs.service;

import com.example.jobs.entity.Page;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@AllArgsConstructor
public class NavbarService {

    public void activateNavbarTab(Page tabName, Model model) {
        inactivateAllNavbarTabs(model);
        switch (tabName) {
            case DASHBOARD:
                model.addAttribute("dashboardActive", true);
                break;
            case DASHBOARDEMPLOYER:
                model.addAttribute("dashboard-employerActive", true);
                break;
            case USER_MANAGEMENT:
                model.addAttribute("userManagementActive", true);
                break;
            case SKILLS:
                model.addAttribute("skillsActive", true);
                break;
            case CATEGORIES:
                model.addAttribute("categoriesActive", true);
                break;
        }
    }

    public void inactivateAllNavbarTabs(Model model) {
        model.addAttribute("dashboardActive", false);
        model.addAttribute("userManagementActive", false);
        model.addAttribute("skillsActive", false);
        model.addAttribute("categoriesActive", false);
    }
}
