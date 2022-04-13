package com.hansybx.clovedoctorbe.mapper;

import com.hansybx.clovedoctorbe.DTO.CartItemDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CartExtMapper {

    @Select("SELECT cart.* , drugs.drugImg, drugs.drugName, drugs.price\n" +
            "FROM drugs\n" +
            "LEFT JOIN cart ON cart.drugId = drugs.id and drugs.stock > 0\n" +
            "WHERE cart.userId =#{UserId}\n" +
            "ORDER BY cart.updateTime DESC\n" +
            "LIMIT #{pageNum} , #{pageSize}")
    List<CartItemDTO> selectCartPage(@Param("UserId") Integer UserId,@Param("pageNum")Integer pageNum,@Param("pageSize")Integer pageSize);
}
