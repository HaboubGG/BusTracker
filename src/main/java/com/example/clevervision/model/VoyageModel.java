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
    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "VoyageModel{" +
                "id=" + id +
                ", HeureDepart=" + HeureDepart +
                ", HeureArrive=" + HeureArrive +
                ", HeureArrive=" + enRoute +
                ", HeureArrive=" + busPosition +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoyageModel that = (VoyageModel) o;
        return Objects.equals(id, that.id) && Objects.equals(HeureDepart, that.HeureDepart) && Objects.equals(HeureArrive, that.HeureArrive) && Objects.equals(enRoute, that.enRoute) && Objects.equals(busPosition, that.busPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, HeureDepart, HeureArrive, enRoute, busPosition);
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
