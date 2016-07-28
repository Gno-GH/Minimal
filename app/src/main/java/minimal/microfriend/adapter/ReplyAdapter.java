package minimal.microfriend.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import cn.bmob.v3.listener.SaveListener;
import minimal.microfriend.R;
import minimal.microfriend.entry.Reply;
import minimal.microfriend.entry.Trend;
import minimal.microfriend.entry.User;
import minimal.microfriend.utils.MicroTools;

public class ReplyAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Reply> replies;
	private LinearLayout ll_pop;
	private PopupWindow mComment;
	private View popView;
	private Reply reply;
	private User user ;
	private Trend trend;
	public ReplyAdapter(Context context,ArrayList<Reply> replies,LinearLayout ll_pop,User user,Trend trend){
		this.context = context;
		this.replies = replies;
		this.ll_pop = ll_pop;
		this.user = user;
		this.trend = trend;
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
			holder.answer = (TextView) convertView.findViewById(R.id.answer);
			popCommentWindows(holder,position);
			convertView.setTag(holder);
		}
		else holder = (ViewHolder) convertView.getTag();
		holder.observer.setText(replies.get(position).getObserver().getPetname());
		if(replies.get(position).isfrist()) {
			holder.responder.setVisibility(View.INVISIBLE);
			holder.answer.setText(":");
		}
		else holder.responder.setText(replies.get(position).getReceiver().getPetname()+":");
		holder.word.setText(replies.get(position).getReplycontent());
		return convertView;
	}

	private void popCommentWindows(ViewHolder holder, final int position) {
		holder.word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
				initPopView();
				ImageButton pop_close = (ImageButton) popView.findViewById(R.id.pop_close);
				//关闭窗口按钮
				pop_close.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						mComment.dismiss();
					}
				});
				final EditText et_context = (EditText) popView.findViewById(R.id.et_context);
				Button send_yes = (Button) popView.findViewById(R.id.send_yes);
				send_yes.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						reply = new Reply();
						reply.setObserver(user);
						reply.setReceiver(replies.get(position).getObserver());
						reply.setIsfrist(false);
						if(et_context.getText().toString().trim().equals("")){
							et_context.setText("输入不能为空");
							return ;
						}
						reply.setReplycontent(et_context.getText().toString().trim());
						reply.setTrend(trend);
						reply.save(context, new SaveListener() {
							@Override
							public void onSuccess() {
								replies.add(reply);
								mComment.dismiss();
								ReplyAdapter.this.notifyDataSetChanged();
							}

							@Override
							public void onFailure(int i, String s) {
								MicroTools.toast(context,s);
							}
						});
					}
				});
            }
        });
	}

	private void initPopView() {
		popView = View.inflate(context, R.layout.pop_comment,null);
		mComment = new PopupWindow(popView,
				ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
		mComment.setBackgroundDrawable(new BitmapDrawable());
		mComment.setOutsideTouchable(true);
		mComment.showAtLocation(ll_pop, Gravity.CENTER, 0, 0);
	}

	class ViewHolder{
		public TextView observer;
		public TextView responder;
		public TextView word;
		public TextView answer;
	}
}
