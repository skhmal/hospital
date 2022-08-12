package com.khmal.hospital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping("/")
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/main")
    public String getMain() {
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

    @GetMapping("/error")
    public String getError(Model model){
        model.getAttribute("message1");
        return "error";
    }
}
