package com.hansybx.clovedoctorbe.service.user;

import com.hansybx.clovedoctorbe.DTO.CartDTO;
import com.hansybx.clovedoctorbe.DTO.CartItemDTO;
import com.hansybx.clovedoctorbe.common.CommonResponse;
import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.mapper.CartExtMapper;
import com.hansybx.clovedoctorbe.mapper.CartMapper;
import com.hansybx.clovedoctorbe.model.Cart;
import com.hansybx.clovedoctorbe.model.CartExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class CartService {
    @Resource
    CartMapper cartMapper;

    @Resource
    CartExtMapper cartExtMapper;

    public CommonResult getCartPageList(Integer userId, Integer pageNum, Integer pageSize) {
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUserIdEqualTo(userId);
        Long total = cartMapper.countByExample(cartExample);

        List<CartItemDTO> cartList = cartExtMapper.selectCartPage(userId,pageNum-1,pageSize);

        Map<String, Object> map = new HashMap<>();
        map.put("cartList", cartList);
        map.put("total", total);
        return CommonResponse.Success(map);
    }

//    public CommonResult getUserCartList(CartDTO cartDTO,Integer pageNum, Integer pageSize) {
//        PageHelper.startPage(pageNum, pageSize);
//        List<CartItemDTO> cartList = cartExtMapper.selectCartPage(cartDTO.getUserId());
//        return CommonResponse.Success(cartList);
//    }

    public CommonResult addToCart(CartDTO cartDTO) {
        // 更新时直接使用总数量， 如原先是1 想更新为2 cartDTO中的drugNum直接传入2
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andDrugIdEqualTo(cartDTO.getDrugId())
                .andUserIdEqualTo(cartDTO.getUserId());

        TimeZone time = TimeZone.getTimeZone("Etc/GMT-8");  //转换为中国时区
        TimeZone.setDefault(time);
        cartDTO.setUpdateTime(new Date());
        Long exist = cartMapper.countByExample(cartExample);
        int res;
        if (exist > 0) {
            List<Cart> cartList = cartMapper.selectByExample(cartExample);
            Cart cartUpdate = cartList.get(0);
            cartUpdate.setDrugNum(cartUpdate.getDrugNum()+cartDTO.getDrugNum());
            cartUpdate.setUpdateTime(cartDTO.getUpdateTime());
            res = cartMapper.updateByExample(cartUpdate, cartExample);
        } else {
            res = cartMapper.insert(cartDTO);
        }
        if (res > 0) {
            return CommonResponse.Success("添加成功", "");
        }
        return CommonResponse.Fail("添加失败");
    }

    public CommonResult removeCartItem(Integer id){
        int res = cartMapper.deleteByPrimaryKey(id);
        if(res>0) return CommonResponse.Success("移除成功","");
        return  CommonResponse.Fail("移除失败");
    }
}
