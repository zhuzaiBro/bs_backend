package com.gs.project.biz.service;

import com.gs.project.biz.domain.User;

import java.util.List;

public interface UserService {
    public boolean createUser(User u);

    public User handleUserLogin(User u);

    public User getUserInfo(long id);


    public boolean updateUserInfo(User u);

    public List<User> getUserList(Integer page, Integer  size, String order);
}
