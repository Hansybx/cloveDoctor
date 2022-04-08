package com.hansybx.clovedoctorbe.controller.user;

import com.hansybx.clovedoctorbe.DTO.CartDTO;
import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.service.user.CartService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/user/cart")
public class CartController {
    @Resource
    CartService cartService;

    @GetMapping("")
    public CommonResult getCartPageList(@RequestParam Integer userId, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return cartService.getCartPageList(userId,pageNum,pageSize);
    }

    @PostMapping("/del")
    public CommonResult removeCartItem(@RequestBody CartDTO cartDTO){
        return cartService.removeCartItem(cartDTO.getId());
    }
}
