package com.example.clevervision.controller;

import com.example.clevervision.model.PasswordChangeRequest;
import com.example.clevervision.model.UsersModel;
import com.example.clevervision.service.UsersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MainController {
    private final UsersService usersService;

    public MainController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/main")
    public String showMainPage(Model model, HttpSession session) {
        UsersModel user = (UsersModel) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            return "main_page";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/editProfile")
    public String showEditProfilePage(Model model, HttpSession session) {
        UsersModel user = (UsersModel) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            return "editProfile_page";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/editProfile")
    public String editProfile(@ModelAttribute UsersModel usersModel, Model model, HttpSession session) {
        System.out.println("Edit profile request: " + usersModel);
        UsersModel OldUser = (UsersModel) session.getAttribute("user");
       System.out.println("Old user"+ OldUser.getEmail()+ OldUser.getRole());
        UsersModel UpdatedUser = usersService.UpdateProfile(OldUser.getEmail(), usersModel.getLogin(), usersModel.getEmail());
        if (UpdatedUser != null) {
            session.setAttribute("user", UpdatedUser);
            return "redirect:/main";
        }
        else  return "redirect:/editProfile?error";
        }
        @PostMapping("/ChangePasswordProfile")
    public String ChangePasswordProfile(@RequestBody PasswordChangeRequest pass, Model model, HttpSession session) {
            System.out.println("Edit profile request: " + pass);
            UsersModel OldUser = (UsersModel) session.getAttribute("user");
            return "redirect:/main";
        }


    }

