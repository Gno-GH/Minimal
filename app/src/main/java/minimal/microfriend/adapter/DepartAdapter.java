package minimal.microfriend.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import minimal.microfriend.R;
import minimal.microfriend.entry.Depart;

/**
 * Created by gno on 16/7/18.
 */
public class DepartAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Depart> departs;
    public DepartAdapter(Context context,ArrayList<Depart> departs){
        this.context = context;
        this.departs = departs;
    }
    @Override
    public int getCount() {
        return departs.size();
    }

    @Override
    public Object getItem(int i) {
        return departs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(context, R.layout.listview_logon_depart, null);
            holder = new ViewHolder();
            holder.tv = (TextView) view.findViewById(R.id.tv);
            view.setTag(holder);
        } else holder = (ViewHolder) view.getTag();
        holder.tv.setText(departs.get(i).getDname());
        return view;
    }

    public class ViewHolder {
        TextView tv;

        public TextView getTv() {
            return tv;
        }
    }
}
