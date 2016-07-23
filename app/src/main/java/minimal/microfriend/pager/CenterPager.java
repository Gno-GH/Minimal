package minimal.microfriend.pager;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import minimal.microfriend.adapter.TrendsAdapter;
import minimal.microfriend.base.BaseTabPager;
import minimal.microfriend.entry.Reply;
import minimal.microfriend.entry.Trend;
import minimal.microfriend.entry.User;
import minimal.microfriend.utils.ListTable;
import minimal.microfriend.utils.MicroTools;
import minimal.microfriend.view.RefrenshListView;

/**
 * Created by gno on 16-5-28.
 */
public class CenterPager extends BaseTabPager {
    private RefrenshListView trend_rlv;
    private ListTable allreplies;
    private ArrayList<Reply> replies;
    private ArrayList<Trend> mTrends;
    private TrendsAdapter madapter;
    private int index = 0;
    private int postion = 0;
    private int startX = 0;

    public CenterPager(Context context, User user) {
        super(context);
        this.user = user;
    }

    @Override
    public void initData() {
        if (allreplies == null) {
            trend_rlv = new RefrenshListView(context);
            trend_rlv.setDividerHeight(0);
            trend_rlv.setOverScrollMode(View.OVER_SCROLL_NEVER);//去除阴影
            trend_rlv.setVerticalScrollBarEnabled(false);//隐藏滚动条
            initTrends();
        }
    }

    private void initTrends() {
        //初始化
        allreplies = new ListTable();
        mTrends = new ArrayList<Trend>();
        BmobQuery<Trend> queryTrend = new BmobQuery<Trend>();//查询
        queryTrend.include("createUser");
        queryTrend.order("-updatedAt");
        queryTrend.setLimit(10);
        queryTrend.setSkip(0);
        queryTrend.findObjects(this.context, new FindListener<Trend>() {
            @Override
            public void onSuccess(List<Trend> list) {
                index = list.size() - 1;
                for (Trend t : list) {
                    mTrends.add(t);
                }
                //查询正确
                queryReply();
            }

            @Override
            public void onError(int i, String s) {
                MicroTools.toast(context, "查询失败" + i);
            }
        });
    }

    private void queryReply() {
        final BmobQuery<Reply> queryReply = new BmobQuery<Reply>();
        queryReply.include("trend");
        queryReply.include("receiver,observer");
        queryReply.order("createdAt");
        queryReply.addWhereEqualTo("trend", mTrends.get(postion));
        queryReply.findObjects(context, new FindListener<Reply>() {
            @Override
            public void onSuccess(List<Reply> list) {
                if (postion < index + 1) {
                    replies = new ArrayList<Reply>();
                    replies = (ArrayList<Reply>) list;
                    allreplies.add(mTrends.get(postion), replies);
                    if (postion == index) {
                        madapter = new TrendsAdapter(context, allreplies, user, ll_root);
                        trend_rlv.setAdapter(madapter);
                        postion = 0;
                        linearLayout.addView(trend_rlv);
                        trend_rlv.setOnRefrenshListener(new RefrenshListView.OnRefrenshListener() {
                            @Override
                            public void onReFrensh() {
                                //查询数据
                                replyRefrensh();
                            }

                            @Override
                            public void loadMore() {

                            }
                        });
                        return;
                    }
                    postion++;
                    queryReply();
                }
            }

            @Override
            public void onError(int i, String s) {
                MicroTools.toast(context, "查询失败" + i);
            }
        });
    }

    private void replyRefrensh() {
        allreplies = new ListTable();
        mTrends = new ArrayList<Trend>();
        BmobQuery<Trend> queryTrend = new BmobQuery<Trend>();//查询
        queryTrend.include("createUser");
        queryTrend.order("-updatedAt");
        queryTrend.setLimit(10);
        queryTrend.setSkip(0);
        queryTrend.findObjects(this.context, new FindListener<Trend>() {
            @Override
            public void onSuccess(List<Trend> list) {
                index = list.size() - 1;
                for (Trend t : list) {
                    mTrends.add(t);
                }
                //查询正确
                queryReplyRefrensh();
            }

            @Override
            public void onError(int i, String s) {
                MicroTools.toast(context, "查询失败" + i);
            }
        });
    }

    private void queryReplyRefrensh() {
        final BmobQuery<Reply> queryReply = new BmobQuery<Reply>();
        queryReply.include("trend");
        queryReply.include("receiver,observer");
        queryReply.order("createdAt");
        queryReply.addWhereEqualTo("trend", mTrends.get(postion));
        queryReply.findObjects(context, new FindListener<Reply>() {
            @Override
            public void onSuccess(List<Reply> list) {
                if (postion < index + 1) {
                    replies = new ArrayList<Reply>();
                    replies = (ArrayList<Reply>) list;
                    allreplies.add(mTrends.get(postion), replies);
                    if (postion == index) {
                        madapter = new TrendsAdapter(context, allreplies, user, ll_root);
                        trend_rlv.setAdapter(madapter);
                        trend_rlv.onRefrenshComplete();
                        postion = 0;
                        return;
                    }
                    postion++;
                    queryReplyRefrensh();
                }
            }
            @Override
            public void onError(int i, String s) {
                MicroTools.toast(context, "查询失败" + i);
            }
        });
    }
}