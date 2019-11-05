package com.ykm.service.impl;

import com.ykm.dao.UserDao;
import com.ykm.entity.User;
import com.ykm.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @description
 * @auth YKM
 * @date 2019/11/5 15:02
 **/
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    public User findById(String id) {
        return userDao.findById(id);
    }

    public List<User> find(Map<String, Object> map) {
        return userDao.find(map);
    }

    public Long getTotal(Map<String, Object> map) {
        return userDao.getTotal(map);
    }

    public int update(User user) {
        return userDao.update(user);
    }

    public int add(User user) {
        return userDao.add(user);
    }

    public int delete(String id) {
        return userDao.delete(id);
    }
}