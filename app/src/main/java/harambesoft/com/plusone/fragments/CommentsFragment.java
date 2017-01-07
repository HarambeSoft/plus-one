package harambesoft.com.plusone.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import harambesoft.com.plusone.CurrentUser;
import harambesoft.com.plusone.R;
import harambesoft.com.plusone.adapters.CommentsAdapter;
import harambesoft.com.plusone.helpers.RecyclerTouchListener;
import harambesoft.com.plusone.models.CommentModel;
import harambesoft.com.plusone.models.SimpleResponseModel;
import harambesoft.com.plusone.services.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by isa on 1/6/17.
 */

public class CommentsFragment extends Fragment {
    @BindView(R.id.recyclerViewComments)
    RecyclerView recyclerViewComments;

    @BindView(R.id.editTextComment)
    EditText editTextComment;

    @BindView(R.id.buttonComment)
    Button buttonComment;

    private CommentsAdapter commentsAdapter;
    private List<CommentModel> commentModelList = new ArrayList<>();

    int pollID;

    public static Fragment newInstance(int pollID) {
        Bundle args = new Bundle();
        args.putInt("pollID", pollID);

        CommentsFragment fragment = new CommentsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comments, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        Bundle args = getArguments();
        int pollID = args.getInt("pollID", -1);
        if (pollID != -1) {
            setPollID(pollID);
            loadComments(pollID);
        }

        loadAdapterAndRecyclerView();
    }

    private void loadComments(int pollID) {
        ApiClient.apiService().getComments(String.valueOf(pollID), CurrentUser.apiToken()).enqueue(new Callback<List<CommentModel>>() {
            @Override
            public void onResponse(Call<List<CommentModel>> call, Response<List<CommentModel>> response) {
                commentModelList.addAll(response.body());
                commentsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CommentModel>> call, Throwable t) {

            }
        });
    }

    private void loadAdapterAndRecyclerView() {
        commentsAdapter = new CommentsAdapter(commentModelList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerViewComments.setLayoutManager(mLayoutManager);
        recyclerViewComments.setItemAnimator(new DefaultItemAnimator());
        recyclerViewComments.setAdapter(commentsAdapter);

        recyclerViewComments.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerViewComments, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                // TODO: what happens when item_comment gets clicked?
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public int getPollID() {
        return pollID;
    }

    public void setPollID(int pollID) {
        this.pollID = pollID;
    }

    @OnClick(R.id.buttonComment)
    public void makeComment() {
        ApiClient.apiService().makeComment(String.valueOf(getPollID()), editTextComment.getText().toString(), CurrentUser.apiToken()).enqueue(new Callback<SimpleResponseModel>() {
            @Override
            public void onResponse(Call<SimpleResponseModel> call, Response<SimpleResponseModel> response) {
                if (!response.body().getError()) {
                    // Success

                }
            }

            @Override
            public void onFailure(Call<SimpleResponseModel> call, Throwable t) {

            }
        });
    }
}
