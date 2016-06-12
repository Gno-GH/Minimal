package minimal.microfriend.entry;

import cn.bmob.v3.BmobObject;

/**
 * Created by gno on 16/6/12.
 */
public class UserID extends BmobObject{
    private String userid;
    private School school;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
