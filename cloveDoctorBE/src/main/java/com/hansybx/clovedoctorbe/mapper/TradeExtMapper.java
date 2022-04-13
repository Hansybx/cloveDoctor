package com.hansybx.clovedoctorbe.mapper;

import com.hansybx.clovedoctorbe.DTO.TradeAdminDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TradeExtMapper {
    @Select("SELECT trade.*, `user`.username\n" +
            "FROM `user`\n" +
            "RIGHT JOIN trade\n" +
            "on trade.userId = `user`.id \n" +
            "WHERE `user`.username IS NOT NULL")
    List<TradeAdminDTO> getTradeList();

    @Select("SELECT trade.*, `user`.username\n" +
            "FROM `user`\n" +
            "LEFT JOIN trade\n" +
            "on trade.userId = `user`.id\n" +
            "WHERE `user`.username like CONCAT('%',#{keyword},'%') ")
    List<TradeAdminDTO> searchTradeList(@Param("keyword") String keyword);
}
