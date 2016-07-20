package minimal.microfriend.pager;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import minimal.microfriend.base.BaseTabPager;
import minimal.microfriend.entry.Reply;
import minimal.microfriend.entry.Trend;
import minimal.microfriend.entry.User;
import minimal.microfriend.utils.MicroTools;

/**
 * Created by gno on 16-5-28.
 */
public class CenterPager extends BaseTabPager {
    private ListView trend_lv;
    private HashMap<Trend, ArrayList<Reply>> allreplies;
    private ArrayList<Reply> replies;
    public CenterPager(Context context, User user) {
        super(context);
        this.user = user;
    }

    @Override
    public void initData() {
        Log.d("ABC",user.getPetname());
        initTrends();
//        if (isaddview) {
//            trend_lv = new ListView(context);
//            trend_lv.setAdapter(new TrendsAdapter(this.context, allreplies));
////            trend_lv.setOverScrollMode(View.OVER_SCROLL_NEVER);//去除阴影
//            trend_lv.setVerticalScrollBarEnabled(false);//隐藏滚动条
//            linearLayout.addView(trend_lv);
//        }
    }

    private void initTrends() {
        allreplies = new HashMap<Trend,ArrayList<Reply>>();
        BmobQuery<Trend> queryTrend = new BmobQuery<Trend>();//查询
        queryTrend.include("createUser");
        queryTrend.order("-updatedAt");
        queryTrend.setLimit(10);
        queryTrend.setSkip(0);
        queryTrend.findObjects(this.context, new FindListener<Trend>() {
            @Override
            public void onSuccess(List<Trend> list) {
                for (Trend t : list){
                    replies = new ArrayList<Reply>();
                    BmobQuery<Reply> queryReply = new BmobQuery<Reply>();
                    queryReply.include("trend");
                    queryReply.include("observer");
                    queryReply.include("receiver");
                    queryReply.addWhereEqualTo("trend",t);
                    queryReply.findObjects(context, new FindListener<Reply>() {
                        @Override
                        public void onSuccess(List<Reply> list) {
                            replies = (ArrayList<Reply>) list;
                            MicroTools.toast(context,"查询成功");
                        }
                        @Override
                        public void onError(int i, String s) {
                            MicroTools.toast(context,"查询失败"+i);
                        }
                    });
                    allreplies.put(t,replies);
                }
            }
            @Override
            public void onError(int i, String s) {
                MicroTools.toast(context,"查询失败"+i);
            }
        });
    }
}
