package minimal.microfriend.entry;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by gno on 16-6-4.
 */
public class School extends BmobObject {
    private String sname;//学校名称

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }
}
