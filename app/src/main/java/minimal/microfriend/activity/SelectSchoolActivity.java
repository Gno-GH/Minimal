package minimal.microfriend.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import minimal.microfriend.R;
import minimal.microfriend.base.BaseActivity;

/**
 * Created by gno on 16/6/22.
 */
public class SelectSchoolActivity extends BaseActivity{
    private ArrayList<String> strings;
    private EditText et_search;
    private ListView lv_depart;
    private OwnerAdapter ownerAdapter;
    private PopupWindow mojarWindow;
    private View view;
    private LinearLayout logon;
    private RelativeLayout rl_repart;
    private ListView lv_major;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon_selectschool);
        strings = new ArrayList<String>();
        initView();
        ownerAdapter = new OwnerAdapter();
        lv_depart.setAdapter(ownerAdapter);
        initPopUpWindow();
        et_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    rl_repart.setVisibility(View.VISIBLE);
                    lv_depart.setVisibility(View.VISIBLE);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_search.getWindowToken(), 0);
                    for (int a = 0; a < 20; a++) {
                        strings.add(new String("ABCDEFG" + a));
                    }
                    ownerAdapter.notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });
        lv_depart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OwnerAdapter.ViewHolder holder = (OwnerAdapter.ViewHolder) (view.getTag());
                lv_major.setAdapter(ownerAdapter);
                lv_major.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        
                    }
                });
                mojarWindow.showAtLocation(logon, Gravity.CENTER, 0, 0);
            }
        });
    }

    public void initView() {
        et_search = (EditText) findViewById(R.id.et_search);
        lv_depart = (ListView) findViewById(R.id.lv_depart);
        logon = (LinearLayout) findViewById(R.id.logon);
        rl_repart = (RelativeLayout) findViewById(R.id.rl_depart);
    }

    private void initPopUpWindow() {
        view = View.inflate(SelectSchoolActivity.this, R.layout.listview_logon_major, null);
        lv_major = (ListView) view.findViewById(R.id.lv_major);
        mojarWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mojarWindow.setBackgroundDrawable(new BitmapDrawable());
        mojarWindow.setOutsideTouchable(true);
    }

    class OwnerAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return strings.size();
        }

        @Override
        public Object getItem(int i) {
            return strings.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = View.inflate(SelectSchoolActivity.this, R.layout.listview_logon_depart, null);
                holder = new ViewHolder();
                holder.tv = (TextView) view.findViewById(R.id.tv);
                view.setTag(holder);
            } else holder = (ViewHolder) view.getTag();
            holder.tv.setText(strings.get(i));
            return view;
        }

        public class ViewHolder {
            TextView tv;

            public TextView getTv() {
                return tv;
            }
        }
    }
}
