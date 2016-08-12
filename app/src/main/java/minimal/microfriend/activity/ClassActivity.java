package minimal.microfriend.activity;

import android.os.Bundle;
import android.view.View;
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
public class ClassActivity extends BaseActivity {
    private ListView lv_week;
    private WeekAdapter mWeekAdapter;
    private OwnerClass myclass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_class);
        initView();
        initData();
    }

    @Override
    public void initView() {
        lv_week = (ListView) findViewById(R.id.lv_week);
    }

    @Override
    public void initData() {
        user = (minimal.microfriend.entry.User) getIntent().getSerializableExtra("user");
        final File classFile = new File(getCacheDir() + "/bmob/ownerClass.json");
        if (classFile.exists()) {
            //解析本地课表json
            classFileAnalysis(MicroTools.fileToString(classFile));
        } else {
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
                    } else {
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
                }

                @Override
                public void onError(int i, String s) {
                    MicroTools.toast(ClassActivity.this, "网络似乎有点问题!");
                }
            });
            //课表json文件更名
            //解析课表json
        }
        lv_week.setOverScrollMode(View.OVER_SCROLL_NEVER);//去除阴影
        lv_week.setVerticalScrollBarEnabled(false);//隐藏滚动条
    }

    /**
     * 课程表解析
     *
     * @param s
     */
    private void classFileAnalysis(String s) {
        Gson gson = new Gson();
        myclass = gson.fromJson(s, OwnerClass.class);
        mWeekAdapter = new WeekAdapter(ClassActivity.this, myclass);
        lv_week.setAdapter(mWeekAdapter);
    }
}
