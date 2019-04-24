package com.example.demo.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lwx
 * @since 2019-03-26
 */
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String geNumber;

    private String geName;

    private String password;

    private String userState;

    private String phone;

    private String email;

    private String createTime;

    private String updateTime;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getGeNumber() {
        return geNumber;
    }

    public void setGeNumber(String geNumber) {
        this.geNumber = geNumber;
    }
    public String getGeName() {
        return geName;
    }

    public void setGeName(String geName) {
        this.geName = geName;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "User{" +
        "id=" + id +
        ", geNumber=" + geNumber +
        ", geName=" + geName +
        ", password=" + password +
        ", userState=" + userState +
        ", phone=" + phone +
        ", email=" + email +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
