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
import harambesoft.com.plusone.api.model.TokenModel;
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

    private static String name = "YucelT";
    private static String password = "14593683762";

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

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<TokenModel> call = apiService.getToken(name, password);
        call.enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                TokenModel tokenModel;
                tokenModel = response.body();
                Log.d(TAG, "USER: " + tokenModel.getUserModel().getEmail());
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
}
