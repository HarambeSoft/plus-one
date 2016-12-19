package harambesoft.com.plusone.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import harambesoft.com.plusone.CurrentUser;
import harambesoft.com.plusone.R;
import harambesoft.com.plusone.model.CategoryModel;
import harambesoft.com.plusone.model.RequestOptionModel;
import harambesoft.com.plusone.model.PollModel;
import harambesoft.com.plusone.model.ResponseModel;
import harambesoft.com.plusone.services.ApiClient;
import harambesoft.com.plusone.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by isa on 18.12.2016.
 */
public class NewPollFragment extends Fragment {
    @BindView(R.id.editTextPollTitle)
    EditText editTextPollTitle;
    @BindView(R.id.editTextQuestion)
    EditText editTextQuestion;
    @BindView(R.id.editTextDuration)
    EditText editTextDuration;
    @BindView(R.id.editTextDiameter)
    EditText editTextDiameter;
    @BindView(R.id.editTextChoice1)
    EditText editTextChoice1;
    @BindView(R.id.editTextChoice2)
    EditText editTextChoice2;
    @BindView(R.id.editTextChoice3)
    EditText editTextChoice3;
    @BindView(R.id.editTextChoice4)
    EditText editTextChoice4;
    @BindView(R.id.spinnerCategory)
    Spinner spinnerCategory;
    @BindView(R.id.spinnerOptionType)
    Spinner spinnerOptionType;
    @BindView(R.id.spinnerPollType)
    Spinner spinnerPollType;

    private HashMap<String, Integer> hashMapCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newpoll, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);

        String arrayOptionType[] = {"multi", "single"};
        ArrayAdapter<String> adapterOptionType = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrayOptionType);
        spinnerOptionType.setAdapter(adapterOptionType);

        String arrayPollType[] = {"global", "local"};
        ArrayAdapter<String> adapterPollType = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrayPollType);
        spinnerPollType.setAdapter(adapterPollType);

        getCategories();

        return view;
    }

    public void getCategories() {

        hashMapCategory = new HashMap<>();

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<List<CategoryModel>> call = apiService.getCategories(CurrentUser.apiToken());
        call.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                String[] arrayCategories = new String[response.body().size()];
                for (int i = 0; i < response.body().size(); i++) {
                    String categoryName = response.body().get(i).getName();
                    hashMapCategory.put(categoryName, response.body().get(i).getId());
                    arrayCategories[i] = categoryName;
                }
                ArrayAdapter<String> adapterCategories = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrayCategories);
                spinnerCategory.setAdapter(adapterCategories);

            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {

            }
        });
    }

    public void createPoll() {
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseModel<PollModel>> call = apiService.createPoll(editTextQuestion.getText().toString(),
                spinnerPollType.getSelectedItem().toString(),
                spinnerOptionType.getSelectedItem().toString(),
                editTextDuration.getText().toString(),
                CurrentUser.latitude(),
                CurrentUser.longitude(),
                editTextDiameter.getText().toString(),
                hashMapCategory.get(spinnerCategory.getSelectedItem().toString()).toString(),
                CurrentUser.apiToken());
        call.enqueue(new Callback<ResponseModel<PollModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<PollModel>> call, Response<ResponseModel<PollModel>> response) {
                Log.e("TEST", response.body().getMessage());
                if (!response.body().getError()) {

                    RequestOptionModel requestOptionModel = new RequestOptionModel();
                    requestOptionModel.setContent(editTextChoice1.getText().toString());
                    RequestOptionModel requestOptionModel2 = new RequestOptionModel();
                    requestOptionModel2.setContent(editTextChoice2.getText().toString());
                    RequestOptionModel requestOptionModel3 = new RequestOptionModel();
                    requestOptionModel3.setContent(editTextChoice3.getText().toString());
                    RequestOptionModel requestOptionModel4 = new RequestOptionModel();
                    requestOptionModel4.setContent(editTextChoice4.getText().toString());

                    List<RequestOptionModel> requestOptionModels = new ArrayList<RequestOptionModel>();
                    requestOptionModels.add(requestOptionModel);
                    requestOptionModels.add(requestOptionModel2);
                    requestOptionModels.add(requestOptionModel3);
                    requestOptionModels.add(requestOptionModel4);

                    Gson gson = new Gson();
                    Log.d("TEST", "GSON: " +   gson.toJson(requestOptionModels));
                    Call<List<RequestOptionModel>> callOption = apiService.createOption(response.body().getResponse().getId().toString(),
                            requestOptionModels);

                    callOption.enqueue(new Callback<List<RequestOptionModel>>() {
                        @Override
                        public void onResponse(Call<List<RequestOptionModel>> call, Response<List<RequestOptionModel>> response) {

                        }

                        @Override
                        public void onFailure(Call<List<RequestOptionModel>> call, Throwable t) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<ResponseModel<PollModel>> call, Throwable t) {
                Log.e("TEST", "Failed" + t.getLocalizedMessage() + " " + call.request());
            }
        });
    }

    @OnClick(R.id.buttonCreate)
    public void onClickCreatePoll() {
        Log.d("Test", "BASTIN AMK");
        createPoll();
    }


}
