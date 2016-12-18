package harambesoft.com.plusone.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.lang.reflect.Array;

import butterknife.BindView;
import butterknife.ButterKnife;
import harambesoft.com.plusone.CurrentUser;
import harambesoft.com.plusone.R;
import harambesoft.com.plusone.api.model.PollModel;
import harambesoft.com.plusone.api.model.ResponseModel;
import harambesoft.com.plusone.services.ApiClient;
import harambesoft.com.plusone.services.ApiInterface;
import harambesoft.com.plusone.services.LocationTrackerService;
import retrofit2.Call;

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




        return view;
    }

    public void createPoll() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseModel<PollModel>> call = apiService.createPoll(editTextQuestion.getText().toString(),
                spinnerPollType.getSelectedItem().toString(),
                spinnerOptionType.getSelectedItem().toString(),
                editTextDuration.getText().toString(),
                CurrentUser.latitude(),
                CurrentUser.longitude(),
                editTextDiameter.getText().toString(),
                spinnerCategory.getSelectedItem().toString(),
                CurrentUser.apiToken());
    }



}
