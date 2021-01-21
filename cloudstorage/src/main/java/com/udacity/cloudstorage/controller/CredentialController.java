package com.udacity.cloudstorage.controller;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
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
        var errors = new ArrayList<String>();

        if (data.get("url").isEmpty())
            errors.add("URL must not be empty.");

        if (data.get("username").isEmpty())
            errors.add("Username must not be empty.");

        if (data.get("password").isEmpty())
            errors.add("Password must not be empty.");

        return errors;
    }

    /**
     * It receives a credential ID, retrieve the credential from the storage, decrypts it, and returns it decrypted.
     *
     * @param credentialId The credential ID.
     * @param authentication It allows the application to check the credential ownership based on the UID in the session.
     * @return A decrypted credential for the caller.
     */
    @ResponseBody
    @GetMapping("{credentialId}")
    public ResponseEntity<String> decryptCredential(@PathVariable Integer credentialId, Authentication authentication) {
        try {
            var UID = users.getUser(authentication.getName()).getUserId();
            return new ResponseEntity<>(
                credentials.decryptCredential(new Credential(credentialId, UID)),
                HttpStatus.OK
            );

        } catch (Exception ignored) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * It creates a new credential on the storage.
     *
     * @param response It allows the customization of the Http status code on the reply.
     * @param authentication It allows the application to check the credential ownership based on the UID in the session.
     * @param data The form sent from the caller with the credentials data.
     * @param model The object returned to the HTML template.
     * @return The name of the template that will process the reply.
     */
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

    /**
     * It edits a credential data.
     *
     * @param response It allows the customization of the Http status code on the reply.
     * @param authentication It allows the application to check the credential ownership based on the UID in the session.
     * @param data The form sent from the caller with the credentials data.
     * @param model The object returned to the HTML template.
     * @return The name of the template that will process the reply.
     */
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

    /**
     * It removes a credential from the storage.
     *
     * @param response It allows the customization of the Http status code on the reply.
     * @param credentialId The ID of the credential being removed.
     * @param authentication It allows the application to check the credential ownership based on the UID in the session.
     * @param model The object returned to the HTML template.
     * @return The name of the template that will process the reply.
     */
    @DeleteMapping(value = "{credentialId}")
    public String removeView(
        HttpServletResponse response,
        @PathVariable Integer credentialId,
        Authentication authentication,
        Model model
    ) {
        var errors = new ArrayList<String>();
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
