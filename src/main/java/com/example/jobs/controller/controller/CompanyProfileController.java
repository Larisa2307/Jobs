package com.example.jobs.controller.controller;

import com.example.jobs.entity.Employer;
import com.example.jobs.service.EmployerService;
import com.sun.net.httpserver.HttpContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.logging.Logger;

@RequiredArgsConstructor
@Controller
//@Slf4j
public class CompanyProfileController {
    final EmployerService employerService;

    @GetMapping("/profile/{id}")
    String getProfileIdPage(Model model, @PathVariable String id) {
        var employer = employerService.getbyId(id);

        model.addAttribute("employer", employer);
        return "profile";
    }

}
