package com.yousha.web;

import com.yousha.web.forms.LoginForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class WebsiteController {

    @GetMapping("/")
    public String homePage(Model model, @RequestParam(name = "username", required = false, defaultValue = "stranger") String username){
        model.addAttribute("username", username);
        model.addAttribute("currentDate", LocalDateTime.now());
        return "index.html";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("loginForm", new LoginForm());
        return "login.html";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute @Valid LoginForm loginForm, BindingResult bindingResult, Model model){
        // note: for error handling of login form,
        // must add BindingResult parameter directly after your LoginForm in method argument
        // otherwise it won't work.
        if (bindingResult.hasErrors()) {
            return "login.html";
        }
        if (loginForm.getUsername().equals(loginForm.getPassword())) {
            return "redirect:/";
        }
        model.addAttribute("invalidCredentials", "true");
        return "login.html";
    }
}
