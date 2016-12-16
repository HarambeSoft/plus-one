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
     * No args constructor for use in serialization
     *
     */
    public OptionModel() {
    }

    /**
     *
     * @param content
     * @param id
     * @param pollId
     * @param vote
     */
    public OptionModel(String pollId, Integer id, String content, String vote) {
        super();
        this.pollId = pollId;
        this.id = id;
        this.content = content;
        this.vote = vote;
    }

    public String getPollId() {
        return pollId;
    }

    public void setPollId(String pollId) {
        this.pollId = pollId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

}
