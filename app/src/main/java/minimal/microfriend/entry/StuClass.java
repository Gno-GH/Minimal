package minimal.microfriend.entry;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by gno on 16/8/10.
 */
public class StuClass extends BmobObject {
    private Major major;
    private BmobFile classJson;
    private Integer level;

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public BmobFile getClassJson() {
        return classJson;
    }

    public void setClassJson(BmobFile classJson) {
        this.classJson = classJson;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
