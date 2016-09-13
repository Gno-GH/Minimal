package minimal.microfriend.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import minimal.microfriend.R;
import minimal.microfriend.base.BaseActivity;
import minimal.microfriend.fragment.LeftFragment;
import minimal.microfriend.utils.MicroTools;
import minimal.microfriend.view.CricularView;

/**
 * Created by gno on 16/8/2.
 */
public class MeansActivity extends BaseActivity implements View.OnClickListener {
    //TODO: 个人资料修改待完善
    private GridView gd_mood;
    private CricularView cv_userimg;
    private TextView tv_age, tv_depart;
    private ImageView tv_sex;
    private MoodAdapter mMoodAdapter;
    private String[] mStrings = {"看电视", "玩手机", "游泳", "玩游戏", "听歌"};
    private Button b_means_close, b_means_edit;
    private static File img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_means);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void initView() {
        mMoodAdapter = new MoodAdapter();
        gd_mood = (GridView) findViewById(R.id.gd_mood);
        gd_mood.setAdapter(mMoodAdapter);
        cv_userimg = (CricularView) findViewById(R.id.cv_userimg);
        cv_userimg.setOnClickListener(this);
        tv_age = (TextView) findViewById(R.id.tv_age);
        tv_depart = (TextView) findViewById(R.id.tv_depart);
        tv_sex = (ImageView) findViewById(R.id.tv_sex);
        b_means_close = (Button) findViewById(R.id.b_means_close);
        b_means_edit = (Button) findViewById(R.id.b_means_edit);
        b_means_edit.setOnClickListener(this);
        b_means_close.setOnClickListener(this);
    }

    @Override
    public void initData() {
        user = (minimal.microfriend.entry.User) getIntent().getSerializableExtra("user");
        if (img == null)
            img = (File) getIntent().getSerializableExtra("img");
        if (user.getUserphoto() != null && img != null)
            cv_userimg.setImageBitmap(BitmapFactory.decodeFile(img.getPath()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_means_close:
                finish();
                break;
            case R.id.b_means_edit:
                break;
            case R.id.cv_userimg:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            cv_userimg.setImageURI(data.getData());
            img = MicroTools.uriToFile(data.getData(), MeansActivity.this, user);
            BmobFile bimg = new BmobFile(img);
            user.setUserphoto(bimg);
            user.getUserphoto().upload(MeansActivity.this, new UploadFileListener() {
                @Override
                public void onSuccess() {
                    user.update(MeansActivity.this, new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            MicroTools.toast(MeansActivity.this, "SUCCESS");
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            MicroTools.toast(MeansActivity.this, s);
                        }
                    });
                }

                @Override
                public void onFailure(int i, String s) {
                    MicroTools.toast(MeansActivity.this, s);
                }
            });

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    class MoodAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mStrings.length;
        }

        @Override
        public Object getItem(int i) {
            return mStrings[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = View.inflate(MeansActivity.this, R.layout.gridview_mood, null);
            TextView tv_mood = (TextView) view.findViewById(R.id.tv_mood);
            tv_mood.setText(mStrings[i]);
            return view;
        }
    }
}
