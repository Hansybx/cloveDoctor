package com.hansybx.clovedoctorbe.mapper;

import com.hansybx.clovedoctorbe.model.RecommendDrugs;
import com.hansybx.clovedoctorbe.model.RecommendDrugsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RecommendDrugsMapper {
    long countByExample(RecommendDrugsExample example);

    int deleteByExample(RecommendDrugsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RecommendDrugs record);

    int insertSelective(RecommendDrugs record);

    List<RecommendDrugs> selectByExample(RecommendDrugsExample example);

    RecommendDrugs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RecommendDrugs record, @Param("example") RecommendDrugsExample example);

    int updateByExample(@Param("record") RecommendDrugs record, @Param("example") RecommendDrugsExample example);

    int updateByPrimaryKeySelective(RecommendDrugs record);

    int updateByPrimaryKey(RecommendDrugs record);
}