package harambesoft.com.plusone.api.model;

/**
 * Created by yucel on 12.12.2016.
 */

public class OptionModel {

    private String pollId;
    private Integer id;
    private String content;
    private String vote;

    /**
     *
     * @return
     *     The pollId
     */
    public String getPollId() {
        return pollId;
    }

    /**
     *
     * @param pollId
     *     The poll_id
     */
    public void setPollId(String pollId) {
        this.pollId = pollId;
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
     *     The vote
     */
    public String getVote() {
        return vote;
    }

    /**
     *
     * @param vote
     *     The vote
     */
    public void setVote(String vote) {
        this.vote = vote;
    }

}

