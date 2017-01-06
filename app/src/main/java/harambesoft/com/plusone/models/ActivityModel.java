package harambesoft.com.plusone.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by isa on 28.12.2016.
 */

public class ActivityModel {
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("body")
    @Expose
    private String body;

    @SerializedName("poll_id")
    @Expose
    private int pollID;

    @SerializedName("comment_id")
    @Expose
    private int commentID;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public int getPollID() {
        return pollID;
    }

    public void setPollID(int pollID) {
        this.pollID = pollID;
    }

    public boolean hasPoll() {
        return pollID > 0;
    }

    public boolean hasComment() {
        return commentID > 0;
    }
}
