package minimal.microfriend.entry;

import cn.bmob.v3.BmobObject;

/**
 * Created by gno on 16/8/5.
 */
public class Interaction extends BmobObject {
    private Trend trend;
    private User user;
    private Integer type;

    public Trend getTrend() {
        return trend;
    }

    public void setTrend(Trend trend) {
        this.trend = trend;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
