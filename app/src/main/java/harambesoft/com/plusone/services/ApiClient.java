package harambesoft.com.plusone.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yucel on 17.12.2016.
 */

public class ApiClient {
    public static final String BASE_URL = "http://192.198.1.174/~isa/plus-one-server/public/index.php/api/v1/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
