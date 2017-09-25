package com.autism.tl.controlhelper.model;

/**
 * Created by 唐亮 on 2017/8/17.
 */

public class Respond {
    private String status;

    public Respond(String status){
        this.status=status;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status=status;
    }
}
