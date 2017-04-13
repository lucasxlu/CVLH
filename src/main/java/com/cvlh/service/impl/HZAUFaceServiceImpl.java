package com.cvlh.service.impl;

import com.cvlh.mapper.HZAUFaceMapper;
import com.cvlh.model.HZAUFace;
import com.cvlh.service.HZAUFaceService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 29140 on 2017/4/11.
 */
@Service
public class HZAUFaceServiceImpl implements HZAUFaceService {

    @Autowired
    private HZAUFaceMapper hzauFaceMapper;

    @Override
    public List<HZAUFace> showAllFaces() {
        return hzauFaceMapper.selectAllFaces();
    }

    @Override
    public int score(String idnumber, double labeledScore, String labeler) {
        return hzauFaceMapper.update(idnumber, labeledScore, labeler);
    }
}
