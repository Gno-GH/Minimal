package minimal.microfriend.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import minimal.microfriend.R;
import minimal.microfriend.base.BaseActivity;

/**
 * Created by gno on 16/8/2.
 */
public class MeansActivity extends BaseActivity implements View.OnClickListener {
    //TODO: 个人资料修改待完善
    private GridView gd_mood;
    private MoodAdapter mMoodAdapter;
    private String[] mStrings = {"看电视", "玩手机", "游泳", "玩游戏", "听歌"};
    private Button b_means_close, b_means_edit;

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
        b_means_close = (Button) findViewById(R.id.b_means_close);
        b_means_edit = (Button) findViewById(R.id.b_means_edit);
        b_means_edit.setOnClickListener(this);
        b_means_close.setOnClickListener(this);
    }

    @Override
    public void initData() {
        user = (minimal.microfriend.entry.User) getIntent().getSerializableExtra("user");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_means_close:
                finish();
                break;
            case R.id.b_means_edit:
                break;
        }
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
