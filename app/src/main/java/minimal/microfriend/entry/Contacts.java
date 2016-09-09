package minimal.microfriend.entry;

import cn.bmob.v3.BmobObject;

/**
 * Created by gno on 16/9/8.
 */
public class Contacts extends BmobObject{
    private User Owner;
    private User Friend;
    private Integer dividi;

    public User getOwner() {
        return Owner;
    }

    public void setOwner(User owner) {
        Owner = owner;
    }

    public User getFriend() {
        return Friend;
    }

    public void setFriend(User friend) {
        Friend = friend;
    }

    public Integer getDividi() {
        return dividi;
    }

    public void setDividi(Integer dividi) {
        this.dividi = dividi;
    }
}
