package com.autism.tl.controlhelper.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * Created by 唐亮 on 2017/8/5.
 */
@Entity
public class ClassInfo implements Serializable {

    @Id
    private long id;
    private int grade;
    private String name;
    private String teacher;
    private String number;
    public String getNumber() {
        return this.number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getTeacher() {
        return this.teacher;
    }
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getGrade() {
        return this.grade;
    }
    public void setGrade(int grade) {
        this.grade = grade;
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    @Generated(hash = 217762611)
    public ClassInfo(long id, int grade, String name, String teacher, String number) {
        this.id = id;
        this.grade = grade;
        this.name = name;
        this.teacher = teacher;
        this.number = number;
    }
    @Generated(hash = 295356596)
    public ClassInfo() {
    }


}
