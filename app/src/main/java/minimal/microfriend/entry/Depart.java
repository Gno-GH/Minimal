package minimal.microfriend.entry;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by gno on 16-6-4.
 */
public class Depart extends BmobObject {
    private String dname;//系院名称
    private School school;//依赖的学校

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }
}
