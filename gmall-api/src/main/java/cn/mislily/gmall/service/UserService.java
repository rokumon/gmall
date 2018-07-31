package cn.mislily.gmall.service;

import cn.mislily.gmall.bean.UserAddress;
import cn.mislily.gmall.bean.UserInfo;

import java.util.List;

public interface UserService {

    //Info
    public List<UserInfo> userInfoList();

    public UserInfo userInfo(String id);

    Integer saveUserInfo(UserInfo userInfo);

    Integer deleteUserInfo(String id);

    Integer updateUserInfo(UserInfo userInfo);


    //Address
    public List<UserAddress> userAddressList();

    public UserAddress userAddress(String id);

    Integer saveUserAddress(UserAddress userAddress);

    Integer deleteUserAddress(String id);

    Integer updateUserAddress(UserAddress userAddress);


}
