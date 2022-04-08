package com.hansybx.clovedoctorbe.controller.user;

import com.hansybx.clovedoctorbe.DTO.CartDTO;
import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.service.user.CartService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/user/drug")
public class DrugController {
    @Resource
    CartService cartService;

    @PostMapping("/add")
    public CommonResult addCart(@RequestBody CartDTO cartDTO) {
        return cartService.addToCart(cartDTO);
    }
}
