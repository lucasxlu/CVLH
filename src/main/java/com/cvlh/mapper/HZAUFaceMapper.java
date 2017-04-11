package com.cvlh.mapper;

import com.cvlh.model.HZAUFace;

import java.util.List;

public interface HZAUFaceMapper {
    int deleteByPrimaryKey(String idnumber);

    int insert(HZAUFace record);

    int insertSelective(HZAUFace record);

    HZAUFace selectByPrimaryKey(String idnumber);

    int updateByPrimaryKeySelective(HZAUFace record);

    int update(String idnumber, double labeledScore, String labeler);

    int updateByPrimaryKey(HZAUFace record);

    List<HZAUFace> selectAllFaces();
}