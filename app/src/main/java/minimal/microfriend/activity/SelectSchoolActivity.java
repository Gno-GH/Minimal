package minimal.microfriend.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import minimal.microfriend.R;
import minimal.microfriend.adapter.DepartAdapter;
import minimal.microfriend.adapter.MajorAdapter;
import minimal.microfriend.base.BaseActivity;
import minimal.microfriend.entry.Depart;
import minimal.microfriend.entry.Major;
import minimal.microfriend.entry.School;
import minimal.microfriend.entry.User;
import minimal.microfriend.utils.MicroTools;

/**
 * Created by gno on 16/6/22.
 */
public class SelectSchoolActivity extends BaseActivity{
    private ArrayList<Depart> departs;
    private ArrayList<Major> majors;
    private EditText et_search;
    private ListView lv_depart;
    private MajorAdapter mMajorAdapter;
    private DepartAdapter mDepartAdapter;
    private PopupWindow mojarWindow;
    private View view;
    private LinearLayout logon;
    private RelativeLayout rl_repart;
    private ListView lv_major;
    private School mSchool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon_selectschool);
        Bmob.initialize(SelectSchoolActivity.this,"1fd27bc83141c867dc42b8f5b1d91c1e");
        initView();
        initData();
        initPopUpWindow();
        et_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    departs.clear();
                    rl_repart.setVisibility(View.GONE);
                    lv_depart.setVisibility(View.GONE);
                    //查询学校
                    String school = et_search.getText().toString().trim();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_search.getWindowToken(), 0);
                    BmobQuery<School> schoolBmobQuery = new BmobQuery<School>();
                    schoolBmobQuery.addWhereEqualTo("sname",school);
                    schoolBmobQuery.findObjects(SelectSchoolActivity.this, new FindListener<School>() {
                        @Override
                        public void onSuccess(List<School> list) {
                            if(list.size()==1) {
                                mSchool = list.get(0);
                                user = new User();
                                user.setSchool(mSchool);
                                BmobQuery<Depart> departBmobQuery = new BmobQuery<Depart>();
                                departBmobQuery.include("school");
                                departBmobQuery.addWhereEqualTo("school",mSchool);
                                departBmobQuery.findObjects(SelectSchoolActivity.this, new FindListener<Depart>() {
                                    @Override
                                    public void onSuccess(List<Depart> list) {
                                        if(list.size()>0){
                                            rl_repart.setVisibility(View.VISIBLE);
                                            lv_depart.setVisibility(View.VISIBLE);
                                            for (Depart depart:list) {
                                                departs.add(depart);
                                            }
                                            mDepartAdapter.notifyDataSetChanged();
                                        }else
                                            MicroTools.toast(SelectSchoolActivity.this,"服务器错误");
                                    }

                                    @Override
                                    public void onError(int i, String s) {
                                        MicroTools.toast(SelectSchoolActivity.this,"学校未注册"+s);
                                    }
                                });
                            }
                            else MicroTools.toast(SelectSchoolActivity.this,"学校未注册");
                        }

                        @Override
                        public void onError(int i, String s) {
                            MicroTools.toast(SelectSchoolActivity.this,"学校未注册"+s);
                        }
                    });
                    mDepartAdapter.notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });
        lv_depart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Depart depart = departs.get(i);
                user.setDepart(depart);
                BmobQuery<Major> majorBmobQuery = new BmobQuery<Major>();
                majorBmobQuery.include("depart");
                majorBmobQuery.addWhereEqualTo("depart",depart);
                majorBmobQuery.findObjects(SelectSchoolActivity.this, new FindListener<Major>() {
                    @Override
                    public void onSuccess(List<Major> list) {
                        majors.clear();
                        if(list.size()>0) {
                            for (Major major:list) {
                                majors.add(major);
                            }
                            lv_major.setAdapter(mMajorAdapter);
                            mMajorAdapter.notifyDataSetChanged();
                            mojarWindow.showAtLocation(logon, Gravity.CENTER, 0, 0);
                            lv_major.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    user.setMajor(majors.get(i));
                                    intent = new Intent(SelectSchoolActivity.this,LogonActivity.class);
                                    intent.putExtra("user",user);
                                    SelectSchoolActivity.this.startActivity(intent);
                                    mojarWindow.dismiss();
                                    SelectSchoolActivity.this.finish();
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        MicroTools.toast(SelectSchoolActivity.this,"服务器错误");
                    }
                });
            }
        });
    }

    public void initView() {
        et_search = (EditText) findViewById(R.id.et_search);
        lv_depart = (ListView) findViewById(R.id.lv_depart);
        logon = (LinearLayout) findViewById(R.id.logon);
        rl_repart = (RelativeLayout) findViewById(R.id.rl_depart);
    }
    public void initData(){
        mSchool = new School();
        departs = new ArrayList<Depart>();
        majors = new ArrayList<Major>();
        mMajorAdapter = new MajorAdapter(SelectSchoolActivity.this,majors);
        mDepartAdapter = new DepartAdapter(SelectSchoolActivity.this,departs);
        lv_depart.setAdapter(mDepartAdapter);
    }
    private void initPopUpWindow() {
        view = View.inflate(SelectSchoolActivity.this, R.layout.listview_logon_major, null);
        lv_major = (ListView) view.findViewById(R.id.lv_major);
        mojarWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mojarWindow.setBackgroundDrawable(new BitmapDrawable());
        mojarWindow.setOutsideTouchable(true);
    }
}
