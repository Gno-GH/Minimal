package minimal.microfriend.centerpager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import minimal.microfriend.base.BaseTabPager;

/**
 * Created by gno on 16-5-28.
 */
public class MicroPager extends BaseTabPager {
    public MicroPager(Context context) {
        super(context);
    }
    @Override
    public void initData(){
        TextView tv = new TextView(this.context);
        tv.setText("查找");
        tv.setTextSize(25);
        tv.setTextColor(Color.RED);
        tv.setGravity(Gravity.CENTER);
        frameLayout.addView(tv);
    }
}

