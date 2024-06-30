package com.file_manager.Controller;


import com.file_manager.pojo.Login;
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
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Map<String, Object>  login (@RequestBody Login login) {
        User user = userService.login(login.getAccount(), login.getPassword());
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
    public boolean register(@RequestBody User user){
        return userService.register(user.getAccount(), user.getPassword(), user.getUserName(), user.getPhoneNumber(), user.getEmail(),user.getSex());
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
