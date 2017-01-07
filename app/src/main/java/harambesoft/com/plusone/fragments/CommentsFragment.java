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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import harambesoft.com.plusone.CurrentUser;
import harambesoft.com.plusone.R;
import harambesoft.com.plusone.adapters.CommentsAdapter;
import harambesoft.com.plusone.helpers.RecyclerTouchListener;
import harambesoft.com.plusone.models.CommentModel;
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

    private CommentsAdapter commentsAdapter;
    private List<CommentModel> commentModelList = new ArrayList<>();

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
}
