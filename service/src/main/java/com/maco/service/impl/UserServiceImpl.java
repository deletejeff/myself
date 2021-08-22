package com.maco.service.impl;

import com.maco.common.po.MyWxMpUser;
import com.maco.common.po.UserInfo;
import com.maco.common.po.UserRole;
import com.maco.dao.UserDao;
import com.maco.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    @Override
    public UserRole getRole(String openid){
        return userDao.getRole(openid);
    }

    @Override
    public MyWxMpUser getUserInfo(String openId) {
        return userDao.getUser(openId);
    }

    @Override
    public List<UserInfo> getUserList() {
        return userDao.getUserList();
    }
}
