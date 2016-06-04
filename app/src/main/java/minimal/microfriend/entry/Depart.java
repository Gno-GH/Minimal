package minimal.microfriend.entry;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by gno on 16-6-4.
 */
public class Depart extends BmobObject {
    private String dname;//系院名称
    private List majors;//专业

    public List getMajors() {
        return majors;
    }

    public void setMajors(List majors) {
        this.majors = majors;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }
}
