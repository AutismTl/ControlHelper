package com.autism.tl.controlhelper.model;

/**
 * Created by 唐亮 on 2017/8/14.
 */

public class PostInfo {
    String id;
    Boolean is;
    public PostInfo(String id,Boolean is){
        this.is=is; this.id=id;
    }
    public String getId(){
        return id;
    }
    public Boolean getIs(){
        return is;
    }
}
