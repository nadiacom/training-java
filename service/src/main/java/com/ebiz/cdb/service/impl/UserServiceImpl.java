package com.ebiz.cdb.service.impl;

import com.ebiz.cdb.core.models.User;
import com.ebiz.cdb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.ebiz.cdb.persistence.dao.UserDao;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User get(String username, String password) {
        return userDao.get(username, password);
    }

    @Override
    public Long create(User u) {
        return userDao.create(u);
    }

    @Override
    public void update(User u) {
        userDao.update(u);
    }

    @Override
    public void delete(User u) {
        userDao.delete(u);
    }
}
