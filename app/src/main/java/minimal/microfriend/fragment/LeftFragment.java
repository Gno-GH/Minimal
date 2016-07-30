package minimal.microfriend.fragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import minimal.microfriend.R;
import minimal.microfriend.base.BaseFragment;
import minimal.microfriend.entry.User;

public class LeftFragment extends BaseFragment {
    private ImageView iv_userimg;
    private TextView tv_petname, tv_number;

    @SuppressLint("InflateParams")
    @Override
    public View iniView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_left, null);
        iv_userimg = (ImageView) view.findViewById(R.id.iv_userimg);
        tv_number = (TextView) view.findViewById(R.id.tv_number);
        tv_petname = (TextView) view.findViewById(R.id.tv_petname);
        return view;
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("user");
            if (user.getPetname()!=null)
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
}
