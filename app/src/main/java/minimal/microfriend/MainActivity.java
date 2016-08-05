package minimal.microfriend;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import minimal.microfriend.entry.User;
import minimal.microfriend.fragment.FrontFragment;
import minimal.microfriend.fragment.LeftFragment;
import minimal.microfriend.utils.MicroTools;
import minimal.microfriend.view.MinimalLayout;

public class MainActivity extends FragmentActivity {

    private MinimalLayout mini_layout;
    public User user;
    private LeftFragment mleft;
    private FrontFragment mfront;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (User) getIntent().getSerializableExtra("user");
        MicroTools.toast(MainActivity.this,"登录成功");
        mini_layout = (MinimalLayout) findViewById(R.id.mini_layout);
        initFragment();
    }

    private void initFragment() {
        mfront = new FrontFragment(mini_layout);
        mleft = new LeftFragment(mini_layout);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",user);
        mleft.setArguments(bundle);
        mfront.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_left,mleft);
        transaction.replace(R.id.fl_main, mfront);
        transaction.commit();
    }

    public FrontFragment getFrontFragment() {
        return mfront;
    }

    public LeftFragment getLeftFragment() {
        return mleft;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mini_layout.derail();
    }
}