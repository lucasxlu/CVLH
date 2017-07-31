package com.cvlh.service;

import com.cvlh.model.DoubanItemReview;
import com.cvlh.model.douban.TimeSequence;

import java.util.List;

/**
 * Created by 29140 on 2017/2/25.
 */
public interface DoubanItemCommentService {

    int addDoubanItemComment(DoubanItemReview doubanItemComment);

    List<TimeSequence> showCommentsTimeSeq();
}
