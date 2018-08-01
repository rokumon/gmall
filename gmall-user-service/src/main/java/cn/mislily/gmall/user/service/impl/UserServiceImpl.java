package cn.mislily.gmall.user.service.impl;

import cn.mislily.gmall.bean.UserAddress;
import cn.mislily.gmall.user.mapper.UserAddressMapper;
import cn.mislily.gmall.user.mapper.UserInfoMapper;
import cn.mislily.gmall.service.UserService;
import cn.mislily.gmall.bean.UserInfo;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public UserInfo userInfo(String id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<UserInfo> userInfoList(){
        return userInfoMapper.selectAll();
    }

    @Override
    public Integer saveUserInfo(UserInfo userInfo) {
        return userInfoMapper.insert(userInfo);
    }

    @Override
    public Integer deleteUserInfo(String id) {
        return userInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer updateUserInfo(UserInfo userInfo) {
        return userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }


    @Override
    public UserAddress userAddress(String id) {
        return userAddressMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<UserAddress> userAddressList() {
        return userAddressMapper.selectAll();
    }

    @Override
    public Integer saveUserAddress(UserAddress userAddress) {
        return userAddressMapper.insert(userAddress);
    }

    @Override
    public Integer deleteUserAddress(String id) {
        return userAddressMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer updateUserAddress(UserAddress userAddress) {
        return userAddressMapper.updateByPrimaryKeySelective(userAddress);
    }


}
