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

    public void AddVoyage(int busId , int destination , int driverId)
    {
        VoyageModel newVoy = new VoyageModel();
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime heureArrive = currentTime.plus(Duration.ofMinutes(20));
        newVoy.setHeureArrive(heureArrive);
        newVoy.setEnRoute(1);
        if(destination == 1)
        {
            newVoy.setBusPosition(0);
        }
        else{
            newVoy.setBusPosition(101);
        }
        newVoy.setBusId(busId);
        newVoy.setDestination(destination);
        newVoy.setDriverId(driverId);
        busRepository.save(newVoy);
    }

    @Scheduled(fixedRate = 1000) // Run every second
    public void updateBusPositions() {
        // Get the current positions of all buses
        VoyageModel bus = busRepository.findFirstByEnRoute(1);
        if (bus != null) {
            //check if desination 1 : bizerte -> iset / 2 : iset -> bizerte
            if(bus.getDestination()==1){
                //check if bus has arrived or not to 101 : iset
                if (bus.getBusPosition() < 101) {
                    // Update the position of each bus
                    bus.setBusPosition(bus.getBusPosition() + 1);
                    // Save the updated bus positions to the database
                    busRepository.save(bus);
                }
                else{
                    bus.setEnRoute(0);
                    busRepository.deleteById(bus.getId());
                }
            }
            else if (bus.getDestination()==2){
                //check if bus has arrived or not to 0 : bizerte
                if (bus.getBusPosition() > 0) {
                    // Update the position of each bus
                    bus.setBusPosition(bus.getBusPosition() - 1);
                    // Save the updated bus positions to the database
                    busRepository.save(bus);
                }
                else{
                    bus.setEnRoute(0);
                    busRepository.deleteById(bus.getId());
                }
            }
        }
    }
}
