package harambesoft.com.plusone.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import harambesoft.com.plusone.R;
import harambesoft.com.plusone.models.ActivityModel;

/**
 * Created by isa on 28.12.2016.
 */

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ActivityViewHolder> {
    private List<ActivityModel> activityList;

    public class ActivityViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewActivityTitle)
        public TextView textViewActivityTitle;

        @BindView(R.id.textViewActivityBody)
        public TextView textViewActivityBody;

        public ActivityViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public ActivitiesAdapter(List<ActivityModel> categoryList) {
        this.activityList = categoryList;
    }

    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_activity, parent, false);

        return new ActivityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ActivityViewHolder holder, int position) {
        ActivityModel activity = activityList.get(position);
        holder.textViewActivityTitle.setText(activity.getTitle());
        holder.textViewActivityBody.setText(activity.getBody());
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }
}
