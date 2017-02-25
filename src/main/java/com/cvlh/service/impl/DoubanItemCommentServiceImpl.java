package com.cvlh.service.impl;

import com.cvlh.mapper.DoubanItemCommentMapper;
import com.cvlh.model.DoubanItemComment;
import com.cvlh.model.douban.TimeSequence;
import com.cvlh.service.DoubanItemCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by 29140 on 2017/2/25.
 */
@Service
public class DoubanItemCommentServiceImpl implements DoubanItemCommentService {

    @Autowired
    private DoubanItemCommentMapper doubanItemCommentMapper;

    @Override
    public int addDoubanItemComment(DoubanItemComment doubanItemComment) {
        return doubanItemCommentMapper.insert(doubanItemComment);
    }

    @Override
    public List<TimeSequence> showCommentsTimeSeq() {
        return doubanItemCommentMapper.selectCommentsDates();
    }


}
