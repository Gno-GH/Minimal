package minimal.microfriend.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.util.ArrayList;
import minimal.microfriend.R;
import minimal.microfriend.adapter.ContextPagerAdapter;
import minimal.microfriend.base.BaseFragment;
import minimal.microfriend.base.BaseTabPager;
import minimal.microfriend.centerpager.CenterPager;
import minimal.microfriend.centerpager.MessagePager;
import minimal.microfriend.centerpager.MicroPager;
import minimal.microfriend.view.NoScrollViewPager;

public class FrontFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    private NoScrollViewPager front_vp;
    private ArrayList<BaseTabPager> pagers;
    private RadioGroup front_rg;

    @Override
    public View iniView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_front, null);
        front_vp = (NoScrollViewPager) view.findViewById(R.id.front_vp);
        front_rg = (RadioGroup) view.findViewById(R.id.front_rg);
        return view;
    }

    @Override
    public void initData() {
        pagers = new ArrayList<BaseTabPager>();
        pagers.add(new MessagePager(this.activity));
        pagers.add(new CenterPager(this.activity));
        pagers.add(new MicroPager(this.activity));
        front_vp.setAdapter(new ContextPagerAdapter(pagers));
        front_rg.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_mes:
                front_vp.setCurrentItem(0,false);
                break;
            case R.id.radio_center:
                front_vp.setCurrentItem(1,false);
                break;
            case R.id.radio_micro:
                front_vp.setCurrentItem(2,false);
                break;
        }
    }
}
