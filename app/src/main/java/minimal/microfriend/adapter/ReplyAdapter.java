package minimal.microfriend.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import minimal.microfriend.R;
import minimal.microfriend.entry.Reply;

public class ReplyAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Reply> replies;
	public ReplyAdapter(Context context,ArrayList<Reply> replies){
		this.context = context;
		this.replies = replies;
	}
	@Override
	public int getCount() {
		return replies.size();
	}

	@Override
	public Object getItem(int position) {
		return replies.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			convertView = View.inflate(this.context, R.layout.child_listview_item, null);
			holder = new ViewHolder();
			holder.observer = (TextView) convertView.findViewById(R.id.observer);
			holder.responder = (TextView) convertView.findViewById(R.id.responder);
			holder.word = (TextView) convertView.findViewById(R.id.word);
			convertView.setTag(holder);
		}
		else holder = (ViewHolder) convertView.getTag();
		holder.observer.setText(replies.get(position).getObserver().getPetname());
		holder.responder.setText(replies.get(position).getReceiver().getPetname());
		holder.word.setText(replies.get(position).getReplycontent());
		return convertView;
	}
	class ViewHolder{
		public TextView observer;
		public TextView responder;
		public TextView word;
	}
}
