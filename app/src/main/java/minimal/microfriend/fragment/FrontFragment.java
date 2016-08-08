package minimal.microfriend.fragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import minimal.microfriend.R;
import minimal.microfriend.adapter.ContextPagerAdapter;
import minimal.microfriend.base.BaseFragment;
import minimal.microfriend.base.BaseTabPager;
import minimal.microfriend.entry.User;
import minimal.microfriend.pager.CenterPager;
import minimal.microfriend.pager.MessagePager;
import minimal.microfriend.pager.MicroPager;
import minimal.microfriend.utils.MicroTools;
import minimal.microfriend.view.DragLinearLayout;
import minimal.microfriend.view.MinimalLayout;
import minimal.microfriend.view.NoScrollViewPager;

@SuppressLint("ValidFragment")
public class FrontFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, View.OnLongClickListener {
    private NoScrollViewPager front_vp;
    private ArrayList<BaseTabPager> pagers;
    private RadioGroup front_rg;
    private MinimalLayout minimalLayout;
    private DragLinearLayout dragLinearLayout;
    private RadioButton radio_center;

    public FrontFragment(MinimalLayout minimalLayout) {
        super();
        this.minimalLayout = minimalLayout;
    }

    @Override
    public View iniView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_front, null);
        front_vp = (NoScrollViewPager) view.findViewById(R.id.front_vp);
        front_rg = (RadioGroup) view.findViewById(R.id.front_rg);
        dragLinearLayout = (DragLinearLayout) view.findViewById(R.id.dl_layout);
        dragLinearLayout.setDragLayout(minimalLayout);
        radio_center = (RadioButton) view.findViewById(R.id.radio_center);
        radio_center.setOnLongClickListener(this);
        return view;
    }

    @Override
    public void initData() {
        pagers = new ArrayList<BaseTabPager>();
        pagers.add(new MessagePager(this.activity,user));
        pagers.add(new CenterPager(this.activity,user));
        pagers.add(new MicroPager(this.activity,user));
        pagers.get(0).initData();
        front_vp.setAdapter(new ContextPagerAdapter(pagers));
        front_rg.setOnCheckedChangeListener(this);
    }

    @Override
    public User initUser() {
        if(getArguments()!=null) {
            return (User) getArguments().getSerializable("user");
        }
        else  return new User();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int index = 0;
        switch (checkedId) {
            case R.id.radio_mes:
                index = 0;
                break;
            case R.id.radio_center:
                index = 1;
                break;
            case R.id.radio_micro:
                index = 2;
                break;
        }
        front_vp.setCurrentItem(index, false);
        pagers.get(index).initData();
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.radio_center:
                if (radio_center.isChecked()) {
                    //TODO:动态发表
                    MicroTools.toast(this.activity,"弹出窗口");
                }
                break;
        }
        return true;
    }
}
