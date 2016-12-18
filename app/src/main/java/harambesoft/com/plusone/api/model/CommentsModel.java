package harambesoft.com.plusone.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yucel on 16.12.2016.
 */

public class CommentsModel {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("up_vote")
    @Expose
    private String upVote;
    @SerializedName("down_vote")
    @Expose
    private String downVote;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpVote() {
        return upVote;
    }

    public void setUpVote(String upVote) {
        this.upVote = upVote;
    }

    public String getDownVote() {
        return downVote;
    }

    public void setDownVote(String downVote) {
        this.downVote = downVote;
    }

}

