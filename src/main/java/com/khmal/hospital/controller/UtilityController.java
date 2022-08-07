package com.khmal.hospital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UtilityController {

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
}
