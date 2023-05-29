package com.mebel.shop.controllers;

import com.mebel.shop.models.Item;
import com.mebel.shop.models.Stage;
import com.mebel.shop.models.User;
import com.mebel.shop.repository.OrdersRepository;
import com.mebel.shop.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfileController {
    @Autowired
    private CatalogService catalogService;

    @Autowired
    private OrdersRepository ordersRepository;

    @GetMapping("/profile")
    public String usersProfile (@AuthenticationPrincipal User user, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User customUser = (User)authentication.getPrincipal();

        if (catalogService.getAllReq().size() < 1) {
            model.addAttribute("noTickets", true);
            model.addAttribute("user",user);
            return "claim_request";
        }

        Stage first = Stage.OPEN;
        Stage second = Stage.WORKING;

        model.addAttribute("noTickets", false);
        model.addAttribute("allTickets", catalogService.getAllUsersReq(customUser));
        model.addAttribute("user",user);

        return "users_profile";
    }
}
