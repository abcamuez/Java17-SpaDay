package org.launchcode.controllers;

import jakarta.validation.Valid;
import org.launchcode.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @GetMapping("/add")
    public String displayAddUserForm() {
        return "user/add";
    }

    @PostMapping
    public String processAddUserForm(Model model, @Valid @ModelAttribute User user, BindingResult bindingResult, String verify) {
        model.addAttribute("user", user);
        model.addAttribute("verify", verify);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessages.add(error.getDefaultMessage()); // Use getDefaultMessage to get the error message
            }
            model.addAttribute("errors", errorMessages);
            return "user/add"; // Return to the form to display validation errors
        }

        else if (user.getPassword().equals(verify)) {
            return "user/index"; // Passwords match, proceed to index
        } else {
            model.addAttribute("error", "Passwords do not match");
            return "user/add"; // Return to the form with an error message
        }
    }


}