package com.udacity.cloudstorage.controller;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.apache.tomcat.util.buf.UDecoder;
import org.springframework.ui.Model;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import com.udacity.cloudstorage.model.Credential;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.udacity.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import com.udacity.cloudstorage.service.CredentialService;

@Controller
@RequestMapping("/credentials")
public class CredentialController {

    private final UserService users;
    private final CredentialService credentials;

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.users          = userService;
        this.credentials    = credentialService;
    }

    public List<String> validate(Map<String, String> data) {
        List<String> errors = new ArrayList<String>();

        if (data.get("url").isEmpty())
            errors.add("URL must not be empty.");

        if (data.get("username").isEmpty())
            errors.add("Username must not be empty.");

        if (data.get("password").isEmpty())
            errors.add("Password must not be empty.");

        return errors;
    }

    @ResponseBody
    @GetMapping("{credentialId}")
    public ResponseEntity<String> decryptCredential(@PathVariable Integer credentialId, Authentication authentication) {
        try {
            var UID = users.getUser(authentication.getName()).getUserId();
            return new ResponseEntity<>(credentials.decryptCredential(new Credential(credentialId, UID)), HttpStatus.OK);

        } catch (Exception ignored) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createView(
        HttpServletResponse response,
        Authentication authentication,
        @RequestParam Map<String, String> data,
        Model model
    ) {
        var UID = users.getUser(authentication.getName()).getUserId();
        credentials.add(
            new Credential(
                data.get("url"),
                data.get("username"),
                data.get("password"),
                UID
            )
        );

        var errors = validate(data);
        model.addAttribute("success", true);
        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("success", false);

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return "result";
    }

    @PutMapping(value = "{credentialId}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String editView(
        HttpServletResponse response,
        Authentication authentication,
        @RequestParam Map<String, String> data,
        Model model
    ) {
        var UID = users.getUser(authentication.getName()).getUserId();
        credentials.add(
            new Credential(
                Integer.parseInt(data.get("credentialId")),
                data.get("url"),
                data.get("username"),
                data.get("password"),
                UID
            )
        );

        var errors = validate(data);
        model.addAttribute("success", true);
        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("success", false);

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return "result";
    }

    @DeleteMapping(value = "{credentialId}")
    public String removeView(
        HttpServletResponse response,
        @PathVariable Integer credentialId,
        Authentication authentication,
        Model model
    ) {
        List<String> errors = new ArrayList<String>();
        model.addAttribute("success", true);
        try {
            var UID = users.getUser(authentication.getName()).getUserId();
            credentials.remove(new Credential(credentialId, UID));

        } catch (Exception ignore) {
            errors.add("There was a server error. The credential was not removed.");
            model.addAttribute("errors", errors);
            model.addAttribute("success", false);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return "result";
    }

}
