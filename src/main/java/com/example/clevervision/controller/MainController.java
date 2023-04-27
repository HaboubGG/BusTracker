package com.example.clevervision.controller;

import com.example.clevervision.model.PasswordChangeRequest;
import com.example.clevervision.model.UsersModel;
import com.example.clevervision.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {
    private final UsersService usersService;
    @Autowired
    private HttpServletRequest request;

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

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage() {
       HttpSession session = request.getSession(false);
       if(session!=null)
       {
           session.invalidate();
       }
       return "redirect:/login";
    }


    @GetMapping("/editProfile")
    public String showEditProfilePage(Model model, HttpSession session) {
        UsersModel user = (UsersModel) session.getAttribute("user");
        model.addAttribute("editProfileRequest",new PasswordChangeRequest());
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
    public String ChangePasswordProfile(@RequestParam String currentPassword ,@RequestParam String newPassword ,@RequestParam String confirmPassword, Model model, HttpSession session) {
            System.out.println("Edit profile request: " + currentPassword + " "+ newPassword + " " + confirmPassword);
            if (!newPassword.equals(confirmPassword))
            {
                return "redirect:/editProfile?error1";
            }
            else{
                UsersModel OldUser = (UsersModel) session.getAttribute("user");
                UsersModel UpdatedUser = usersService.ChangePassword(OldUser.getEmail(), currentPassword, newPassword);
                if(UpdatedUser!=null)
                {
                    session.setAttribute("user", UpdatedUser);
                    return "redirect:/main";
                }
                else
                {
                    return "redirect:/editProfile?error";
                }
            }
        }


    @GetMapping("/dashboard")
    public String showDashboard(Model model, HttpSession session) {
        UsersModel user = (UsersModel) session.getAttribute("user");
        if (user != null) {
            List<UsersModel> UsersList = usersService.listUsers();
            model.addAttribute("UsersList", UsersList);
            model.addAttribute("user",user);
            return "dashboard_page";
        } else {
            return "redirect:/login";
        }
    }
    @PostMapping("/dashboard/delete")
public String deleteUser(@RequestParam("id") int id , Model model, HttpSession session)
    {
        UsersModel user = (UsersModel) session.getAttribute("user");
        List<UsersModel> UsersList = usersService.listUsers();
        model.addAttribute("UsersList", UsersList);
        model.addAttribute("user",user);
        usersService.deleteUser(id);
        return "redirect:/dashboard";
    }

    }

