package com.example.clevervision.controller;

import com.example.clevervision.model.UsersModel;
import com.example.clevervision.service.BusService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BusController {

 private final BusService busService;
    @Autowired
    private HttpServletRequest request;

    public BusController(BusService busService) {
        this.busService = busService;
    }

    @PostMapping("/StartBus")
    public String StartBus(Model model, HttpSession session)
   {
       UsersModel user = (UsersModel) session.getAttribute("user");
       busService.AddVoyage();
       busService.updateBusPositions();
       model.addAttribute("user",user);
     return("redirect:/main");
   }


}
