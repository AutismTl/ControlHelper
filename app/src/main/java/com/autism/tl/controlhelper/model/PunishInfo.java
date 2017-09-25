package com.autism.tl.controlhelper.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 唐亮 on 2017/8/16.
 */
@Entity
public class PunishInfo {
    @Id
    private long id;
    private int type;
    private String name;
    private String person;
    private String date;
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getPerson() {
        return this.person;
    }
    public void setPerson(String person) {
        this.person = person;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    @Generated(hash = 317445765)
    public PunishInfo(long id, int type, String name, String person, String date) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.person = person;
        this.date = date;
    }
    @Generated(hash = 1367026000)
    public PunishInfo() {
    }

}
