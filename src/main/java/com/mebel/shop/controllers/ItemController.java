package com.mebel.shop.controllers;

import com.mebel.shop.models.Item;
import com.mebel.shop.models.Stage;
import com.mebel.shop.models.User;
import com.mebel.shop.models.UsersRequest;
import com.mebel.shop.repository.CatalogRepository;
import com.mebel.shop.repository.OrdersRepository;
import com.mebel.shop.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.validation.Valid;
import java.util.Date;

@Controller
public class ItemController {

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private OrdersRepository ordersRepository;

    @PostMapping("/current_item")
    public String getTicketByIdd(@AuthenticationPrincipal User user,
                                 @RequestParam(required = true, defaultValue = "" ) Long art,
                                 Model model) {



        return "redirect:/current_item/" + art;
    }

    @GetMapping("/current_item/{art}")
    public String ticketByIdd (@PathVariable("art") Long art, Model model) {

        Item activeTicket = catalogService.getItemById(art);
        model.addAttribute("pht", activeTicket.getPhoto_name());
        model.addAttribute("item", catalogService.getItemById(art));
        return "current_item";
    }

    @PostMapping("/ok")
    public String SuccessReq(@AuthenticationPrincipal User user,
                                 @RequestParam(required = true, defaultValue = "" ) Long id,
                                 Model model) {
        UsersRequest find = catalogService.getReqById(id);
        find.setStage(Stage.WORKING);
        find.setLastUpdated(new Date());
        ordersRepository.save(find);
        return "redirect:/current_ticket/" + id;
    }

    @PostMapping("/no")
    public String DeniedRequest(@AuthenticationPrincipal User user,
                                 @RequestParam(required = true, defaultValue = "" ) Long id,
                                 Model model) {
        UsersRequest find = catalogService.getReqById(id);
        find.setStage(Stage.CLOSED);
        find.setDateClosed(new Date());
        ordersRepository.save(find);
        return "redirect:/current_ticket/" + id;
    }

    @PostMapping("/deliver")
    public String Delivered(@AuthenticationPrincipal User user,
                                @RequestParam(required = true, defaultValue = "" ) Long id,
                                Model model) {
        UsersRequest find = catalogService.getReqById(id);
        find.setStage(Stage.DELIVERED);
        find.setDateClosed(new Date());
        ordersRepository.save(find);
        return "redirect:/current_ticket/" + id;
    }
}
