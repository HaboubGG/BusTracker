package com.example.clevervision.repository;

import com.example.clevervision.model.CompletedTravelsModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompletedTravelsRepository extends JpaRepository<CompletedTravelsModel,Integer> {
}
