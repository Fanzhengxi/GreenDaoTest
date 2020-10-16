package com.avatarmind.greendaotest.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by zhangqun on 18-4-8.
 */
@Entity
public class Student {

    @Id
    private Long id;
    private String name;
    private String sex;
    private int age;
    private float score;
    //isSelected应该是标记选中的数据项的，但是怎么用呢？
    private boolean isSelected;

    @Generated(hash = 66442372)
    //有isSelected标记的Student构造器
    public Student(Long id, String name, String sex, int age, float score,
                   boolean isSelected) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.score = score;
        this.isSelected = isSelected;
    }
//没有isSelected标记的Studet构造器
    public Student(Long id, String name, String sex, int age, float score
    ) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.score = score;
    }


    @Generated(hash = 1556870573)
    public Student() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getScore() {
        return this.score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public boolean getIsSelected() {
        return this.isSelected;
    }
    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
    //这两个方法不是对象isSelectrd的get和set函数，怎么用的？
    public boolean isSelected() {
        return isSelected;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", score=" + score +
                ", isSelected=" + isSelected +
                '}';
    }
}
