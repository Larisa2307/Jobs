package com.example.jobs.controller;

import com.example.jobs.entity.UserApp;
import com.example.jobs.service.UserAppService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class RegisterController {

    final UserAppService userAppService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register-candidate")
    public String getRegisterEmployeePage(@RequestParam(required = false) final boolean errorEmail,
                                          @RequestParam(required = false) final boolean errorPassword, Model model) {
        model.addAttribute("errorEmail", errorEmail);
        model.addAttribute("errorPassword", errorPassword);
        return "register-candidate";
    }

    @PostMapping("/register-candidate/submit")
    public String submitRegisterEmployee(@ModelAttribute("userApp") UserApp userApp,
                                         @ModelAttribute("confirmPassword") String confirmPassword) {
        log.info("Try to register user: " + userApp.getEmail());
        if ("".equals(userApp.getPhone())) {
            userApp.setPhone(null);
        }

        List<UserApp> usersByEmail = userAppService.getUsersByEmail(userApp.getEmail());
        if (usersByEmail != null && !usersByEmail.isEmpty()) {
            return "redirect:/register-candidate?errorEmail=true";
        } else if (!confirmPassword.equals(userApp.getPassword())) {
            return "redirect:/register-candidate?errorPassword=true";
        } else {
            userApp.setPassword(passwordEncoder.encode(userApp.getPassword()));
            userAppService.saveAdmin(userApp);
            return "redirect:/login";
        }
    }

}
