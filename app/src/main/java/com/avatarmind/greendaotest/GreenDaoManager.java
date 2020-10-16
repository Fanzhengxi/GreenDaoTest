package com.avatarmind.greendaotest;


import android.util.Log;

import com.avatarmind.greendaotest.app.MyApplication;
import com.avatarmind.greendaotest.bean.Student;

import java.util.List;

import greendao.StudentDao;

/**
 *操作类，写增删查改方法
 */

public class GreenDaoManager {
    private static final String TAG = "GreenDaoManager";
    private static GreenDaoManager instance;
    private StudentDao dao;
    /**
     * 1 数据库类的创建
     * 线程安全创建DaoManager,使用单例模式获得操作数据库的对象
     *
     * @return note:为保证数据库的有效性，采用单利模式进行访问
     */
    //单例构造器
    public static GreenDaoManager getInstance() {
        if (instance == null) {
            synchronized (GreenDaoManager.class) {
                if (instance == null) {
                    instance = new GreenDaoManager();
                }
            }
        }
        return instance;
    }

    private GreenDaoManager() {
        dao = MyApplication.getApplication().getDaoSession().getStudentDao();
        //return studentDao, dao=studentDao
    }


    /**
     * 增加数据
     *
     * @param student
     * @return
     */
    long insertData(Student student) {
        Log.d(TAG,"insert===>"+student);
        return dao.insert(student);
    }

    /**
     * 删除数据
     *
     * @param id keyId
     */
    public void deleteData(Long id) {
        Log.d(TAG,"delete===>id"+id);
        dao.deleteByKey(id);
    }

    /**
     * 更改数据
     */
    public void updateData(Student student) {
        Log.d(TAG,"update===>"+student);
        dao.update(student);
    }

    /**
     * 查寻所有数据
     */
    public List<Student> queryAllData() {
        return dao.loadAll();
    }
}
