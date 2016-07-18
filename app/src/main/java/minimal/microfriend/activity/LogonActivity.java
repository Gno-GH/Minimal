package minimal.microfriend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import minimal.microfriend.R;
import minimal.microfriend.base.BaseActivity;

/**
 * Created by gno on 16/6/25.
 */
public class LogonActivity extends BaseActivity implements View.OnClickListener {
    private Button b_logon_next;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon_main);
        initView();
        intent = new Intent(LogonActivity.this,minimal.microfriend.activity.SelectSchoolActivity.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_logon_next:
                startActivity(intent);
                break;
        }
    }

    @Override
    public void initView() {
        b_logon_next = (Button) findViewById(R.id.b_logon_next);
        b_logon_next.setOnClickListener(this);
    }
}
