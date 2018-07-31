package cn.mislily.gmall.user.service.impl;

import cn.mislily.gmall.user.mapper.UserInfoMapper;
import cn.mislily.gmall.user.service.UserService;
import cn.mislily.gmall.user.bean.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    public List<UserInfo> userInfoList(){
        return userInfoMapper.selectAll();
    }

}
