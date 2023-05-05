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
            case DASHBOARD -> model.addAttribute("dashboardActive", true);
            case DASHBOARD_COMPANY -> model.addAttribute("dashboard-companyActive", true);
            case JOBS_COMPANY -> model.addAttribute("jobs-companyActive", true);
            case USERS_COMPANY -> model.addAttribute("users-companyActive", true);
            case ANNOUNCEMENT -> model.addAttribute("announcementActive", true);
        }
    }

    public void inactivateAllNavbarTabs(Model model) {
        model.addAttribute("dashboardActive", false);
        model.addAttribute("dashboard-companyActive", false);
        model.addAttribute("jobs-companyActive", false);
        model.addAttribute("users-companyActive", false);
        model.addAttribute("announcementActive", false);

    }
}
