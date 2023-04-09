package com.mebel.shop.controllers;

import com.mebel.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/")
    public String homePage(Model model){
        return "Homepage";
    }
}
