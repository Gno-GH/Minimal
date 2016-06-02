package minimal.microfriend.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import minimal.microfriend.base.BaseTabPager;

/**
 * Created by gno on 16-5-29.
 */
public class ContextPagerAdapter extends PagerAdapter {
    private ArrayList<BaseTabPager> pagers;

    public ContextPagerAdapter(ArrayList<BaseTabPager> pagers) {
        this.pagers = pagers;
    }

    @Override
    public int getCount() {
        return pagers.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(pagers.get(position).getRootView());
        return pagers.get(position).getRootView();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
