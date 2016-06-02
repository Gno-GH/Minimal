package minimal.microfriend.entry;

import java.util.ArrayList;

/**
 * 动态类
 */
public class Trend {
	private String context_text;//文本内容
	private String context_image;//图片路径
	private ArrayList<Reply> replies;//评论集合
	public Trend(String context_text,String context_image,ArrayList<Reply> replies) {
		this.context_text = context_text;
		this.context_image = context_image;
		this.replies = replies;
	}
	public String getContext_text() {
		return context_text;
	}
	public void setContext_text(String context_text) {
		this.context_text = context_text;
	}
	public String getContext_image() {
		return context_image;
	}
	public void setContext_image(String context_image) {
		this.context_image = context_image;
	}
	public ArrayList<Reply> getReplies() {
		return replies;
	}
	public void setReplies(ArrayList<Reply> replies) {
		this.replies = replies;
	}
}
