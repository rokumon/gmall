package cn.mislily.gmall.bean;

import cn.mislily.gmall.bean.interfaces.DataBaseUpdateEntity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class UserInfo implements Serializable, DataBaseUpdateEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column
    private String loginName;
    @Column
    private String nickName;
    @Column
    private String passwd;
    @Column
    private String name;
    @Column
    private String phoneNum;
    @Column
    private String email;
    @Column
    private String headImg;
    @Column
    private String userLevel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = (id == "" ? null : id);
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = (loginName == "" ? null : loginName);
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = (nickName == "" ? null : nickName);
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = (passwd == "" ? null : passwd);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = (name == "" ? null : name);
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = (phoneNum == "" ? null : phoneNum);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = (email == "" ? null : email);
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = (headImg == "" ? null : headImg);
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = (userLevel == "" ? null : userLevel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserInfo userInfo = (UserInfo) o;

        if (id != null ? !id.equals(userInfo.id) : userInfo.id != null) return false;
        if (loginName != null ? !loginName.equals(userInfo.loginName) : userInfo.loginName != null) return false;
        if (nickName != null ? !nickName.equals(userInfo.nickName) : userInfo.nickName != null) return false;
        if (passwd != null ? !passwd.equals(userInfo.passwd) : userInfo.passwd != null) return false;
        if (name != null ? !name.equals(userInfo.name) : userInfo.name != null) return false;
        if (phoneNum != null ? !phoneNum.equals(userInfo.phoneNum) : userInfo.phoneNum != null) return false;
        if (email != null ? !email.equals(userInfo.email) : userInfo.email != null) return false;
        if (headImg != null ? !headImg.equals(userInfo.headImg) : userInfo.headImg != null) return false;
        return userLevel != null ? userLevel.equals(userInfo.userLevel) : userInfo.userLevel == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (loginName != null ? loginName.hashCode() : 0);
        result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
        result = 31 * result + (passwd != null ? passwd.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phoneNum != null ? phoneNum.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (headImg != null ? headImg.hashCode() : 0);
        result = 31 * result + (userLevel != null ? userLevel.hashCode() : 0);
        return result;
    }
}
