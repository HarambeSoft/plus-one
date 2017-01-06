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
import harambesoft.com.plusone.R;
import harambesoft.com.plusone.models.CommentModel;

/**
 * Created by isa on 1/6/17.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>{
    private List<CommentModel> commentList;

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewContentComment)
        public TextView textViewContentComment;

        @BindView(R.id.textViewUserNameComment)
        public TextView textViewUserNameComment;

        @BindView(R.id.imageViewUserImageComment)
        public ImageView imageViewUserImageComment;

        public CommentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public CommentsAdapter(List<CommentModel> commentList) {
        this.commentList = commentList;
    }

    @Override
    public CommentsAdapter.CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);

        return new CommentsAdapter.CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommentsAdapter.CommentViewHolder holder, int position) {
        CommentModel comment = commentList.get(position);
        holder.textViewContentComment.setText(comment.getContent());
        holder.textViewUserNameComment.setText(comment.getUserId());

        //TODO: handle upvote/downvote click events here, or somewhere near
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
}
