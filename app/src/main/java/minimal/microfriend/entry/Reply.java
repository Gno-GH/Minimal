package minimal.microfriend.entry;

import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * 回复类
 */
public class Reply extends BmobObject {
    private User observer;// 评论者
    private User receiver;// 回复者
    private String replycontent;// 回复内容
    private boolean isfrist;//是否为主评论

    public User getObserver() {
        return observer;
    }

    public void setObserver(User observer) {
        this.observer = observer;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getReplycontent() {
        return replycontent;
    }

    public void setReplycontent(String replycontent) {
        this.replycontent = replycontent;
    }

    public boolean isfrist() {
        return isfrist;
    }

    public void setIsfrist(boolean isfrist) {
        this.isfrist = isfrist;
    }
}