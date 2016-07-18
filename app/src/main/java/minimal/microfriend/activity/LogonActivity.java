package minimal.microfriend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import minimal.microfriend.R;
import minimal.microfriend.base.BaseActivity;
import minimal.microfriend.utils.MicroTools;

/**
 * Created by gno on 16/6/25.
 */
public class LogonActivity extends BaseActivity implements View.OnClickListener {
    private Button b_logon_next;
    private Intent intent;
    private EditText et_user_number,et_user_password;
    private RadioButton rb_boy,rb_girl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon_main);
        initView();
        MicroTools.toast(this,getUser().getSchool().getSname()+""+getUser().getMajor().getMname());
        intent = new Intent(LogonActivity.this,minimal.microfriend.MainActivity.class);
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
        rb_boy = (RadioButton) findViewById(R.id.rb_boy);
        rb_girl = (RadioButton) findViewById(R.id.rb_girl);
        et_user_number = (EditText) findViewById(R.id.et_student_number);
        et_user_password = (EditText) findViewById(R.id.et_student_password);
        b_logon_next = (Button) findViewById(R.id.b_logon_next);
        b_logon_next.setOnClickListener(this);
    }
}
