package harambesoft.com.plusone.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import harambesoft.com.plusone.CurrentUser;
import harambesoft.com.plusone.PlusOne;
import harambesoft.com.plusone.R;
import harambesoft.com.plusone.api.PlusOneAPI;
import harambesoft.com.plusone.api.model.TokenModel;
import harambesoft.com.plusone.api.model.User;
import harambesoft.com.plusone.services.ApiClient;
import harambesoft.com.plusone.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import harambesoft.com.plusone.R;

/**
 * Created by gizemfitoz on 13/12/16.
 */

public class MeFragment extends Fragment {

    private static String name = "YucelT";
    private static String password = "14593683762";

    private static final String TAG = MeFragment.class.getSimpleName();

    @BindView(R.id.editTextProfession)
    EditText editTextProfession;

    @BindView(R.id.editTextrCountry)
    EditText editTextrCountry;

    @BindView(R.id.editTextrFullName)
    EditText editTextrFullName;

    @BindView(R.id.editTextrCıty)
    EditText editTextrCıty;

    @BindView(R.id.editTextrEMail)
    EditText editTextrEMail;

    @BindView(R.id.textViewCreatedDate)
    TextView textViewCreatedDate;

    @BindView(R.id.textViewXp)
    TextView textViewXp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
        getUser();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void getUser() {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<User> call = apiService.getUser(CurrentUser.id(), CurrentUser.apiToken());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d(TAG, "onResponse: " + response.body().getId());
                Log.d(TAG, "onResponse: " + response.body().getName());
                Log.d(TAG, "onResponse: " + response.body().getEmail());
                Log.d(TAG, "onResponse: " + response.body().getXp());
                Log.d(TAG, "onResponse: " + response.body().getCreateDate());
                editTextrFullName.setText(response.body().getName());
                textViewXp.setText("XP: " + response.body().getXp());
                editTextrEMail.setText(response.body().getEmail());
                textViewCreatedDate.setText("CD: " + response.body().getCreateDate());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    @OnClick(R.id.buttonMeSave)
    public void saveUser(){

    }
}
