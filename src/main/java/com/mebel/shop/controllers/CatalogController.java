package com.mebel.shop.controllers;

import com.mebel.shop.models.Item;
import com.mebel.shop.models.User;
import com.mebel.shop.repository.CatalogRepository;
import com.mebel.shop.repository.UserRepository;
import com.mebel.shop.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class CatalogController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private CatalogService catalogService;

    @GetMapping("/catalog")
    public String CatalogPage(@AuthenticationPrincipal User user, HttpServletResponse response, Model model){
        List<Item> nado = catalogService.getAllItems();
        model.addAttribute("allItems", nado);
        model.addAttribute("user",user);
        return "catalog";
    }
}
