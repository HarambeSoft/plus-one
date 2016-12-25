package harambesoft.com.plusone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yucel on 18.12.2016.
 */

public class ResponseModel<T> extends SimpleResponseModel {
    @SerializedName("response")
    @Expose
    private T response;

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}


