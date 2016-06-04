package minimal.microfriend.entry;

import cn.bmob.v3.BmobObject;

/**
 * Created by gno on 16-6-4.
 */
public class Major extends BmobObject{
    private String mname;//主修名称
    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }
}
