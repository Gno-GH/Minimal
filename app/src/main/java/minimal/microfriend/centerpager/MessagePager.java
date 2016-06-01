package minimal.microfriend.centerpager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import minimal.microfriend.base.BaseTabPager;

/**
 * Created by gno on 16-5-28.
 */
public class MessagePager extends BaseTabPager{
    private TextView tv;
    public MessagePager(Context context) {
        super(context);
    }
    @Override
    public void initData(){
        if(isaddview) {
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
