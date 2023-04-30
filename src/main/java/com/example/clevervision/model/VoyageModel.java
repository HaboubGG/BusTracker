package com.example.clevervision.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="voyage_table")
public class VoyageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id ;
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime HeureDepart = LocalDateTime.now();

    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime HeureArrive;

    Integer enRoute ;
    Integer busPosition;

    Integer destination;

    Integer busId;
    Integer driverId;

    @Override
    public String toString() {
        return "VoyageModel{" +
                "id=" + id +
                ", HeureDepart=" + HeureDepart +
                ", HeureArrive=" + HeureArrive +
                ", enRoute=" + enRoute +
                ", busPosition=" + busPosition +
                ", destination=" + destination +
                ", busId=" + busId +
                ", driverId=" + driverId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoyageModel that = (VoyageModel) o;
        return Objects.equals(id, that.id) && Objects.equals(HeureDepart, that.HeureDepart) && Objects.equals(HeureArrive, that.HeureArrive) && Objects.equals(enRoute, that.enRoute) && Objects.equals(busPosition, that.busPosition) && Objects.equals(destination, that.destination) && Objects.equals(busId, that.busId) && Objects.equals(driverId, that.driverId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, HeureDepart, HeureArrive, enRoute, busPosition, destination, busId, driverId);
    }

    public Integer getDestination() {
        return destination;
    }

    public void setDestination(Integer destination) {
        this.destination = destination;
    }

    public Integer getBusId() {
        return busId;
    }

    public void setBusId(Integer busId) {
        this.busId = busId;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getBusPosition() {
        return busPosition;
    }

    public void setBusPosition(Integer busPosition) {
        this.busPosition = busPosition;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public LocalDateTime getHeureDepart() {
        return HeureDepart;
    }

    public void setHeureDepart(LocalDateTime heureDepart) {
        HeureDepart = heureDepart;
    }

    public Integer getEnRoute() {
        return enRoute;
    }


    public void setEnRoute(Integer enRoute) {
        this.enRoute = enRoute;
    }

    public LocalDateTime getHeureArrive() {
        return HeureArrive;
    }

    public void setHeureArrive(LocalDateTime heureArrive) {
        HeureArrive = heureArrive;
    }



}
