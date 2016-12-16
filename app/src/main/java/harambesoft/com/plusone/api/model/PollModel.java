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
     * No args constructor for use in serialization
     *
     */
    public PollModel() {
    }

    /**
     *
     * @param id
     * @param diameter
     * @param duration
     * @param categoryId
     * @param userId
     * @param pollType
     * @param longitude
     * @param latitude
     * @param question
     * @param stat
     * @param optionType
     */
    public PollModel(String userId, String categoryId, Integer id, String question, String pollType, String optionType, String stat, String duration, String latitude, String longitude, String diameter) {
        super();
        this.userId = userId;
        this.categoryId = categoryId;
        this.id = id;
        this.question = question;
        this.pollType = pollType;
        this.optionType = optionType;
        this.stat = stat;
        this.duration = duration;
        this.latitude = latitude;
        this.longitude = longitude;
        this.diameter = diameter;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getPollType() {
        return pollType;
    }

    public void setPollType(String pollType) {
        this.pollType = pollType;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

}


