package minimal.microfriend.entry;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 用户类
 */
public class User extends BmobUser implements Serializable{
    private String about;//自我简介
    private String sex;//性别
    private Integer age;//年龄
    private School school;//学校
    private Depart depart;//系院
    private Major major;//主修
    private Integer grade;//年级
    private BmobFile userphoto;//用户头像
    private String petname;//用户昵称
    private String brith;//用户生日
    private Integer level;//用户的年级

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getBrith() {
        return brith;
    }

    public void setBrith(String brith) {
        this.brith = brith;
    }

    public String getPetname() {
        return petname;
    }

    public void setPetname(String petname) {
        this.petname = petname;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Depart getDepart() {
        return depart;
    }

    public void setDepart(Depart depart) {
        this.depart = depart;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public BmobFile getUserphoto() {
        return userphoto;
    }

    public void setUserphoto(BmobFile userphoto) {
        this.userphoto = userphoto;
    }
}
