package harambesoft.com.plusone.api.model;

/**
 * Created by yucel on 12.12.2016.
 */

public class PollCommentsModel {

    private String userId;
    private Integer id;
    private String createDate;
    private String content;
    private String upVote;
    private String downVote;

    /**
     *
     * @return
     *     The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     *     The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     *     The createDate
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     *
     * @param createDate
     *     The create_date
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     *
     * @return
     *     The content
     */
    public String getContent() {
        return content;
    }

    /**
     *
     * @param content
     *     The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     *
     * @return
     *     The upVote
     */
    public String getUpVote() {
        return upVote;
    }

    /**
     *
     * @param upVote
     *     The up_vote
     */
    public void setUpVote(String upVote) {
        this.upVote = upVote;
    }

    /**
     *
     * @return
     *     The downVote
     */
    public String getDownVote() {
        return downVote;
    }

    /**
     *
     * @param downVote
     *     The down_vote
     */
    public void setDownVote(String downVote) {
        this.downVote = downVote;
    }

}

