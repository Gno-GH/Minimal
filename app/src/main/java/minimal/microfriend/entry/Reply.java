package minimal.microfriend.entry;

/**
 * 回复类
 */
public class Reply {
	private User observer;// 评论者
	private User responder;// 回复者
	private String word;// 回复内容

	public Reply(User observer, User responder, String word) {
		this.observer = observer;
		this.responder = responder;
		this.word = word;
	}

	public User getObserver() {
		return observer;
	}

	public void setObserver(User observer) {
		this.observer = observer;
	}

	public User getResponder() {
		return responder;
	}

	public void setResponder(User responder) {
		this.responder = responder;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
}