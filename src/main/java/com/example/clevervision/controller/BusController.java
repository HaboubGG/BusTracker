package com.example.clevervision.controller;

import com.example.clevervision.model.UsersModel;
import com.example.clevervision.service.BusService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BusController {

 private final BusService busService;
    @Autowired
    private HttpServletRequest request;

    public BusController(BusService busService) {
        this.busService = busService;
    }

    @PostMapping("/StartBus")
    public String StartBus(Model model, HttpSession session,
                           @RequestParam("driverId") int driverId,
                           @RequestParam("destination") int destination,
                           @RequestParam("busId") int busId)
   {
       UsersModel user = (UsersModel) session.getAttribute("user");
       busService.AddVoyage(busId , destination,driverId);
       busService.updateBusPositions();
       model.addAttribute("user",user);
     return("redirect:/main");
   }



}
