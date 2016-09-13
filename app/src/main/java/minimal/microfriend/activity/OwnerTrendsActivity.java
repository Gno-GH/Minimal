package minimal.microfriend.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import minimal.microfriend.R;
import minimal.microfriend.adapter.TrendsAdapter;
import minimal.microfriend.base.BaseActivity;
import minimal.microfriend.entry.Reply;
import minimal.microfriend.entry.Trend;
import minimal.microfriend.utils.ListTable;
import minimal.microfriend.utils.MicroTools;
import minimal.microfriend.view.RefrenshListView;

/**
 * Created by gno on 16/8/14.
 */
public class OwnerTrendsActivity extends BaseActivity implements View.OnClickListener {
    private RefrenshListView rl_trends;
    private Button b_close;
    private ListTable allreplies;
    private ArrayList<Reply> replies;
    private ArrayList<Trend> mTrends;
    private TrendsAdapter madapter;
    private int index = 0;
    private int postion = 0;
    private boolean mQuerySuccess = false;
    private LinearLayout ll_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownertrends);
        initView();
        initData();
    }

    @Override
    public void initView() {
        rl_trends = (RefrenshListView) findViewById(R.id.rl_trends);
        b_close = (Button) findViewById(R.id.b_close);
        b_close.setOnClickListener(this);
        ll_root = (LinearLayout) findViewById(R.id.ll_root);
    }

    @Override
    public void initData() {
        user = (minimal.microfriend.entry.User) getIntent().getSerializableExtra("user");
        if (allreplies == null) {
            allreplies = new ListTable();
            mTrends = new ArrayList<Trend>();
            rl_trends.setDividerHeight(0);
            rl_trends.setOverScrollMode(View.OVER_SCROLL_NEVER);//去除阴影
            rl_trends.setVerticalScrollBarEnabled(false);//隐藏滚动条
        }
        if (!mQuerySuccess)
            initTrends();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_close:
                finish();
                break;
        }
    }

    private void initTrends() {
        //初始化
        BmobQuery<Trend> queryTrend = new BmobQuery<Trend>();//查询
        queryTrend.include("createUser");
        queryTrend.order("-createdAt");
        queryTrend.setLimit(10);
        queryTrend.setSkip(0);
        queryTrend.addWhereEqualTo("createUser",user);
        queryTrend.findObjects(OwnerTrendsActivity.this, new FindListener<Trend>() {
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
                MicroTools.toast(OwnerTrendsActivity.this, "查询失败" + i);
            }
        });
    }

    private void queryReply() {
        final BmobQuery<Reply> queryReply = new BmobQuery<Reply>();
        queryReply.include("trend");
        queryReply.include("receiver,observer");
        queryReply.order("createdAt");
        queryReply.addWhereEqualTo("trend", mTrends.get(postion));
        queryReply.findObjects(OwnerTrendsActivity.this, new FindListener<Reply>() {
            @Override
            public void onSuccess(List<Reply> list) {
                if (postion < index + 1) {
                    replies = new ArrayList<Reply>();
                    replies = (ArrayList<Reply>) list;
                    allreplies.add(mTrends.get(postion), replies);
                    if (postion == index) {
                        madapter = new TrendsAdapter(OwnerTrendsActivity.this, allreplies, user, ll_root,null,OwnerTrendsActivity.this,1);
                        rl_trends.setAdapter(madapter);
                        postion = 0;
                        rl_trends.setOnRefrenshListener(new RefrenshListView.OnRefrenshListener() {
                            @Override
                            public void onReFrensh() {
                                //查询数据
                                replyRefrensh();
                            }

                            @Override
                            public void loadMore() {

                            }
                        });
                        mQuerySuccess = true;
                        return;
                    }
                    postion++;
                    queryReply();
                }
            }

            @Override
            public void onError(int i, String s) {
                mQuerySuccess = false;
                MicroTools.toast(OwnerTrendsActivity.this, "查询失败" + i);
            }
        });
    }

    public void replyRefrensh() {
        allreplies = new ListTable();
        mTrends = new ArrayList<Trend>();
        BmobQuery<Trend> queryTrend = new BmobQuery<Trend>();//查询
        queryTrend.include("createUser");
        queryTrend.order("-createdAt");
        queryTrend.setLimit(10);
        queryTrend.setSkip(0);
        queryTrend.addWhereEqualTo("createUser",user);
        queryTrend.findObjects(OwnerTrendsActivity.this, new FindListener<Trend>() {
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
                MicroTools.toast(OwnerTrendsActivity.this, "查询失败" + i);
            }
        });
    }

    private void queryReplyRefrensh() {
        final BmobQuery<Reply> queryReply = new BmobQuery<Reply>();
        queryReply.include("trend");
        queryReply.include("receiver,observer");
        queryReply.order("createdAt");
        queryReply.addWhereEqualTo("trend", mTrends.get(postion));
        queryReply.findObjects(OwnerTrendsActivity.this, new FindListener<Reply>() {
            @Override
            public void onSuccess(List<Reply> list) {
                if (postion < index + 1) {
                    replies = new ArrayList<Reply>();
                    replies = (ArrayList<Reply>) list;
                    allreplies.add(mTrends.get(postion), replies);
                    if (postion == index) {
                        madapter = new TrendsAdapter(OwnerTrendsActivity.this, allreplies, user, ll_root,null,OwnerTrendsActivity.this,1);
                        rl_trends.setAdapter(madapter);
                        rl_trends.onRefrenshComplete();
                        postion = 0;
                        rl_trends.setSelection(0);
                        return;
                    }
                    postion++;
                    queryReplyRefrensh();
                }
            }

            @Override
            public void onError(int i, String s) {
                rl_trends.onRefrenshComplete();
                MicroTools.toast(OwnerTrendsActivity.this, "查询失败" + i);
            }
        });
    }

}
