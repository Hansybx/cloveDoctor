package com.hansybx.clovedoctorbe.DTO;

import lombok.Data;

@Data
public class DrugsDTO {
    private Integer id;

    private String drugName;

    private String drugImg;

    private Integer stock;

    private String updateTime;

    private Float price;

    private Integer status;
}
