package com.hansybx.clovedoctorbe.DTO;

import com.hansybx.clovedoctorbe.model.Cart;
import lombok.Data;

@Data
public class CartItemDTO extends Cart {
    private String drugImg;
    private String drugName;
    private Float price;
}
