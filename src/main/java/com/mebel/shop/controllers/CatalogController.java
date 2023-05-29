package com.mebel.shop.controllers;

import com.mebel.shop.models.*;
import com.mebel.shop.repository.CatalogRepository;
import com.mebel.shop.repository.OrdersRepository;
import com.mebel.shop.repository.UserRepository;
import com.mebel.shop.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
public class CatalogController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private OrdersRepository ordersRepository;

    @GetMapping("/catalog")
    public String CatalogPage(@AuthenticationPrincipal User user, HttpServletResponse response, Model model){
        List<Item> nado = catalogService.getAllItems();
        model.addAttribute("allItems", nado);
        model.addAttribute("user",user);
        return "catalog";
    }

    @PostMapping("/cart")
    public String createReq (@RequestParam(required = true, defaultValue = "" ) Long art, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UsersRequest newRequest = new UsersRequest();
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Date currentDate = new Date();
            newRequest.setDateOpened(currentDate);
            newRequest.setCreatedBy(user);
            newRequest.setOrder_name(catalogService.getItemById(art));
            ordersRepository.save(newRequest);
            return "redirect:/";
        }else{
            return "redirect:/login";
        }

    }

    @GetMapping("/claim")
    public String ClaimPage(@AuthenticationPrincipal User user, Model model){
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
        model.addAttribute("allTickets", catalogService.getAllReqInOpen(first, second));
        model.addAttribute("user",user);
        return "claim_request";
    }

    @PostMapping("/current_ticket")
    public String getTicketByIdd(@AuthenticationPrincipal User user,
                                 @RequestParam(required = true, defaultValue = "" ) Long ticketId,
                                 Model model) {



        return "redirect:/current_ticket/" + ticketId;
    }

    @GetMapping("/current_ticket/{id}")
    public String ticketByIdd (@PathVariable("id") Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User customUser = (User)authentication.getPrincipal();

        UsersRequest activeTicket = catalogService.getReqById(id);
        model.addAttribute("pht", activeTicket.getOrder_name().getPhoto_name());
        Status newStatus = new Status();

        List<Status> updates = activeTicket.getUpdates();

        if (customUser.getRoles().size() < 2) {
            if (!catalogService.isUsersTicket(customUser, id)) {
                return "Homepage";
            }
        }

        UsersRequest reqq = catalogService.getReqById(id);

        model.addAttribute("statuses", activeTicket.getUpdates());
        model.addAttribute("status", newStatus);
        model.addAttribute("ticket", reqq);
        model.addAttribute("item",reqq.getItem().getName());
        model.addAttribute("artic",reqq.getItem().getArticul());
        model.addAttribute("price",reqq.getItem().getPrice());
        model.addAttribute("currStatus", activeTicket.getStage().toString());
        return "current_ticket";
    }


}
