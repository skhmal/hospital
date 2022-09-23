package com.khmal.hospital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping("/")
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/main")
    public String getMain(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        return "main";
    }

    @GetMapping("/successful")
    public String getSuccessful() {
        return "successful";
    }

    @GetMapping("/login")
    public String getLog(){
        return "login";
    }

    @GetMapping("/logout")
    public String getLogout(){
        return "redirect:/";
    }
}
