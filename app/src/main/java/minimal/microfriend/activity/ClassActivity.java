package minimal.microfriend.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import minimal.microfriend.R;
import minimal.microfriend.adapter.WeekAdapter;
import minimal.microfriend.base.BaseActivity;
import minimal.microfriend.entry.OwnerClass;
import minimal.microfriend.entry.StuClass;
import minimal.microfriend.utils.MicroTools;

/**
 * Created by gno on 16/8/2.
 */
public class ClassActivity extends BaseActivity implements View.OnClickListener {
    private ListView lv_week;
    private WeekAdapter mWeekAdapter;
    private OwnerClass myclass;
    private Button b_class_refrensh, b_class_close;
    private File classFile;
    private LinearLayout ll_parent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_class);
        initView();
        initData();
    }

    @Override
    public void initView() {
        ll_parent = (LinearLayout) findViewById(R.id.ll_parent);
        lv_week = (ListView) findViewById(R.id.lv_week);
        b_class_refrensh = (Button) findViewById(R.id.b_class_refrensh);
        b_class_close = (Button) findViewById(R.id.b_class_close);
        b_class_refrensh.setOnClickListener(this);
        b_class_close.setOnClickListener(this);
    }

    @Override
    public void initData() {
        user = (minimal.microfriend.entry.User) getIntent().getSerializableExtra("user");
        classFile = new File(getCacheDir() + "/bmob/ownerClass.json");
        if (classFile.exists()) {
            //解析本地课表json
            classFileAnalysis(MicroTools.fileToString(classFile));
        } else {
            pullClass(classFile);
        }
        lv_week.setOverScrollMode(View.OVER_SCROLL_NEVER);//去除阴影
        lv_week.setVerticalScrollBarEnabled(false);//隐藏滚动条
    }

    private void pullClass(final File classFile) {
        //下载网络课表json
        BmobQuery<StuClass> bmobQuery = new BmobQuery<StuClass>();
        bmobQuery.include("major");
        bmobQuery.include("classJson");
        bmobQuery.addWhereEqualTo("level", user.getLevel());
        bmobQuery.addWhereEqualTo("major", user.getMajor());
        bmobQuery.findObjects(ClassActivity.this, new FindListener<StuClass>() {
            @Override
            public void onSuccess(List<StuClass> list) {
                if (list.size() == 0) {
                    //TODO:服务器中不存在课程表 提示用户手动添加
                    BmobQuery<StuClass> Query = new BmobQuery<StuClass>();
                    Query.include("major");
                    Query.include("classJson");
                    Query.addWhereEqualTo("level", 9);
                    Query.findObjects(ClassActivity.this, new FindListener<StuClass>() {
                        @Override
                        public void onSuccess(List<StuClass> list) {
                            downLoadClass(list,classFile);
                        }

                        @Override
                        public void onError(int i, String s) {
                            MicroTools.toast(ClassActivity.this, "网络似乎有点问题!");
                        }
                    });
                    MicroTools.toast(ClassActivity.this,"暂无课表");
                } else {
                    downLoadClass(list, classFile);

                }
            }

            @Override
            public void onError(int i, String s) {
                MicroTools.toast(ClassActivity.this, "网络似乎有点问题!");
            }
        });
    }

    private void downLoadClass(List<StuClass> list, final File classFile) {
        //下载json文件
        list.get(0).getClassJson().download(ClassActivity.this, new DownloadFileListener() {
            @Override
            public void onSuccess(String s) {
                MicroTools.fileCopy(new File(s), classFile);
                //解析本地课表json
                classFileAnalysis(MicroTools.fileToString(classFile));
            }

            @Override
            public void onFailure(int i, String s) {
                MicroTools.toast(ClassActivity.this, "网络似乎有点问题!");
            }
        });
    }

    /**
     * 课程表解析
     *
     * @param s
     */
    private void classFileAnalysis(String s) {
        Gson gson = new Gson();
        myclass = gson.fromJson(s, OwnerClass.class);
        mWeekAdapter = new WeekAdapter(ClassActivity.this, myclass,ll_parent);
        lv_week.setAdapter(mWeekAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_class_close:
                finish();
                break;
            case R.id.b_class_refrensh:
                classFile = new File(getCacheDir() + "/bmob/ownerClass.json");
                pullClass(classFile);
                break;
        }
    }
}
