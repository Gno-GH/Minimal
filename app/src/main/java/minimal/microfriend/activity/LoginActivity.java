package minimal.microfriend.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import minimal.microfriend.R;

public class LoginActivity extends Activity {
    private Button login;
    private Intent intent;
    private TextView tv_logon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.login);
        tv_logon = (TextView) findViewById(R.id.tv_logon);
        intent = new Intent(LoginActivity.this,minimal.microfriend.activity.LogonActivity.class);
        tv_logon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
//        intent = new Intent(MainActivity.this,SelectSchoolActivity.class);
//
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(intent);
//            }
//        });
    }
}
