package com.hansybx.clovedoctorbe.model;

import java.util.Date;

public class RecommendDrugs {
    private Integer id;

    private Integer drugId;

    private Date addDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDrugId() {
        return drugId;
    }

    public void setDrugId(Integer drugId) {
        this.drugId = drugId;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }
}