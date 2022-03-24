package com.hansybx.clovedoctorbe.DTO;

import lombok.Data;

@Data
public class SwiperDTO {
    private Integer id;

    private String imgUrl;

    private Integer drugId;

    private Integer sortId;

    private String addTime;
}