package minimal.microfriend;

import minimal.microfriend.R;
import minimal.microfriend.fragment.FrontFragment;
import minimal.microfriend.fragment.LeftFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends FragmentActivity {
    private static final String FRONT_FT = "FRONT_FT";
    private static final String LEFT_FT = "LEFT_FT";
    //git
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
    }

    private void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_left, new LeftFragment(), LEFT_FT);
        transaction.replace(R.id.fl_main, new FrontFragment(), FRONT_FT);
        transaction.commit();
    }
}