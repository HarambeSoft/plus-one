package harambesoft.com.plusone.services;


import java.util.List;

import harambesoft.com.plusone.api.model.TokenModel;
import harambesoft.com.plusone.api.model.UserModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by yucel on 17.12.2016.
 */

public interface ApiInterface {

    @GET("user")
    Call<UserModel> getUserInfos(@Path("id") String id, @Query("api_token") String apiKey);

    @POST("token")
    Call<TokenModel> getToken(@Header("name") String name, @Header("passwort") String password);


}
