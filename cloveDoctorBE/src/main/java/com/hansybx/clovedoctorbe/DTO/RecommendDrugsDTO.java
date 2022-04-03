package com.hansybx.clovedoctorbe.DTO;

import com.hansybx.clovedoctorbe.model.RecommendDrugs;
import lombok.Data;

@Data
public class RecommendDrugsDTO extends RecommendDrugs {
    private String drugImg;
    private String drugName;
}
