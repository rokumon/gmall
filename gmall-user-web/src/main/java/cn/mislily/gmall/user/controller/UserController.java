package cn.mislily.gmall.user.controller;

import cn.mislily.gmall.bean.UserAddress;
import cn.mislily.gmall.bean.UserInfo;

import cn.mislily.gmall.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Reference
    private UserService userService;

    @RequestMapping("userInfo/{id}")
    public ResponseEntity<UserInfo> userInfo(@PathVariable("id")String id){
        UserInfo userInfo = userService.userInfo(id);
        return ResponseEntity.ok(userInfo);
    }

    @RequestMapping("userInfoList")
    public ResponseEntity<List<UserInfo>> userInfoList(){
        List<UserInfo> userInfoList = userService.userInfoList();
        return ResponseEntity.ok(userInfoList);
    }

    @RequestMapping("saveUserInfo")
    public ResponseEntity<Boolean> userInfo(UserInfo userInfo){
        Integer changedRow = userService.saveUserInfo(userInfo);
        if(changedRow != null && changedRow == 1){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }

    @RequestMapping("deleteUserInfo/{id}")
    public ResponseEntity<Boolean> deleteUserInfo(@PathVariable("id")String id){
        Integer changedRow = userService.deleteUserInfo(id);
        if(changedRow != null && changedRow == 1){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }

    @RequestMapping("updateUserInfo")
    public ResponseEntity<Boolean> updateUserInfo(UserInfo userInfo){
        Integer changedRow = userService.updateUserInfo(userInfo);
        if(changedRow != null && changedRow == 1){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }


    /**
     * 根据 id 获取 用户地址
     * @param id
     * @return
     */
    @RequestMapping("userAddress/{id}")
    public ResponseEntity<UserAddress> userAddress(@PathVariable("id")String id){
        UserAddress userAddress = userService.userAddress(id);
        return ResponseEntity.ok(userAddress);
    }

    /**
     * 获取用户列表
     * @return
     */
    @RequestMapping("userAddressList")
    public ResponseEntity<List<UserAddress>> userAddressList(){
        List<UserAddress> userAddressList = userService.userAddressList();
        return ResponseEntity.ok(userAddressList);
    }

    @RequestMapping("saveUserAddress")
    public ResponseEntity<Boolean> saveUserAddress(UserAddress userAddress){
        Integer changedRow = userService.saveUserAddress(userAddress);
        if(changedRow != null && changedRow == 1){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }

    @RequestMapping("deleteUserAddress/{id}")
    public ResponseEntity<Boolean> deleteUserAddress(@PathVariable("id")String id){
        Integer changedRow = userService.deleteUserAddress(id);
        if(changedRow != null && changedRow == 1){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }

    /**
     * 更新用户地址信息
     * @param userAddress
     * @return
     */
    @RequestMapping("updateUserAddress")
    public ResponseEntity<Boolean> updateUserAddress(UserAddress userAddress){
        Integer changedRow = userService.updateUserAddress(userAddress);
        if(changedRow != null && changedRow == 1){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }
}
