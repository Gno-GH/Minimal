package minimal.microfriend.fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import minimal.microfriend.R;
import minimal.microfriend.activity.ClassActivity;
import minimal.microfriend.activity.DiaryActivity;
import minimal.microfriend.activity.MeansActivity;
import minimal.microfriend.activity.OwnerTrendsActivity;
import minimal.microfriend.base.BaseFragment;
import minimal.microfriend.entry.User;
import minimal.microfriend.utils.MicroTools;
import minimal.microfriend.view.MinimalLayout;

public class LeftFragment extends BaseFragment implements View.OnClickListener, View.OnTouchListener {
    private MinimalLayout minimalLayout;
    private ImageView iv_userimg;
    private TextView tv_petname, tv_number;
    private TextView tv_class, tv_diary, tv_interest, tv_join;
    private ImageView iv_class, iv_diary, iv_interest, iv_join;
    private PopupWindow selectState;
    private View popView;
    private Button b_select_state;
    private ListView lv_state;
    private int[] state = {R.drawable.onclass, R.drawable.free, R.drawable.busy, R.drawable.outline};
    private Intent mIntent;
    public LeftFragment(MinimalLayout minimalLayout){
        super();
        this.minimalLayout = minimalLayout;
    }

    @Override
    public View iniView(LayoutInflater inflater) {
        //状态窗口初始化
        popView = View.inflate(activity, R.layout.pop_select_state, null);
        lv_state = (ListView) popView.findViewById(R.id.lv_state);
        lv_state.setOverScrollMode(View.OVER_SCROLL_NEVER);//去除阴影
        lv_state.setVerticalScrollBarEnabled(false);//隐藏滚动条
        selectState = new PopupWindow(popView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        selectState.setBackgroundDrawable(new BitmapDrawable());
        selectState.setOutsideTouchable(true);

        //侧边栏view
        View view = inflater.inflate(R.layout.fragment_left, null);
        iv_userimg = (ImageView) view.findViewById(R.id.iv_userimg);
        tv_number = (TextView) view.findViewById(R.id.tv_number);
        tv_petname = (TextView) view.findViewById(R.id.tv_petname);
        b_select_state = (Button) view.findViewById(R.id.b_select_state);

        b_select_state.setOnClickListener(this);
        iv_userimg.setOnClickListener(this);
        iv_userimg.setOnTouchListener(this);

        tv_class = (TextView) view.findViewById(R.id.tv_class);
        tv_diary = (TextView) view.findViewById(R.id.tv_diary);
        tv_interest = (TextView) view.findViewById(R.id.tv_interest);
        tv_join = (TextView) view.findViewById(R.id.tv_join);
        iv_class = (ImageView) view.findViewById(R.id.iv_class);
        iv_diary = (ImageView) view.findViewById(R.id.iv_diary);
        iv_interest = (ImageView) view.findViewById(R.id.iv_interest);
        iv_join = (ImageView) view.findViewById(R.id.iv_join);

        tv_class.setOnClickListener(this);
        tv_interest.setOnClickListener(this);
        tv_join.setOnClickListener(this);
        tv_diary.setOnClickListener(this);
        iv_class.setOnClickListener(this);
        iv_interest.setOnClickListener(this);
        iv_join.setOnClickListener(this);
        iv_diary.setOnClickListener(this);

        lv_state.setAdapter(new StateAdapter());
        return view;
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("user");
            if (user.getPetname() != null)
                tv_petname.setText(user.getPetname());
            tv_number.setText(user.getUsername());
        }
    }

    @Override
    public User initUser() {
        if (getArguments() != null) {
            return (User) getArguments().getSerializable("user");
        } else
            return new User();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_class:
            case R.id.iv_class:
                myClass();
                break;
            case R.id.tv_interest:
            case R.id.iv_interest:
                myInterest();
                break;
            case R.id.tv_join:
            case R.id.iv_join:
                myJoin();
                break;
            case R.id.tv_diary:
            case R.id.iv_diary:
                myDiary();
                break;
            case R.id.iv_userimg:
                myMeans();
                break;
            case R.id.b_select_state:
                selectState.showAsDropDown(b_select_state);
                break;
        }
    }
    //TODO:检测资料是否完善
    private void myMeans() {
        mIntent = new Intent(activity, MeansActivity.class);
        mIntent.putExtra("user",user);
        startActivity(mIntent);
    }

    private void myClass() {
        mIntent = new Intent(activity, ClassActivity.class);
        mIntent.putExtra("user",user);
        startActivity(mIntent);
    }

    //TODO: 用户兴趣爱好
    private void myInterest() {
        MicroTools.toast(activity, "INTEREST");
    }

    private void myJoin() {
        mIntent = new Intent(activity, OwnerTrendsActivity.class);
        mIntent.putExtra("user",user);
        startActivity(mIntent);
    }

    private void myDiary() {
        mIntent = new Intent(activity, DiaryActivity.class);
        mIntent.putExtra("user",user);
        startActivity(mIntent);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.iv_userimg:
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        iv_userimg.setAlpha(0.7f);
                        break;
                    case MotionEvent.ACTION_UP:
                        iv_userimg.setAlpha(1.0f);
                        break;
                }
                break;
        }
        return false;
    }

    class StateAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return state.length;
        }

        @Override
        public Object getItem(int i) {
            return state[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = View.inflate(activity, R.layout.listview_state, null);
            ImageButton ib_state = (ImageButton) view.findViewById(R.id.ib_state);
            ib_state.setBackgroundResource(state[i]);
            ib_state.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MicroTools.toast(activity, "" + i);
                    //TODO: 此处设置状态 访问网络并修改状态
                    b_select_state.setBackgroundResource(state[i]);//设置状态
                    selectState.dismiss();//隐藏弹出窗口
                }
            });
            return view;
        }
    }
}
