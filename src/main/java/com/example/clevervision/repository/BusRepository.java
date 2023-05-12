package com.example.clevervision.repository;

import com.example.clevervision.model.UsersModel;
import com.example.clevervision.model.VoyageModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusRepository  extends JpaRepository<VoyageModel,Integer> {
VoyageModel findFirstByEnRoute(int x);
    VoyageModel findFirstById(int x);
    List<VoyageModel> findAllByDriverId(int id);
    void deleteById(int id);

}
