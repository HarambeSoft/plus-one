package harambesoft.com.plusone.services;


import java.util.List;

import harambesoft.com.plusone.api.model.UserModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by yucel on 17.12.2016.
 */

public interface ApiInterface {



    @GET("user")
    Call<List<UserModel>> getUserInfos(@Path("id") int id, @Query("api_token") String apiKey);


}
