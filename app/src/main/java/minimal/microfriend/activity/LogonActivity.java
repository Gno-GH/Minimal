package minimal.microfriend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import minimal.microfriend.R;
import minimal.microfriend.base.BaseActivity;
import minimal.microfriend.entry.UserID;
import minimal.microfriend.utils.MicroTools;
import minimal.microfriend.utils.SharePrefences;

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
        user = (minimal.microfriend.entry.User) getIntent().getSerializableExtra("user");
        MicroTools.toast(this,user.getSchool().getSname()+""+user.getMajor().getMname());
        intent = new Intent(LogonActivity.this,minimal.microfriend.MainActivity.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_logon_next:
                //检测输入合法
                final String usernumber = et_user_number.getText().toString().trim();
                final String userpassword = et_user_password.getText().toString().trim();
                if(usernumber.equals("")||userpassword.equals("")) {
                    MicroTools.toast(this, "学号或密码不能为空");
                    et_user_password.setText("");
                }
                else if (userpassword.length()<6){
                    MicroTools.toast(this, "密码长度小于六位");
                    et_user_password.setText("");
                }else {
                    //学号是否存在
                    BmobQuery<UserID> userIDBmobQuery = new BmobQuery<UserID>();
                    userIDBmobQuery.include("school");
                    userIDBmobQuery.addWhereEqualTo("school",user.getSchool());
                    userIDBmobQuery.addWhereEqualTo("userid",usernumber);
                    userIDBmobQuery.findObjects(this, new FindListener<UserID>() {
                        @Override
                        public void onSuccess(List<UserID> list) {
                            if(list.size()==1) {
                                if(list.get(0).getBool().equals("1"))MicroTools.toast(LogonActivity.this,"学号已被注册");
                                else {
                                    list.get(0).setValue("bool","1");
                                    list.get(0).update(LogonActivity.this, new UpdateListener() {
                                        @Override
                                        public void onSuccess() {
                                            //执行注册
                                            if(rb_boy.isChecked())user.setSex("男");
                                            else user.setSex("女");
                                            user.setUsername(usernumber);
                                            user.setPassword(MicroTools.MD5(userpassword));
                                            user.signUp(LogonActivity.this, new SaveListener() {
                                                @Override
                                                public void onSuccess() {
                                                    //保存信息
                                                    SharePrefences.setBoolbean(LogonActivity.this,LOGINED,true);
                                                    SharePrefences.setString(LogonActivity.this,USERPASSWORD, MicroTools.MD5(userpassword));
                                                    SharePrefences.setString(LogonActivity.this,USERNAME, usernumber);
                                                    intent.putExtra("user",user);
                                                    startActivity(intent);
                                                    LogonActivity.this.finish();
                                                }

                                                @Override
                                                public void onFailure(int i, String s) {
                                                    MicroTools.toast(LogonActivity.this,"注册失败"+s);
                                                }
                                            });
                                        }

                                        @Override
                                        public void onFailure(int i, String s) {
                                            MicroTools.toast(LogonActivity.this,"注册失败"+s);
                                        }
                                    });
                                }
                            }
                            else MicroTools.toast(LogonActivity.this,"学号不存在");
                        }

                        @Override
                        public void onError(int i, String s) {
                            MicroTools.toast(LogonActivity.this,"服务器错误"+s);
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void initView() {
        rb_boy = (RadioButton) findViewById(R.id.rb_boy);
        rb_girl = (RadioButton) findViewById(R.id.rb_girl);
        et_user_number = (EditText) findViewById(R.id.et_user_number);
        et_user_password = (EditText) findViewById(R.id.et_user_password);
        b_logon_next = (Button) findViewById(R.id.b_logon_next);
        b_logon_next.setOnClickListener(this);
    }
}
