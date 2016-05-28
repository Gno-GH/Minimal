package minimal.microfriend.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import minimal.microfriend.R;

/**
 * Created by gno on 16-5-28.
 */
public class BaseTabPager {
    public Context context;
    public FrameLayout frameLayout;
    private View rootView;

    public BaseTabPager(Context context) {
        this.context = context;
        rootView = initView();
    }

    private View initView() {
        View view = View.inflate(this.context, R.layout.tab_basepager, null);
        frameLayout = (FrameLayout) view.findViewById(R.id.tab_base_fl);
        return view;
    }

    public View getRootView() {
        return rootView;
    }

    public void initData() {

    }
}
