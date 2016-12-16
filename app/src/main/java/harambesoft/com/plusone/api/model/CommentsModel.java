package harambesoft.com.plusone.api.model;

/**
 * Created by yucel on 16.12.2016.
 */

public class CommentsModel {

    private String userId;
    private Integer id;
    private String createDate;
    private String content;
    private String upVote;
    private String downVote;

    /**
     * No args constructor for use in serialization
     *
     */
    public CommentsModel() {
    }

    /**
     *
     * @param content
     * @param id
     * @param downVote
     * @param userId
     * @param upVote
     * @param createDate
     */
    public CommentsModel(String userId, Integer id, String createDate, String content, String upVote, String downVote) {
        super();
        this.userId = userId;
        this.id = id;
        this.createDate = createDate;
        this.content = content;
        this.upVote = upVote;
        this.downVote = downVote;
    }

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

