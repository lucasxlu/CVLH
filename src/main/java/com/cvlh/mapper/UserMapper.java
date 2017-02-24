package com.cvlh.mapper;

import com.cvlh.model.User;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);
}