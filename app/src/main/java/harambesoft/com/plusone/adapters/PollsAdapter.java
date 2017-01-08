package harambesoft.com.plusone.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import harambesoft.com.plusone.App;
import harambesoft.com.plusone.R;
import harambesoft.com.plusone.models.CategoryModel;
import harambesoft.com.plusone.models.OptionModel;
import harambesoft.com.plusone.models.PollModel;

/**
 * Created by isa on 1/8/17.
 */

public class PollsAdapter extends RecyclerView.Adapter<PollsAdapter.PollViewHolder> {
    private List<PollModel> pollList;

    public class PollViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewPollQuestionPollItem)
        public TextView textViewPollQuestionPollItem;

        @BindView(R.id.textViewTotalVoteCount)
        public TextView textViewTotalVoteCount;

        @BindView(R.id.textViewCreatedAt)
        public TextView textViewCreatedAt;

        public PollViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public PollsAdapter(List<PollModel> pollList) {
        this.pollList = pollList;
    }

    @Override
    public PollsAdapter.PollViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_poll, parent, false);

        return new PollsAdapter.PollViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PollsAdapter.PollViewHolder holder, int position) {
        PollModel poll = pollList.get(position);
        holder.textViewPollQuestionPollItem.setText(poll.getQuestion());
        holder.textViewCreatedAt.setText(poll.getStat());

        int totalVote = 0;
        if (poll.getOptionModels() != null)
            for(OptionModel option: poll.getOptionModels())
                totalVote += Integer.valueOf(option.getVote());

        holder.textViewTotalVoteCount.setText(String.valueOf(totalVote));
    }

    @Override
    public int getItemCount() {
        return pollList.size();
    }
}