package com.cvlh.service.impl;

import com.cvlh.mapper.UserMapper;
import com.cvlh.model.User;
import com.cvlh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 29140 on 2017/2/23.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User showUser() {
        return null;
    }
}
