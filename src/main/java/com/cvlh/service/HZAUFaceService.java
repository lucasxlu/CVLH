package com.cvlh.service;

import com.cvlh.model.HZAUFace;

import java.util.List;

/**
 * Created by 29140 on 2017/4/11.
 */
public interface HZAUFaceService {

    List<HZAUFace> showAllFaces();

    int score(String idnumber, double labeledScore, String labeler);

    List<HZAUFace> showFacesByRange(double minVal, double maxVal);
}
