package com.cvlh.mapper;

import com.cvlh.model.DoubanItemComment;
import com.cvlh.model.douban.TimeSequence;

import java.util.List;

public interface DoubanItemCommentMapper {
    int deleteByPrimaryKey(String commentId);

    int insert(DoubanItemComment record);

    int insertSelective(DoubanItemComment record);

    DoubanItemComment selectByPrimaryKey(String commentId);

    int updateByPrimaryKeySelective(DoubanItemComment record);

    int updateByPrimaryKey(DoubanItemComment record);

    List<TimeSequence> selectCommentsDates();
}