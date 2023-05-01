package com.example.clevervision.service;

import com.example.clevervision.model.BusModel;
import com.example.clevervision.model.UsersModel;
import com.example.clevervision.model.VoyageModel;
import com.example.clevervision.repository.BusRepository;
import com.example.clevervision.repository.GarageRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class BusService {
    private final BusRepository busRepository;
    private final GarageRepository garageRepository;

    public BusService(BusRepository busRepository , GarageRepository garageRepository) {
        this.busRepository = busRepository;
        this.garageRepository=garageRepository;
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

    public List<VoyageModel> listVoyage(int id)
    {
        List<VoyageModel> VoyageList = busRepository.findAllByDriverId(id);
        return VoyageList;
    }
    public Boolean AddBus(int mat , String marque)
    {
        if( garageRepository.findFirstByMat(mat) == null ) {
            BusModel bus = new BusModel();
            bus.setMat(mat);
            bus.setMarque(marque);
            bus.setDispo(true);
            garageRepository.save(bus);
            return true;
        }
        else {
            return false;
        }
    }

    public List<BusModel> showBusList()
    {
        List <BusModel> busList = garageRepository.findAll();
        return busList;
    }
    public List<BusModel> showBusDispoList()
    {
        List <BusModel> busList = garageRepository.findAllByDispo(true);
        return busList;
    }


    public void AddVoyage(int busId , int destination , int driverId , LocalTime startTime)
    {
        VoyageModel newVoy = new VoyageModel();
        newVoy.setHeureDepart(startTime);
        newVoy.setHeureArrive(startTime.plusMinutes(20));
        newVoy.setEnRoute(0);
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
    public void StartBusNow(int id)
    {
        VoyageModel bus = busRepository.findFirstById(id);
        bus.setEnRoute(1);
        busRepository.save(bus);
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
