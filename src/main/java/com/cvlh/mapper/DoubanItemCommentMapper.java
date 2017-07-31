package com.cvlh.mapper;

import com.cvlh.model.DoubanItemReview;
import com.cvlh.model.douban.TimeSequence;

import java.util.List;

public interface DoubanItemCommentMapper {
    int deleteByPrimaryKey(String commentId);

    int insert(DoubanItemReview record);

    int insertSelective(DoubanItemReview record);

    DoubanItemReview selectByPrimaryKey(String commentId);

    int updateByPrimaryKeySelective(DoubanItemReview record);

    int updateByPrimaryKey(DoubanItemReview record);

    List<TimeSequence> selectCommentsDates();
}