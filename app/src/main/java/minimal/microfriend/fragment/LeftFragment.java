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
    private ImageView iv_class, iv_diary, iv_interest, iv_join,iv_user_level;
    private Intent mIntent;
    private int[] levelImg = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five};
    public LeftFragment(MinimalLayout minimalLayout){
        super();
        this.minimalLayout = minimalLayout;
    }

    @Override
    public View iniView(LayoutInflater inflater) {
        //侧边栏view
        View view = inflater.inflate(R.layout.fragment_left, null);
        iv_user_level = (ImageView) view.findViewById(R.id.iv_user_level);
        iv_userimg = (ImageView) view.findViewById(R.id.iv_userimg);
        tv_number = (TextView) view.findViewById(R.id.tv_number);
        tv_petname = (TextView) view.findViewById(R.id.tv_petname);
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
        setLevel(17 - Integer.parseInt(user.getUsername().substring(0, 2)));
    }

    private void setLevel(int i) {
        iv_user_level.setBackgroundResource(levelImg[i-1]);
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
}
