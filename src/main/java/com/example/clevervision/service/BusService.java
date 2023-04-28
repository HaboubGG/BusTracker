package com.example.clevervision.service;

import com.example.clevervision.model.UsersModel;
import com.example.clevervision.model.VoyageModel;
import com.example.clevervision.repository.BusRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BusService {
    private final BusRepository busRepository;

    public BusService(BusRepository busRepository) {
        this.busRepository = busRepository;
    }
    public VoyageModel VoyageData()
    {VoyageModel voyageModel = busRepository.findFirstByEnRoute(1);
        if(voyageModel!=null){
            return voyageModel;
        }
        else{
            return null;
        }
    }

    public void AddVoyage()
    {
        VoyageModel newVoy = new VoyageModel();
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime heureArrive = currentTime.plus(Duration.ofMinutes(20));
        newVoy.setHeureArrive(heureArrive);
        newVoy.setEnRoute(1);
        newVoy.setBusPosition(0);
        busRepository.save(newVoy);
    }
    @Scheduled(fixedRate = 1000) // Run every second
    public void updateBusPositions() {
        // Get the current positions of all buses
        VoyageModel bus = busRepository.findFirstByEnRoute(1);
        if (bus != null) {
            if (bus.getBusPosition() < 101) {
                // Update the position of each bus
                bus.setBusPosition(bus.getBusPosition() + 1);
                // Save the updated bus positions to the database
                busRepository.save(bus);
            } else {
                bus.setEnRoute(0);
                busRepository.save(bus);
            }
        }
    }
}
