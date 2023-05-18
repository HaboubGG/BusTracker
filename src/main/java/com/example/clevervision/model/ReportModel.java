package com.example.clevervision.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="report_table")
public class ReportModel {
    @GeneratedValue
    @Id
    int id ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voy")
    VoyageModel voy;

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

    public VoyageModel getVoy() {
        return voy;
    }

    public void setVoy(VoyageModel voy) {
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
