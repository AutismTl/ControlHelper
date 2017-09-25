package com.autism.tl.controlhelper.base;

/**
 * Created by 唐亮 on 2017/8/13.
 */

public class User {

    private static User user;
    private static int classId = 0;
    private static int gradeId = 0;
    private static int studentId = 0;

    private User() {

    }

    public static User getInstance() {
        user = new User();
        return user;
    }

    public void init(){
        classId=0;gradeId = 0;studentId = 0;
    }

    public int getClassId() {
        return classId;
    }

    public int getGradeId() {
        return gradeId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
