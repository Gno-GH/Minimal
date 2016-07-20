package minimal.microfriend.pager;

import android.content.Context;

import minimal.microfriend.base.BaseTabPager;
import minimal.microfriend.entry.User;

/**
 * Created by gno on 16-5-28.
 */
public class MicroPager extends BaseTabPager {
    public MicroPager(Context context, User user) {
        super(context);
        this.user = user;
    }
    @Override
    public void initData(){
    }
}

