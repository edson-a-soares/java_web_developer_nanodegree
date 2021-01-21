package com.udacity.cloudstorage.controller;

import org.springframework.ui.Model;
import com.udacity.cloudstorage.model.User;
import org.springframework.stereotype.Controller;
import com.udacity.cloudstorage.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signupView() {
        return "signup";
    }

    @PostMapping()
    public String signupUser(@ModelAttribute User user, Model model) {
        String signupError = null;
        if (!userService.isUsernameAvailable(user.getUsername()))
            signupError = "The username '" + user.getUsername() + "' already exists.";

        if (signupError == null) {
            int rowsAdded = userService.createUser(user);

            if (rowsAdded < 0)
                signupError = "There was an error signing you up. Please try again.";

        }

        if (signupError != null) {
            model.addAttribute("signupError", signupError);

            return "signup";
        }

        model.addAttribute("signupSuccess", true);
        return "login";
    }

}
