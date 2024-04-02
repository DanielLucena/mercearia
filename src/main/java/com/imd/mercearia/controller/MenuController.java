package com.imd.mercearia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MenuController {

    @GetMapping("/")
    public String showMenu() {
        return "menu";
    }

}
