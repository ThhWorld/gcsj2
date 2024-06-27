package com.file_manager.service;

import com.file_manager.mapper.UserMapper;
import com.file_manager.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User login(String account, String password) {
        User user = userMapper.findByAccountAndPassword(account, password);
        return user ;
    }

    public boolean register(String account, String password, String userName, String phoneNumber, String email, Integer sex) {
        User existingUser = userMapper.findByAccount(account);
        if (existingUser != null) {
            return false;
        }

        User newUser = new User();
        newUser.setAccount(account);
        newUser.setPassword(password);
        newUser.setUserName(userName);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setEmail(email);
        newUser.setSex(sex);

        userMapper.insertUser(newUser);
        return true;
    }

    public boolean userinfochange(int id, String userName, String phoneNumber, String email) {
        User user = userMapper.findById(id);
        if (user == null) {
            return false; // User not found
        }

        user.setUserName(userName);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);

        userMapper.updateUser(user);
        return true;
    }

    public boolean changepwd(int id, String password) {
        User user = userMapper.findById(id);
        if (user == null) {
            return false;
        }

        user.setPassword(password);
        userMapper.updateUser(user);
        return true;
    }

}