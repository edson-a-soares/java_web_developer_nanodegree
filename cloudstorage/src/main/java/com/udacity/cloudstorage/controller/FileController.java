package com.udacity.cloudstorage.controller;

import java.util.List;
import java.util.ArrayList;
import org.springframework.ui.Model;
import org.springframework.http.MediaType;
import com.udacity.cloudstorage.model.File;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.udacity.cloudstorage.service.FileService;
import com.udacity.cloudstorage.service.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/files")
public class FileController {

    private final UserService users;
    private final FileService files;

    public FileController(UserService userService, FileService fileService) {
        this.users = userService;
        this.files = fileService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadView(
        HttpServletResponse response,
        Authentication authentication,
        @RequestParam(name = "file") MultipartFile multipartFile,
        Model model
    ) {
        List<String> errors = new ArrayList<String>();

        if (multipartFile.isEmpty())
            errors.add("File field must not be empty.");

        model.addAttribute("success", true);
        var UID = users.getUser(authentication.getName()).getUserId();
        try {
            var file = new File(
                multipartFile.getOriginalFilename(),
                multipartFile.getSize(),
                multipartFile.getContentType(),
                multipartFile.getBytes(),
                UID
            );

            if (files.exists(file)) {
                errors.add("File already stored.");
                model.addAttribute("errors", errors);
                model.addAttribute("success", false);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return "result";
            }

            files.store(file);

        } catch (Exception ignored) {
            errors.add("Something bad happened.");
            model.addAttribute("success", false);
            model.addAttribute("errors", errors);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("success", false);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return "result";
    }

    @GetMapping(value = "/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadView(
        HttpServletResponse response,
        Authentication authentication,
        @PathVariable Integer fileId
    ) {
        var UID = users.getUser(authentication.getName()).getUserId();
        var file   = files.get(new File(fileId, UID));
        if (file != null) {
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header("Content-Disposition", "attachment; filename=" + file.getName())
                .body(new ByteArrayResource(file.getData()));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{fileId}")
    public String removeView(
        Authentication authentication,
        HttpServletResponse response,
        @PathVariable Integer fileId,
        Model model
    ) {
        var UID = users.getUser(authentication.getName()).getUserId();
        List<String> errors = new ArrayList<String>();
        model.addAttribute("success", true);
        try {
            files.remove(new File(fileId, UID));

        } catch (Exception ignore) {
            errors.add("There was a server error. The file was not removed.");
            model.addAttribute("errors", errors);
            model.addAttribute("success", false);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return "result";
    }

}
