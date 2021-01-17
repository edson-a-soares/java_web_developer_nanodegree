package com.udacity.cloudstorage.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import com.udacity.cloudstorage.service.UserService;
import com.udacity.cloudstorage.service.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final UserService users;
    private final NoteService notes;

    public HomeController(
        UserService users,
        NoteService notes
    ) {
        this.users = users;
        this.notes = notes;
    }

    @GetMapping()
    public String homeView(Authentication authentication, Model model) {

        try {
            var UID = users.getUser(
                    authentication.getName())
                .getUserId()
                .toString();

            model.addAttribute("notes", notes.allBy(UID));
            model.addAttribute("files", new ArrayList<>());
            model.addAttribute("credentials", new ArrayList<>());

        } catch (Exception ignored) {
            return "redirect:/logout-success";

        }

        return "home";
    }

}
