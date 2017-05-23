package com.ebiz.cdb.service.impl;

import com.ebiz.cdb.core.models.User;
import com.ebiz.cdb.persistence.dao.UserDao;
import com.ebiz.cdb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User get(String username) {
        return userDao.get(username);
    }

    @Override
    public User create(User u) {
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = get(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
}
