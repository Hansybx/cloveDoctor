package com.hansybx.clovedoctorbe.mapper;

import com.hansybx.clovedoctorbe.model.Drugs;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DrugsExtMapper {
    @Select("SELECT * FROM drugs WHERE concat(id, drugName) " +
            "LIKE CONCAT('%',#{keyword},'%') " +
            "AND status = #{status}")
    List<Drugs> search(@Param("keyword") String keyword, @Param("status") int status);

    @Select("SELECT drugs.*  FROM drugs \n" +
            "WHERE\n" +
            "\tconcat( drugs.drugName, drugs.id ) LIKE CONCAT('%',#{keyword},'%') AND status = 1\n" +
            "\tAND drugs.id NOT IN ( SELECT recommenddrugs.drugId FROM recommenddrugs )")
    List<Drugs> searchUnuse(@Param("keyword") String keyword);
}
