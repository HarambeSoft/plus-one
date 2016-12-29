package harambesoft.com.plusone.services;


import java.util.List;

import harambesoft.com.plusone.models.CategoryModel;
import harambesoft.com.plusone.models.PollModel;
import harambesoft.com.plusone.models.RequestOptionModel;
import harambesoft.com.plusone.models.ResponseModel;
import harambesoft.com.plusone.models.SimpleResponseModel;
import harambesoft.com.plusone.models.TokenModel;
import harambesoft.com.plusone.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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
                                   @Query("gender") String gender,
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

    @GET("poll/{id}")
    Call<PollModel> getPoll(@Path("id") int id,
                            @Query("api_token") String api_token);

    @Headers("Content-Type: application/json")
    @POST("poll/{id}/options")
    Call<SimpleResponseModel> createOption(@Path("id") String id,
                                           @Body List<RequestOptionModel> optionModelList);
}
