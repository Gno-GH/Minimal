package minimal.microfriend.pager;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import minimal.microfriend.adapter.TrendsAdapter;
import minimal.microfriend.base.BaseTabPager;
import minimal.microfriend.entry.Reply;
import minimal.microfriend.entry.Trend;
import minimal.microfriend.entry.User;

/**
 * Created by gno on 16-5-28.
 */
public class CenterPager extends BaseTabPager{
    private ListView trend_lv;
    private ArrayList<Trend> trends;
    public CenterPager(Context context) {
        super(context);
    }
    @Override
    public void initData(){
        initTrends();
        if(isaddview){
            trend_lv = new ListView(context);
            trend_lv.setAdapter(new TrendsAdapter(this.context,trends));
//            trend_lv.setOverScrollMode(View.OVER_SCROLL_NEVER);//去除阴影
            trend_lv.setVerticalScrollBarEnabled(false);//隐藏滚动条
            linearLayout.addView(trend_lv);
        }
    }

    private void initTrends() {
        trends = new ArrayList<Trend>();
        for (int i = 0; i < 5; i++) {
            ArrayList<Reply> replies = new ArrayList<Reply>();
            for (int j = 0; j < 5; j++) {
                User observer = new User(j + 10000, "tutu " + j);
                User responder = new User(j + 5220, " utut :" + j);
                Reply reply = new Reply(observer, responder, "你好啊！Hellow!");
                replies.add(reply);
            }
            Trend trend = new Trend("这是内容文本", "", replies);
            trends.add(trend);
        }
    }
}
