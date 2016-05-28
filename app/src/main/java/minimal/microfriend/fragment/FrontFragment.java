package minimal.microfriend.fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import minimal.microfriend.R;
import minimal.microfriend.base.BaseFragment;
public class FrontFragment extends BaseFragment {
	private ViewPager front_vp;
	@Override
	public View iniView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.fragment_front, null);
		front_vp = (ViewPager) view.findViewById(R.id.front_vp);
		return view;
	}
	@Override
	public void initData() {

	}

}
