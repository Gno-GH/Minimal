package minimal.microfriend.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import minimal.microfriend.base.BaseTabPager;
import minimal.microfriend.entry.User;

/**
 * Created by gno on 16-5-28.
 */
public class MessagePager extends BaseTabPager{
    private TextView tv;
    public MessagePager(Context context, User user) {
        super(context);
        this.user = user;
    }
    @Override
    public void initData(){
        if(isaddview) {
            title_tv.setText("消息栏");
            tv = new TextView(this.context);
            tv.setText("消息");
            tv.setTextSize(25);
            tv.setTextColor(Color.RED);
            tv.setGravity(Gravity.CENTER);
            linearLayout.addView(tv);
            isaddview = false;
        }
    }

}
