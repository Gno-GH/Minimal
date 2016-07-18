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
    private static final String FRONT_FT = "FRONT_FT";
    private static final String LEFT_FT = "LEFT_FT";
    private MinimalLayout mini_layout;
    public User user;
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
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_left, new LeftFragment(), LEFT_FT);
        transaction.replace(R.id.fl_main, new FrontFragment(mini_layout), FRONT_FT);
        transaction.commit();
    }

    public FrontFragment getFrontFragment() {
        FragmentManager manager = getSupportFragmentManager();
        return (FrontFragment) manager.findFragmentByTag("FRONT_FT");
    }

    public LeftFragment getLeftFragment() {
        FragmentManager manager = getSupportFragmentManager();
        return (LeftFragment) manager.findFragmentByTag("LEFT_FT");
    }
}