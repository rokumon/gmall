package cn.mislily.gmall.user.controller;

import cn.mislily.gmall.user.bean.UserInfo;

import cn.mislily.gmall.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("userInfoList")
    public ResponseEntity<List<UserInfo>> userInfoList(){
        List<UserInfo> userInfoList = userService.userInfoList();
        return ResponseEntity.ok(userInfoList);
    }
}
