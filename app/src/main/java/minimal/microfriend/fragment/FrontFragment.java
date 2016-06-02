package minimal.microfriend.fragment;

import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import java.util.ArrayList;

import minimal.microfriend.R;
import minimal.microfriend.adapter.ContextPagerAdapter;
import minimal.microfriend.base.BaseFragment;
import minimal.microfriend.base.BaseTabPager;
import minimal.microfriend.pager.CenterPager;
import minimal.microfriend.pager.MessagePager;
import minimal.microfriend.pager.MicroPager;
import minimal.microfriend.view.DragLinearLayout;
import minimal.microfriend.view.MinimalLayout;
import minimal.microfriend.view.NoScrollViewPager;

public class FrontFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    private NoScrollViewPager front_vp;
    private ArrayList<BaseTabPager> pagers;
    private RadioGroup front_rg;
    private MinimalLayout minimalLayout;
    private DragLinearLayout dragLinearLayout;
    public FrontFragment(MinimalLayout minimalLayout){
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
        return view;
    }

    @Override
    public void initData() {
        pagers = new ArrayList<BaseTabPager>();
        pagers.add(new MessagePager(this.activity));
        pagers.add(new CenterPager(this.activity));
        pagers.add(new MicroPager(this.activity));
        pagers.get(0).initData();
        front_vp.setAdapter(new ContextPagerAdapter(pagers));
        front_rg.setOnCheckedChangeListener(this);
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
}
