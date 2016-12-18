package harambesoft.com.plusone.api.model;

/**
 * Created by yucel on 18.12.2016.
 */

public class TokenModel {

    private Boolean error;
    private String apiToken;
    private UserModel userModel;

    /**
     * No args constructor for use in serialization
     * TokenModel
     * public Example() {
     * }
     * <p>
     * /**
     *
     * @param apiToken
     * @param error
     * @param userModel
     */
    public TokenModel(Boolean error, String apiToken, UserModel userModel) {
        super();
        this.error = error;
        this.apiToken = apiToken;
        this.userModel = userModel;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }


}