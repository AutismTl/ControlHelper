package com.autism.tl.controlhelper.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * Created by 唐亮 on 2017/8/10.
 */
@Entity
public class StudentInfo implements Serializable{

    @Id
    private long id;
    private String classId;
    private String imageId;
    private String name;
    private String sex;
    private String home;
    private String qq;
    private String address;
    private String number;
    private String phone;
    private String remarks;
    public String getRemarks() {
        return this.remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getNumber() {
        return this.number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getQq() {
        return this.qq;
    }
    public void setQq(String qq) {
        this.qq = qq;
    }
    public String getHome() {
        return this.home;
    }
    public void setHome(String home) {
        this.home = home;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImageId() {
        return this.imageId;
    }
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
    public String getClassId() {
        return this.classId;
    }
    public void setClassId(String classId) {
        this.classId = classId;
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    @Generated(hash = 421072274)
    public StudentInfo(long id, String classId, String imageId, String name,
            String sex, String home, String qq, String address, String number,
            String phone, String remarks) {
        this.id = id;
        this.classId = classId;
        this.imageId = imageId;
        this.name = name;
        this.sex = sex;
        this.home = home;
        this.qq = qq;
        this.address = address;
        this.number = number;
        this.phone = phone;
        this.remarks = remarks;
    }
    @Generated(hash = 2016856731)
    public StudentInfo() {
    }
   
}
