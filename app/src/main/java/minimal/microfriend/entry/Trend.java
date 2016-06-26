package minimal.microfriend.entry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 动态类
 */
public class Trend extends BmobObject {
    private User createUser;//创建者
    private String contentText;//文本内容
    private List contentImgs;//图片路径集合
    private Integer like;//喜欢
    private Integer dislike;//讨厌

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public List getContentImgs() {
        return contentImgs;
    }

    public void setContentImgs(List contentImgs) {
        this.contentImgs = contentImgs;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getDislike() {
        return dislike;
    }

    public void setDislike(Integer dislike) {
        this.dislike = dislike;
    }

}
