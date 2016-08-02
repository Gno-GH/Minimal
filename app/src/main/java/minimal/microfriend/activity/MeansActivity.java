package minimal.microfriend.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import minimal.microfriend.R;
import minimal.microfriend.base.BaseActivity;

/**
 * Created by gno on 16/8/2.
 */
public class MeansActivity extends BaseActivity {
    private GridView gd_mood;
    private MoodAdapter mMoodAdapter;
    private String[] mStrings = {"看电视", "玩手机", "游泳", "玩游戏", "听歌"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_means);
        initView();
        initData();
    }

    @Override
    public void initView() {
        mMoodAdapter = new MoodAdapter();
        gd_mood = (GridView) findViewById(R.id.gd_mood);
        gd_mood.setAdapter(mMoodAdapter);
    }

    @Override
    public void initData() {
        user = (minimal.microfriend.entry.User) getIntent().getSerializableExtra("user");
    }

    class MoodAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mStrings.length;
        }

        @Override
        public Object getItem(int i) {
            return mStrings[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = View.inflate(MeansActivity.this, R.layout.gridview_mood, null);
            TextView tv_mood = (TextView) view.findViewById(R.id.tv_mood);
            tv_mood.setText(mStrings[i]);
            return view;
        }
    }


}
