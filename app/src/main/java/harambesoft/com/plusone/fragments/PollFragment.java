package harambesoft.com.plusone.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import harambesoft.com.plusone.CurrentUser;
import harambesoft.com.plusone.R;
import harambesoft.com.plusone.models.OptionModel;
import harambesoft.com.plusone.models.PollModel;
import harambesoft.com.plusone.services.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by isa on 29.12.2016.
 */

public class PollFragment extends Fragment {
    @BindView(R.id.textViewPollQuestion)
    TextView textViewPollQuestion;

    @BindView(R.id.textViewChoice1)
    TextView textViewChoice1;

    @BindView(R.id.textViewChoice2)
    TextView textViewChoice2;

    @BindView(R.id.textViewChoice3)
    TextView textViewChoice3;

    @BindView(R.id.textViewChoice4)
    TextView textViewChoice4;

    ArrayList<TextView> optionTextViews = new ArrayList<>();

    public static PollFragment newInstance(int pollID) {
        Bundle args = new Bundle();
        args.putInt("id", pollID);

        PollFragment fragment = new PollFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /*
    public static PollFragment newInstance(PollModel poll) {
        Bundle args = new Bundle();
        args.putParcelable("poll", poll);

        PollFragment fragment = new PollFragment();
        fragment.setArguments(args);
        return fragment;
    }
    */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_poll, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        // FIXME: make options dynamically increase
        optionTextViews.add(textViewChoice1);
        optionTextViews.add(textViewChoice2);
        optionTextViews.add(textViewChoice3);
        optionTextViews.add(textViewChoice4);

        Bundle args = getArguments();
        int pollID = args.getInt("id", -1);
        if (pollID != -1) {
            loadPoll(pollID);
        }
    }

    public void loadPoll(int id) {
        ApiClient.apiService().getPoll(id, CurrentUser.apiToken()).enqueue(new Callback<PollModel>() {
            @Override
            public void onResponse(Call<PollModel> call, Response<PollModel> response) {
                PollModel poll = response.body();
                textViewPollQuestion.setText(poll.getQuestion());

                List<OptionModel> options = poll.getOptionModels();
                for (int i = 0; i < options.size(); i++) {
                    if (i < optionTextViews.size()) // FIXME: make options dynamically increase
                        optionTextViews.get(i).setText(options.get(i).getContent());
                }
            }

            @Override
            public void onFailure(Call<PollModel> call, Throwable t) {

            }
        });
    }
}