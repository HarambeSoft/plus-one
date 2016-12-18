package harambesoft.com.plusone.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yucel on 12.12.2016.
 */

public class PollModel {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("poll_type")
    @Expose
    private String pollType;
    @SerializedName("option_type")
    @Expose
    private String optionType;
    @SerializedName("stat")
    @Expose
    private String stat;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("diameter")
    @Expose
    private String diameter;
    @SerializedName("options")
    @Expose
    private List<OptionModel> optionModels;

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

    public List<OptionModel> getOptionModels() {
        return optionModels;
    }

    public void setOptionModels(List<OptionModel> optionModels) {
        this.optionModels = optionModels;
    }
}


