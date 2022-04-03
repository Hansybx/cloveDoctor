package com.hansybx.clovedoctorbe.mapper;

import com.hansybx.clovedoctorbe.DTO.RecommendDrugsDTO;
import com.hansybx.clovedoctorbe.model.RecommendDrugs;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface RecommendDrugsExtMapper {
    @Insert("INSERT INTO recommenddrugs (drugId) VALUES (#{drugId})")
    int insertItem(@Param("drugId")Integer drugId);

    @Delete("DELETE FROM recommenddrugs\n" +
            "WHERE drugId = #{id}")
    int deleteItem(@Param("id")Integer id);

    @Select("SELECT recommenddrugs.* , drugs.drugImg, drugs.drugName\n" +
            "FROM drugs\n" +
            "LEFT JOIN recommenddrugs ON recommenddrugs.drugId = drugs.id\n" +
            "WHERE recommenddrugs.drugId = #{drugId}")
    RecommendDrugsDTO selectRecommondDrugByDrugId(@Param("drugId")Integer drugId);

    @Select("SELECT recommenddrugs.* , drugs.drugImg, drugs.drugName\n" +
            "FROM drugs\n" +
            "LEFT JOIN recommenddrugs ON recommenddrugs.drugId = drugs.id\n" +
            "WHERE recommenddrugs.drugId = drugs.id " +
            "ORDER BY recommenddrugs.id DESC\n" +
            "LIMIT #{pageNum} , #{pageSize}")
    List<RecommendDrugsDTO> selectAll(@Param("pageNum")Integer pageNum,@Param("pageSize")Integer pageSize);
}