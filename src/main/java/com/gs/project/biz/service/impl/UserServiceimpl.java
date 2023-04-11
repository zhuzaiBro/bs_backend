package com.gs.project.biz.service.impl;

import com.gs.project.biz.domain.User;
import com.gs.project.biz.mapper.UserMapper;
import com.gs.project.biz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceimpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public boolean createUser(User u) {
        try {
            userMapper.insertUser(u);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public User handleUserLogin(User u) {

        // 检查user是否存在
        User data = userMapper.selectUserByMaOpenId(u.getMaOpenId());
        if (data != null && data.getId() > 0) {
            // 说明用户存在,返回拆寻到的用户
            return data;
        } else {
            // 不存在，创建user
            this.createUser(u);
            data = userMapper.selectUserByMaOpenId(u.getMaOpenId());
            return data;
        }
    }

    @Override
    public User getUserInfo(long id) {
        return userMapper.selectUserById(id);
    }

    @Override
    public boolean updateUserInfo(User u) {
        return false;
    }

    /**
     * Admin
     * 查询用户列表
     */
    @Override
    public List<User> getUserList(Integer page , Integer size, String order) {
        int start = (page - 1) * size;

        return userMapper.selectUserList(start, start + size, order);

    }
}
