package harambesoft.com.plusone.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.ganfra.materialspinner.MaterialSpinner;
import harambesoft.com.plusone.App;
import harambesoft.com.plusone.CurrentUser;
import harambesoft.com.plusone.R;
import harambesoft.com.plusone.models.CategoryModel;
import harambesoft.com.plusone.models.PollModel;
import harambesoft.com.plusone.models.RequestOptionModel;
import harambesoft.com.plusone.models.ResponseModel;
import harambesoft.com.plusone.models.SimpleResponseModel;
import harambesoft.com.plusone.services.ApiClient;
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
    @BindView(R.id.spinnerCategory)
    Spinner spinnerCategory;
    @BindView(R.id.spinnerOptionType)
    MaterialSpinner spinnerOptionType;
    @BindView(R.id.spinnerPollType)
    Spinner spinnerPollType;
    @BindView(R.id.layoutChoicesNewPoll)
    LinearLayout layoutChoicesNewPoll;

    private static final String TAG = "NewPollFragment";
    private HashMap<String, Integer> hashMapCategories;
    private ArrayList<EditText> choicesList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newpoll, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);

        loadSpinners();
        loadCategories();
        addNewChoice();

        return view;
    }

    private void loadSpinners() {
        String arrayOptionType[] = {"Multi", "Single"};
        ArrayAdapter<String> adapterOptionType = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, arrayOptionType);
        spinnerOptionType.setAdapter(adapterOptionType);

        String arrayPollType[] = {"Global", "Local"};
        ArrayAdapter<String> adapterPollType = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, arrayPollType);
        spinnerPollType.setAdapter(adapterPollType);
    }

    public void loadCategories() {
        hashMapCategories = new HashMap<>();
        ApiClient.apiService().getCategories(CurrentUser.apiToken()).enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                String[] arrayCategories = new String[response.body().size()];
                for (int i = 0; i < response.body().size(); i++) {
                    String categoryName = response.body().get(i).getName();
                    hashMapCategories.put(categoryName, response.body().get(i).getId());
                    arrayCategories[i] = categoryName;
                }
                ArrayAdapter<String> adapterCategories = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, arrayCategories);
                spinnerCategory.setAdapter(adapterCategories);

            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {

            }
        });
    }

    public void createPoll() {
        ApiClient.apiService().createPoll(editTextQuestion.getText().toString(),
                spinnerPollType.getSelectedItem().toString().toLowerCase(),
                spinnerOptionType.getSelectedItem().toString().toLowerCase(),
                editTextDuration.getText().toString(),
                CurrentUser.latitude(),
                CurrentUser.longitude(),
                editTextDiameter.getText().toString(),
                hashMapCategories.get(spinnerCategory.getSelectedItem().toString()).toString(),
                CurrentUser.apiToken()).
                enqueue(new Callback<ResponseModel<PollModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<PollModel>> call, Response<ResponseModel<PollModel>> response) {
                Log.d(TAG, "Poll added.");
                Log.d(TAG, response.body().getMessage());

                if (!response.body().getError()) {
                    addOptionsToPoll(response.body().getResponse().getId().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<PollModel>> call, Throwable t) {
                Log.d(TAG, "Can't create poll.");
                Log.e(TAG, "Failed" + t.getLocalizedMessage() + " " + call.request());
            }
        });
    }

    private void addOptionsToPoll(final String pollID) {
        List<RequestOptionModel> requestOptionModels = new ArrayList<>();
        for (EditText editText: choicesList) {
            String content = editText.getText().toString();
            if (!content.isEmpty()) {
                RequestOptionModel requestOptionModel = new RequestOptionModel();
                requestOptionModel.setContent(content);
                requestOptionModels.add(requestOptionModel);
            }
        }

        ApiClient.apiService().createOption(pollID,
                requestOptionModels).enqueue(new Callback<SimpleResponseModel>() {
            @Override
            public void onResponse(Call<SimpleResponseModel> call, Response<SimpleResponseModel> response) {
                Log.d(TAG, "Options added to poll.");
                // It's now safe to show poll.
                App.showPoll(Integer.valueOf(pollID));
            }

            @Override
            public void onFailure(Call<SimpleResponseModel> call, Throwable t) {
                Log.d(TAG, "Can't add options to poll.");
                Log.e(TAG, "Failed " + t.getLocalizedMessage() + " " + call.request());
            }
        });
    }


    private void addNewChoice() {
        //TODO: add remove button
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        EditText editTextChoice = new EditText(getActivity());
        editTextChoice.setLayoutParams(params);
        editTextChoice.setHint("Option");

        // EVENTS
        editTextChoice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    int n = choicesList.indexOf(v);
                    if (n + 1 == choicesList.size()) { // is this the last element
                        addNewChoice();
                    }
                }
            }
        });

        choicesList.add(editTextChoice);
        layoutChoicesNewPoll.addView(editTextChoice);
    }

    @OnClick(R.id.buttonCreate)
    public void onClickCreatePoll() {
        createPoll();
    }

    @OnClick(R.id.buttonCancel)
    public void onClickCancelPoll() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new ActivityStreamFragment(), "ActivityStreamTag")
                .commit();
    }

}
