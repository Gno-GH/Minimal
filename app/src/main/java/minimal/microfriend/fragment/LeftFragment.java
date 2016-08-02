package minimal.microfriend.fragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import minimal.microfriend.R;
import minimal.microfriend.base.BaseFragment;
import minimal.microfriend.entry.User;
import minimal.microfriend.utils.MicroTools;

public class LeftFragment extends BaseFragment implements View.OnClickListener {
    private ImageView iv_userimg;
    private TextView tv_petname, tv_number;
    private TextView tv_class, tv_diary, tv_interest, tv_join;
    private ImageView iv_class, iv_diary, iv_interest, iv_join;

    @SuppressLint("InflateParams")
    @Override
    public View iniView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_left, null);
        iv_userimg = (ImageView) view.findViewById(R.id.iv_userimg);
        tv_number = (TextView) view.findViewById(R.id.tv_number);
        tv_petname = (TextView) view.findViewById(R.id.tv_petname);

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
        }
    }

    private void myClass() {
        MicroTools.toast(activity,"CLASS");
    }

    private void myInterest() {
        MicroTools.toast(activity,"INTEREST");
    }

    private void myJoin() {
        MicroTools.toast(activity,"JOIN");
    }

    private void myDiary() {
        MicroTools.toast(activity,"DIARY");
    }
}
