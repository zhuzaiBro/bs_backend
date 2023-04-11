package com.gs.project.biz.mapper;

import com.gs.project.biz.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    public void insertUser(User user);

    public List<User> selectUserList(int start, int end, String order);

    public User selectUserByMaOpenId(String maOpenid);

    public User selectUserById(long id);

    public void updateUserInfo(User user);
}
