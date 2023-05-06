package com.example.clevervision.controller;

import com.example.clevervision.model.BusModel;
import com.example.clevervision.model.UsersModel;
import com.example.clevervision.model.VoyageModel;
import com.example.clevervision.service.BusService;
import com.example.clevervision.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {
    private final UsersService usersService;
    private final BusService busService;
    @Autowired
    private HttpServletRequest request;

    public MainController(UsersService usersService, BusService busService) {
        this.usersService = usersService;
        this.busService = busService;
    }

    @GetMapping("/main")
    public String showMainPage(Model model, HttpSession session) {
        UsersModel user = (UsersModel) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            VoyageModel voyageModel = busService.VoyageData();
            List<VoyageModel> VoyageList = busService.listVoyageMain();
            if(voyageModel!=null) {
                model.addAttribute("VoyageData", voyageModel.getBusPosition());
            }
            if(VoyageList!=null)
            {
                model.addAttribute("VoyageList", VoyageList);
            }
            return "main_page";
        } else {
            return "redirect:/login";
        }
    }
    @GetMapping("/main/bus")
    public ResponseEntity<Integer> GetPosition()
    {
        VoyageModel voyageModel = busService.VoyageData();
        if(voyageModel!=null)
        {
            Integer data = voyageModel.getBusPosition();
            return ResponseEntity.ok(data);
        }
        else{
            return ResponseEntity.ofNullable(0);
        }
    }

    @GetMapping("/driverDashboard")
    public String showDashboardDriverPage(Model model, HttpSession session) {
        UsersModel user = (UsersModel) session.getAttribute("user");

        if (user != null && user.getRole()==2) {
            model.addAttribute("user", user);
            List<VoyageModel> VoyageList = busService.listVoyage(user.getId());
            model.addAttribute("VoyageList", VoyageList);
            return "driverDashboard_page";
        } else {
            return "redirect:/login";
        }
    }
    @GetMapping("/dashboardBus")
    public String showDashboardBusPage(Model model, HttpSession session) {
        UsersModel user = (UsersModel) session.getAttribute("user");
        if (user != null && user.getRole()==3) {
            List<BusModel> BusList = busService.showBusList();
            model.addAttribute("user", user);
            model.addAttribute("BusList",BusList);
            return "dashboardBus_page";
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
        if (user != null) {
            model.addAttribute("user", user);
            return "editProfile_page";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/travelDashboard")
    public String showTravelDashboardPage(Model model, HttpSession session) {
        UsersModel user = (UsersModel) session.getAttribute("user");
        if (user != null && user.getRole()==3) {
            model.addAttribute("user", user);
            List<UsersModel> DriversList = usersService.listDrivers();
            List<BusModel> BusList= busService.showBusDispoList();
            model.addAttribute("DriversList", DriversList);
            model.addAttribute("BusList", BusList);
            return "TravelDashboard_page";
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
    @PostMapping("/dashboard/editRole")
    public String editRole(@RequestParam("id") int id,@RequestParam("role") String role , Model model, HttpSession session)
    {
        UsersModel user = (UsersModel) session.getAttribute("user");
        List<UsersModel> UsersList = usersService.listUsers();
        model.addAttribute("UsersList", UsersList);
        model.addAttribute("user",user);
        usersService.EditRole(role,id);
        return "redirect:/dashboard";
    }

    }

