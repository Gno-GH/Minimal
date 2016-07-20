package minimal.microfriend.fragment;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import minimal.microfriend.R;
import minimal.microfriend.base.BaseFragment;
import minimal.microfriend.entry.User;

public class LeftFragment extends BaseFragment {

	@SuppressLint("InflateParams")
	@Override
	public View iniView(LayoutInflater inflater) {
		View view =inflater.inflate(R.layout.fragment_left, null);
		return view;
	}
	@Override
	public void initData() {
		if(getArguments()!=null) {
			user = (User) getArguments().getSerializable("user");
		}
		Log.d("ABC",user.getUsername());
	}

	@Override
	public User initUser() {
		if(getArguments()!=null) {
			return (User) getArguments().getSerializable("user");
		}
		else  return new User();
	}
}
