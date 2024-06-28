package com.file_manager.Controller;


import com.file_manager.pojo.User;
import com.file_manager.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Map<String, Object>  login (@RequestParam("account") String account,@RequestParam("password") String password) {
        User user = userService.login(account, password);
        Map<String, Object> response = new HashMap<>();

        if (user != null) {
            response.put("success", true);
            response.put("user", user);
        } else {
            response.put("success", false);
            response.put("user", user);
        }

        return response;
    }

    @PostMapping("/register")
    public boolean register(@RequestParam String account, @RequestParam String password,
                            @RequestParam String userName, @RequestParam String phoneNumber,
                            @RequestParam String email, @RequestParam Integer sex){
        return userService.register(account,password,userName,phoneNumber,email,sex);
    }

    @PutMapping("/userinforchange")
    public boolean userinfochange(@RequestParam int id, @RequestParam String userName,
                                  @RequestParam String phoneNumber, @RequestParam String email) {
        return userService.userinfochange(id, userName, phoneNumber, email);

    }

    @PatchMapping("/changepwd")
    public boolean changepwd(@RequestParam int id, @RequestParam String password){
        return userService.changepwd(id,password);
    }
}
