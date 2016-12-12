package harambesoft.com.plusone.api.model;

/**
 * Created by yucel on 12.12.2016.
 */

public class PollModel {

    private String userId;
    private String categoryId;
    private Integer id;
    private String question;
    private String pollType;
    private String optionType;
    private String stat;
    private String duration;
    private String latitude;
    private String longitude;
    private String diameter;

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
     *     The categoryId
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     *
     * @param categoryId
     *     The category_id
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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
     *     The question
     */
    public String getQuestion() {
        return question;
    }

    /**
     *
     * @param question
     *     The question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     *
     * @return
     *     The pollType
     */
    public String getPollType() {
        return pollType;
    }

    /**
     *
     * @param pollType
     *     The poll_type
     */
    public void setPollType(String pollType) {
        this.pollType = pollType;
    }

    /**
     *
     * @return
     *     The optionType
     */
    public String getOptionType() {
        return optionType;
    }

    /**
     *
     * @param optionType
     *     The option_type
     */
    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    /**
     *
     * @return
     *     The stat
     */
    public String getStat() {
        return stat;
    }

    /**
     *
     * @param stat
     *     The stat
     */
    public void setStat(String stat) {
        this.stat = stat;
    }

    /**
     *
     * @return
     *     The duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     *
     * @param duration
     *     The duration
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     *
     * @return
     *     The latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     *     The latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     *     The longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     *     The longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return
     *     The diameter
     */
    public String getDiameter() {
        return diameter;
    }

    /**
     *
     * @param diameter
     *     The diameter
     */
    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

}

