package harambesoft.com.plusone.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import harambesoft.com.plusone.App;
import harambesoft.com.plusone.views.ChoiceItemView;
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

    @BindView(R.id.layoutChoices)
    LinearLayout layoutChoices;

    @BindView(R.id.buttonShowComments)
    TextView buttonShowComments;

    int pollID;

    public static PollFragment newInstance(int pollID) {
        Bundle args = new Bundle();
        args.putInt("id", pollID);

        PollFragment fragment = new PollFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public int getPollID() {
        return pollID;
    }

    public void setPollID(int pollID) {
        this.pollID = pollID;
    }

    /* TODO:
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

        Bundle args = getArguments();
        int pollID = args.getInt("id", -1);
        if (pollID != -1) {
            setPollID(pollID);
            loadPoll(pollID);
        }
    }

    public void loadPoll(int id) {
        ApiClient.apiService().getPoll(id, CurrentUser.apiToken()).enqueue(new Callback<PollModel>() {
            @Override
            public void onResponse(Call<PollModel> call, Response<PollModel> response) {
                PollModel poll = response.body();
                loadPoll(poll);
            }

            @Override
            public void onFailure(Call<PollModel> call, Throwable t) {

            }
        });
    }

    public void loadPoll(PollModel poll) {
        layoutChoices.removeAllViews();

        textViewPollQuestion.setText(poll.getQuestion());

        List<OptionModel> options = poll.getOptionModels();
        for (int i = 0; i < options.size(); i++) {
            ChoiceItemView choiceItemView = new ChoiceItemView(getActivity(), options.get(i));
            layoutChoices.addView(choiceItemView);
        }

        ((ChoiceItemView) layoutChoices.getChildAt(options.size() - 1)).showSeparator(false); // Hide last separator
    }

    @OnClick(R.id.buttonShowComments)
    public void showComments() {
        App.showComments(getPollID());
    }
}
