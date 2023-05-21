package com.example.clevervision.model;

import jakarta.persistence.*;

@Entity
@Table(name="report_table")
public class ReportModel {
    @GeneratedValue
    @Id
    int id ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voy")
    TravelModel voy;

    String Reportername;
    int type;
    String description;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TravelModel getVoy() {
        return voy;
    }

    public void setVoy(TravelModel voy) {
        this.voy = voy;
    }

    public String getReportername() {
        return Reportername;
    }

    public void setReportername(String reportername) {
        Reportername = reportername;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
