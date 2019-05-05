package com.autism.tl.controlhelper.db;

import com.autism.tl.controlhelper.base.MyApplication;
import com.autism.tl.controlhelper.model.ClassInfo;
import com.autism.tl.controlhelper.model.StudentInfo;
import com.autism.tl.greendao.gen.ClassInfoDao;
import com.autism.tl.greendao.gen.DaoSession;
import com.autism.tl.greendao.gen.StudentInfoDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 唐亮 on 2017/8/7.
 */
public class DBHelper {

    private static DBHelper dbHelper;
    private DaoSession daoSession;
    private ClassInfoDao classInfoDao;
    private StudentInfoDao studentInfoDao;

    private DBHelper() {
        daoSession = MyApplication.getDaoSession(MyApplication.getContext());
        classInfoDao = daoSession.getClassInfoDao();
        studentInfoDao = daoSession.getStudentInfoDao();
    }


    public static DBHelper getDBHelper() {
        dbHelper = new DBHelper();
        return dbHelper;
    }

    //获取班级列表长度
    public int getClassInfoSize(int id) {
        return classInfoDao.queryBuilder()
                .where(ClassInfoDao.Properties.Grade.eq(String.valueOf(id)))
                .list()
                .size();
    }

    //获取班级列表
    public List<ClassInfo> getClassInfo(int id) {
        return classInfoDao.queryBuilder()
                .where(ClassInfoDao.Properties.Grade.eq(String.valueOf(id)))
                .orderAsc(ClassInfoDao.Properties.Id)
                .list();
    }

    //存储班级信息
    public void loadClassInfo(List<ClassInfo> classInfoList, int id) {
        classInfoDao.deleteInTx(classInfoDao.queryBuilder()
                .where(ClassInfoDao.Properties.Grade.eq(String.valueOf(id)))
                .orderAsc(ClassInfoDao.Properties.Id)
                .list());
        for (ClassInfo classInfo : classInfoList) {
            classInfoDao.insert(classInfo);
        }
    }


    //获取学生列表长度
    public int getStudentInfoSize(int id) {
        return studentInfoDao.queryBuilder()
                .where(StudentInfoDao.Properties.ClassId.eq(String.valueOf(id)))
                .list()
                .size();
    }
    public int getStudentInfoSize() {
        return studentInfoDao.queryBuilder()
                .list()
                .size();
    }
    //获取学生列表
    public List<StudentInfo> getStudentInfo(int id) {
        return studentInfoDao.queryBuilder()
                .where(StudentInfoDao.Properties.ClassId.eq(String.valueOf(id)))
                .orderAsc(StudentInfoDao.Properties.Id)
                .list();
    }

    //获取学生图片列表
    public List<String> getImageList(List<StudentInfo> studentInfoList) {
        List<String> list = new ArrayList<String>();
        for (StudentInfo studentInfo : studentInfoList) {
            list.add(studentInfo.getImageId());
        }
        return list;
    }

    public List<String> getImageList(int id) {
        List<String> list = new ArrayList<String>();
        List<StudentInfo> slist = studentInfoDao.queryBuilder()
                .where(StudentInfoDao.Properties.ClassId.eq(String.valueOf(id)))
                .orderAsc(StudentInfoDao.Properties.Id)
                .list();
        for (StudentInfo studentInfo : slist) {
            list.add(studentInfo.getImageId());
        }
        return list;
    }

    //存储学生信息
    public void loadStudentInfo(List<StudentInfo> studentInfoList, int id) {
        studentInfoDao.deleteInTx(studentInfoDao.queryBuilder()
                .where(StudentInfoDao.Properties.ClassId.eq(String.valueOf(id)))
                .list());
        for (StudentInfo studentInfo : studentInfoList) {
            studentInfoDao.insert(studentInfo);
        }
    }

    //搜索寝室
    public List<StudentInfo> getDormitory(String id){
        StringBuffer dorm=new StringBuffer();
        dorm.append(String.valueOf(id.charAt(0)));dorm.append("-");
        dorm.append(String.valueOf(id.charAt(1)));dorm.append("-");
        dorm.append(String.valueOf(id.charAt(2)));dorm.append(String.valueOf(id.charAt(3)));
        dorm.append(String.valueOf(id.charAt(4)));
        return  studentInfoDao.queryBuilder()
                .where(StudentInfoDao.Properties.Home.eq(dorm.toString()))
                .orderAsc(StudentInfoDao.Properties.Id)
                .list();
    }

    //搜索学生
    public List<StudentInfo> searchStudent(String key,String value) {
        if(key.equals("姓名")) {
            return studentInfoDao.queryBuilder()
                    .where(StudentInfoDao.Properties.Name.eq(value))
                    .list();
        } else if(key.equals("学号")) {
            return studentInfoDao.queryBuilder()
                    .where(StudentInfoDao.Properties.Id.eq(value))
                    .orderAsc(StudentInfoDao.Properties.Id)
                    .list();
        } else{
            return studentInfoDao.queryBuilder()
                    .where(StudentInfoDao.Properties.Qq.eq(value))
                    .orderAsc(StudentInfoDao.Properties.Id)
                    .list();
        }

    }
    //存储全部学生信息
    public void loadAllStudentInfo(List<StudentInfo> studentInfoList) {
        studentInfoDao.deleteInTx(studentInfoDao.queryBuilder()
                .list());
        for (StudentInfo studentInfo : studentInfoList) {
            studentInfoDao.insert(studentInfo);
        }
    }
}
