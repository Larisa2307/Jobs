package com.example.jobs.util;

import com.example.jobs.entity.UserApp;
import org.springframework.ui.Model;

public class Util {
    public static boolean isUserCompany(UserApp userApp) {
        return "Admin".equals(userApp.getRole()) || "Operator".equals(userApp.getRole()) ||"Decider".equals(userApp.getRole());
    }

    public static boolean isAdmin(UserApp userApp) {
        return "Admin".equals(userApp.getRole());
    }

    public static boolean isOperator(UserApp userApp) {
        return "Operator".equals(userApp.getRole());
    }

    public static boolean isDecider(UserApp userApp) {
        return "Decider".equals(userApp.getRole());
    }

    public static void extractRole(Model model, UserApp userApp) {
        var isUserCompany = isUserCompany(userApp);
        var isAdmin = isAdmin(userApp);
        var isOperator = isOperator(userApp);
        var isDecider = isDecider(userApp);

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isOperator", isOperator);
        model.addAttribute("isDecider", isDecider);
        model.addAttribute("isUserCompany", isUserCompany);
    }
}
