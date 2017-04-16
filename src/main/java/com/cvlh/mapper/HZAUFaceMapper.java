package com.cvlh.mapper;

import com.cvlh.model.HZAUFace;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HZAUFaceMapper {
    int deleteByPrimaryKey(String idnumber);

    int insert(HZAUFace record);

    int insertSelective(HZAUFace record);

    HZAUFace selectByPrimaryKey(String idnumber);

    int updateByPrimaryKeySelective(HZAUFace record);

    int update(@Param("idnumber") String idnumber, @Param("labeledScore") double labeledScore, @Param("labeler") String labeler);

    int updateByPrimaryKey(HZAUFace record);

    List<HZAUFace> selectAllFaces();

    List<HZAUFace> showFacesByRange(@Param("minVal") double minVal, @Param("maxVal") double maxVal);
}