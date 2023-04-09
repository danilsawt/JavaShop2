package com.mebel.shop.controllers;

import com.mebel.shop.models.Role;
import com.mebel.shop.models.User;
import com.mebel.shop.repository.RoleRepository;
import com.mebel.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;
    @GetMapping("/admin")
    public String userList(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("userForm", new User());
        model.addAttribute("allUsers", userService.allUsers());
        model.addAttribute("user", user);
        return "admin";
    }

    @PostMapping("/admin")
    public String deleteUser(@AuthenticationPrincipal User user, @RequestParam(required = true, defaultValue = "") Long userId,
                             @RequestParam(required = true, defaultValue = "") String action,Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User customUser = (User)authentication.getPrincipal();
        if(action.equals("delete")){
            User userr = userService.get(userId);
            userService.deleteUser(userId);
        }
        if (action.equals("give_manager")){
            User userr = userService.get(userId);
            userr.getRoles().add(new Role(2L,"ROLE_MANAGER"));
            userService.saveWith(userr);
        }
        if (action.equals("delete_manager")){
            User userr = userService.get(userId);
            userr.getRoles().clear();
            userr.getRoles().add(new Role(1L,"ROLE_USER"));
            userService.saveWith(userr);
        }
        model.addAttribute("user",user);
        return "redirect:/admin";
    }
    @PostMapping("/admin_registration")
    public String addUserByAdmin(@ModelAttribute("userrForm")@Valid User userrForm, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "admin";
        }
        if (!userrForm.getPassword().equals(userrForm.getPasswordConfirm())){
            model.addAttribute("passwordError","Пароли не совпадают");
            return "admin";
        }
        if (!userService.saveUser(userrForm)){
            model.addAttribute("usernameError","Пользователь с таким именем уже существует!");
            return "admin";
        }
        return "redirect/admin";
    }

}
//adcont