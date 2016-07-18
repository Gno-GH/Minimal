package minimal.microfriend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import minimal.microfriend.R;
import minimal.microfriend.base.BaseActivity;
import minimal.microfriend.entry.User;
import minimal.microfriend.utils.MicroTools;
import minimal.microfriend.utils.SharePrefences;

public class LoginActivity extends BaseActivity {
    private Button login;
    private Intent intent;
    private TextView tv_logon;
    private EditText et_student_number;
    private EditText et_student_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
    }

    @Override
    public void initView() {
        login = (Button) findViewById(R.id.login);
        tv_logon = (TextView) findViewById(R.id.tv_logon);
        et_student_number = (EditText) findViewById(R.id.et_student_number);
        et_student_password = (EditText) findViewById(R.id.et_student_password);
    }

    @Override
    public void initData() {
        tv_logon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(LoginActivity.this,minimal.microfriend.activity.LogonActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(LoginActivity.this,minimal.microfriend.MainActivity.class);
                //a、检查数据合法
                final String password = et_student_password.getText().toString().trim();
                final String usernumber = et_student_number.getText().toString().trim();
                setUser(new User());
                getUser().setPassword(MicroTools.MD5(password));
                getUser().setUsername(usernumber);
                getUser().login(LoginActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        BmobQuery<minimal.microfriend.entry.User> userquery = new BmobQuery<User>();
                        userquery.include("major");
                        userquery.include("depart");
                        userquery.include("school");
                        userquery.addWhereEqualTo("username",usernumber);
                        userquery.findObjects(LoginActivity.this, new FindListener<minimal.microfriend.entry.User>() {
                            @Override
                            public void onSuccess(List<User> list) {
                                //保存MD5
                                SharePrefences.setBoolbean(LoginActivity.this,LOGINED,true);
                                SharePrefences.setString(LoginActivity.this,USERPASSWORD, MicroTools.MD5(password));
                                SharePrefences.setString(LoginActivity.this,USERNAME, usernumber);
                                intent.putExtra("user",list.get(0));
                                startActivity(intent);
                                LoginActivity.this.finish();
                            }
                            @Override
                            public void onError(int i, String s) {
                                MicroTools.toast(LoginActivity.this,"服务器错误");
                            }
                        });
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        MicroTools.toast(LoginActivity.this,"登录失败");
                    }
                });
                }
            });
    }
}
