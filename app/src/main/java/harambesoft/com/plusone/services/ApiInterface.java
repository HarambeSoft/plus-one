package harambesoft.com.plusone.services;


import java.util.List;

import harambesoft.com.plusone.api.model.CategoryModel;
import harambesoft.com.plusone.api.model.OptionModel;
import harambesoft.com.plusone.api.model.PollModel;
import harambesoft.com.plusone.api.model.ResponseModel;
import harambesoft.com.plusone.api.model.TokenModel;
import harambesoft.com.plusone.api.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by yucel on 17.12.2016.
 */

public interface ApiInterface {

    @POST("token")
    Call<TokenModel> getToken(@Query("name") String name,
                              @Query("password") String password);

    @GET("user/{id}")
    Call<User> getUser(@Path("id") String id,
                       @Query("api_token") String api_token);

    @PUT("user/{id}")
    Call<ResponseModel> updateUser(@Path("id") String id,
                                   @Query("email") String eMail,
                                   @Query("fullname") String fullName,
                                   @Query("country") String country,
                                   @Query("city") String city,
                                   @Query("profession") String profession,
                                   @Query("api_token") String api_token);

    @GET("category")
    Call<List<CategoryModel>> getCategories(@Query("api_token") String api_token);

    @POST("poll")
    Call<ResponseModel<PollModel>> createPoll(@Query("question") String question,
                                              @Query("poll_type") String pollType,
                                              @Query("option_type") String optionType,
                                              @Query("duration") String duration,
                                              @Query("latitude") String latitude,
                                              @Query("longitude") String longitude,
                                              @Query("diameter") String diameter,
                                              @Query("category_id") String categoryId,
                                              @Query("api_token") String api_token);

    @POST("poll/{id}/option")
    Call<ResponseModel<OptionModel>> createOption(@Path("id") String id,
                                                  @Query("content") String content);
}
