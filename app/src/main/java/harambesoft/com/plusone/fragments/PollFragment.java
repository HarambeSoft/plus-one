package harambesoft.com.plusone.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import harambesoft.com.plusone.App;
import harambesoft.com.plusone.models.ResponseModel;
import harambesoft.com.plusone.models.SimpleResponseModel;
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
    PollModel poll;
    ArrayList<ChoiceItemView> choiceItemViews = new ArrayList<>();

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

    public PollModel getPoll() {
        return poll;
    }

    public void setPoll(PollModel poll) {
        this.poll = poll;
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
        setPoll(poll);
        layoutChoices.removeAllViews();
        choiceItemViews.clear();

        textViewPollQuestion.setText(poll.getQuestion());

        List<OptionModel> options = poll.getOptionModels();
        for (int i = 0; i < options.size(); i++) {
            final int index = i;
            final ChoiceItemView choiceItemView = new ChoiceItemView(getActivity(), options.get(i));
            choiceItemView.radioButtonChoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (choiceItemView.isChecked())
                        vote(index);
                    else
                        unvote(index);
                }
            });
            layoutChoices.addView(choiceItemView);
            choiceItemViews.add(choiceItemView);
        }

        choiceItemViews.get(choiceItemViews.size() - 1).showSeparator(false); // Hide last separator

        setUserVotes();
    }

    private void setUserVotes() {
        ApiClient.apiService().getVotesOfUserOnPoll(CurrentUser.id(), String.valueOf(pollID), CurrentUser.apiToken()).enqueue(new Callback<ResponseModel<List<Integer>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<Integer>>> call, Response<ResponseModel<List<Integer>>> response) {
                boolean voteCasted = !response.body().getError();
                if (voteCasted) {
                    List<Integer> votedChoices = response.body().getResponse();
                    if (votedChoices != null) {
                        for(int votedID: votedChoices) {
                            for (int j = 0; j < getPoll().getOptionModels().size(); j++) {
                                if (getPoll().getOptionModels().get(j).getId() == votedID) {
                                    choiceItemViews.get(j).setChecked(true);
                                }
                            }
                        }
                    } else {
                        Log.d("PollFrag::setUserVotes", "NULL VOTECHOICES");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<Integer>>> call, Throwable t) {

            }
        });
    }

    public void vote(final int choice) {
        ApiClient.apiService().votePollOption(getPoll().getId().toString(),
                                              getPoll().getOptionModels().get(choice).getId().toString(),
                                              CurrentUser.apiToken()).enqueue(new Callback<SimpleResponseModel>() {
            @Override
            public void onResponse(Call<SimpleResponseModel> call, Response<SimpleResponseModel> response) {
                // Vote success.
                if (getPoll().getOptionType().equals("single")) {
                    for (ChoiceItemView choiceItemView : choiceItemViews) {
                        choiceItemView.setChecked(false);
                    }
                    choiceItemViews.get(choice).setChecked(true);
                }

                updateVote(choice, 1);
            }

            @Override
            public void onFailure(Call<SimpleResponseModel> call, Throwable t) {

            }
        });
    }

    public void unvote(final int choice) {
        ApiClient.apiService().unvotePollOption(getPoll().getId().toString(),
                                                getPoll().getOptionModels().get(choice).getId().toString(),
                                                CurrentUser.apiToken()).enqueue(new Callback<SimpleResponseModel>() {
            @Override
            public void onResponse(Call<SimpleResponseModel> call, Response<SimpleResponseModel> response) {
                choiceItemViews.get(choice).setChecked(false);
                updateVote(choice, -1);
            }

            @Override
            public void onFailure(Call<SimpleResponseModel> call, Throwable t) {

            }
        });
        choiceItemViews.get(choice).setChecked(false);
    }

    private void updateVote(int choice, int vote) {
        int currentVote = Integer.valueOf(getPoll().getOptionModels().get(choice).getVote());
        getPoll().getOptionModels().get(choice).setVote(String.valueOf(currentVote + vote));
        loadPoll(getPoll());
    }

    @OnClick(R.id.buttonShowComments)
    public void showComments() {
        App.showComments(getPollID());
    }
}
