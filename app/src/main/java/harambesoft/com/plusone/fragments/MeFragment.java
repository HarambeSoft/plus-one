package harambesoft.com.plusone.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import harambesoft.com.plusone.R;
import harambesoft.com.plusone.api.PlusOneAPI;
import harambesoft.com.plusone.api.model.UserModel;
import harambesoft.com.plusone.services.ApiClient;
import harambesoft.com.plusone.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gizemfitoz on 13/12/16.
 */

public class MeFragment extends Fragment {

    private static String API_KEY;
    private static String userId;

    private static final String TAG = MeFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getUser();
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void getUser() {

        PlusOneAPI plusOneAPI = new PlusOneAPI();
        API_KEY = plusOneAPI.getUserToken();
        userId = plusOneAPI.getUserId();
        Log.e("TEST", "User ID: " + userId);
        Log.e("TEST", "User API_KEY: " + API_KEY);

        /*ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<UserModel>> call = apiService.getUserInfos(userId, API_KEY);
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                List<UserModel> userModels = new ArrayList<UserModel>();
                userModels = response.body();
                Log.d(TAG, "Number of users received: " + userModels.size());
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });*/
    }
}
