package com.udacity.cloudstorage.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SessionController {

    @GetMapping("/logout-success")
    public String logoutView(Model model) {
        model.addAttribute("logoutSuccess", true);

        return "login";
    }

}
