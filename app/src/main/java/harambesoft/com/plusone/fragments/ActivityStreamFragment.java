package harambesoft.com.plusone.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import harambesoft.com.plusone.App;
import harambesoft.com.plusone.R;
import harambesoft.com.plusone.adapters.ActivitiesAdapter;
import harambesoft.com.plusone.adapters.CategoriesAdapter;
import harambesoft.com.plusone.helpers.ActivityStream;
import harambesoft.com.plusone.helpers.RecyclerTouchListener;
import harambesoft.com.plusone.models.ActivityModel;
import harambesoft.com.plusone.models.CategoryModel;

/**
 * Created by isa on 12.12.2016.
 */

public class ActivityStreamFragment extends Fragment {
    public static final String TAG = ActivityStreamFragment.class.getName();

    @BindView(R.id.recyclerViewActivityStream)
    RecyclerView recyclerViewActivityStream;

    private ActivitiesAdapter activitiesAdapter;
    private List<ActivityModel> activityModelList = new ArrayList<>();

    public static Fragment newInstance() {
        return new ActivityStreamFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activity_stream, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        if (ActivityStream.get().isEmpty()) {
            Toast.makeText(getActivity(), "Looks like you don't have any recent activity.", Toast.LENGTH_SHORT).show();
        }

        activityModelList.addAll(ActivityStream.get());
        loadAdapterAndRecyclerView();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    private void loadAdapterAndRecyclerView() {
        activitiesAdapter = new ActivitiesAdapter(activityModelList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewActivityStream.setLayoutManager(mLayoutManager);
        recyclerViewActivityStream.setItemAnimator(new DefaultItemAnimator());
        recyclerViewActivityStream.setAdapter(activitiesAdapter);

        recyclerViewActivityStream.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerViewActivityStream, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ActivityModel activity = activityModelList.get(position);

                if (activity.hasPoll())
                    App.showPoll(activity.getPollID());
                else if (activity.hasComment()) { /*FIXME: show comment*/}

                Toast.makeText(getActivity(), activity.getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
}