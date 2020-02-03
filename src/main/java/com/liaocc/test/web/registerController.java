package com.liaocc.test.web;

import com.liaocc.test.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class registerController {
    @Autowired
    UserService userService;
    @PostMapping("register")
    String register(@RequestBody String jsonstr){
        JSONObject jsonObject=new JSONObject(jsonstr);

        String username=jsonObject.getString("username");
        String psw=jsonObject.getString("psw");

        boolean flag=userService.register(username,psw);
        if(flag)
            return "ok";
        return "no";
    }
    //是否已经注册过了
    @PostMapping("register/verify")
    String verify(@RequestBody String jsonstr){
        JSONObject jsonObject=new JSONObject(jsonstr);

        String username=jsonObject.getString("username");

        boolean flag=userService.verify(username);
        if(flag)
            return "ok";
        return "no";
    }
}
