package minimal.microfriend.entry;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 动态类
 */
public class Trend extends BmobObject {
    private User createUser;//创建者
    private String contentText;//文本内容
    private BmobFile contentImg;//图片
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

    public BmobFile getContentImg() {
        return contentImg;
    }

    public void setContentImg(BmobFile contentImg) {
        this.contentImg = contentImg;
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

    @Override
    public String toString() {
        return contentText+"----";
    }
}
