package com.udacity.cloudstorage.controller;

import java.util.ArrayList;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import com.udacity.cloudstorage.service.UserService;
import com.udacity.cloudstorage.service.NoteService;
import org.springframework.security.core.Authentication;
import com.udacity.cloudstorage.service.CredentialService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final UserService users;
    private final NoteService notes;
    private final CredentialService credentials;

    public HomeController(
        UserService users,
        NoteService notes,
        CredentialService credentials
    ) {
        this.users = users;
        this.notes = notes;
        this.credentials = credentials;
    }

    @GetMapping()
    public String homeView(Authentication authentication, Model model) {

        try {
            var UID = users.getUser(
                authentication.getName()
            ).getUserId()
             .toString();

            model.addAttribute("notes", notes.allBy(UID));
            model.addAttribute("files", new ArrayList<>());
            model.addAttribute("credentials", credentials.allBy(UID));

        } catch (Exception ignored) {
            return "redirect:/logout-success";
        }

        return "home";
    }

}
