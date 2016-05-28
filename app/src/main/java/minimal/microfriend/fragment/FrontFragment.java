package minimal.microfriend.fragment;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import minimal.microfriend.R;
import minimal.microfriend.base.BaseFragment;
public class FrontFragment extends BaseFragment {
	@SuppressLint("InflateParams")
	@Override
	public View iniView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.fragment_front, null);
		return view;
	}
	@Override
	public void initData() {
	}

}
