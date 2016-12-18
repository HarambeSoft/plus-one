package harambesoft.com.plusone.services;


import java.util.List;

import harambesoft.com.plusone.api.model.TokenModel;
import harambesoft.com.plusone.api.model.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by yucel on 17.12.2016.
 */

public interface ApiInterface {

    @POST("token")
    Call<TokenModel> getToken(@Query("name") String name, @Query("password") String password);

    @GET("user/{id}")
    Call<User> getUser(@Path("id") String id, @Query("api_token") String api_token);
}
