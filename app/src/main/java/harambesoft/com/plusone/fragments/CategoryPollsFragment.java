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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import harambesoft.com.plusone.App;
import harambesoft.com.plusone.CurrentUser;
import harambesoft.com.plusone.R;
import harambesoft.com.plusone.adapters.CategoriesAdapter;
import harambesoft.com.plusone.adapters.PollsAdapter;
import harambesoft.com.plusone.helpers.RecyclerTouchListener;
import harambesoft.com.plusone.models.CategoryModel;
import harambesoft.com.plusone.models.PollModel;
import harambesoft.com.plusone.services.ApiClient;
import harambesoft.com.plusone.views.BackPressedListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by isa on 1/8/17.
 */

public class CategoryPollsFragment extends Fragment {
    public static final String TAG = CategoryPollsFragment.class.getName();

    @BindView(R.id.recyclerViewCategoryPolls)
    RecyclerView recyclerViewCategoryPolls;

    private PollsAdapter pollsAdapter;
    private List<PollModel> pollModelList = new ArrayList<>();

    public int categoryID;

    public static Fragment newInstance(int categoryID) {
        CategoryPollsFragment fragment = new CategoryPollsFragment();
        fragment.categoryID = categoryID;

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categorypolls, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        loadAdapterAndRecyclerView();
        loadPolls();
    }

    private void loadAdapterAndRecyclerView() {
        pollsAdapter = new PollsAdapter(pollModelList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerViewCategoryPolls.setLayoutManager(mLayoutManager);
        recyclerViewCategoryPolls.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCategoryPolls.setAdapter(pollsAdapter);

        recyclerViewCategoryPolls.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerViewCategoryPolls, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                PollModel poll = pollModelList.get(position);
                App.showPoll(poll.getId());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void loadPolls() {
        ApiClient.apiService().getNearPolls(CurrentUser.latitude(), CurrentUser.longitude(), "100", CurrentUser.apiToken()).enqueue(new Callback<List<PollModel>>() {
            @Override
            public void onResponse(Call<List<PollModel>> call, Response<List<PollModel>> response) {
                for(PollModel poll: response.body()) {
                    int pollCategory = Integer.valueOf(poll.getCategoryId());
                    if (pollCategory == categoryID) {
                        pollModelList.add(poll);
                    }
                }

                if (pollModelList.size() == 0)
                    Toast.makeText(App.context, "Nothing here.", Toast.LENGTH_SHORT).show();

                pollsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<PollModel>> call, Throwable t) {

            }
        });
    }
}