package minimal.microfriend.entry;

import cn.bmob.v3.BmobObject;

/**
 * Created by gno on 16-6-4.
 */
public class Major extends BmobObject{
    private String mname;//主修名称
    private Depart depart;//依赖的院系

    public Depart getDepart() {
        return depart;
    }

    public void setDepart(Depart depart) {
        this.depart = depart;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }
}
