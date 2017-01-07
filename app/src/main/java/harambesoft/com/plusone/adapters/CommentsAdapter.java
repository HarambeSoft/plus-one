package harambesoft.com.plusone.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import harambesoft.com.plusone.CurrentUser;
import harambesoft.com.plusone.R;
import harambesoft.com.plusone.models.CommentModel;
import harambesoft.com.plusone.models.SimpleResponseModel;
import harambesoft.com.plusone.services.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by isa on 1/6/17.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>{
    private List<CommentModel> commentList;

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        private int commentID;

        @BindView(R.id.textViewContentComment)
        public TextView textViewContentComment;

        @BindView(R.id.textViewUserNameComment)
        public TextView textViewUserNameComment;

        @BindView(R.id.imageViewUserImageComment)
        public ImageView imageViewUserImageComment;

        @BindView(R.id.buttonUpVote)
        public Button buttonUpVote;

        @BindView(R.id.buttonDownVote)
        public Button buttonDownVote;

        @BindView(R.id.textViewUpVoteCount)
        public TextView textViewUpVoteCount;

        @BindView(R.id.textViewDownVoteCount)
        public TextView textViewDownVoteCount;

        public CommentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public int getCommentID() {
            return commentID;
        }

        public void setCommentID(int commentID) {
            this.commentID = commentID;
        }

        @OnClick(R.id.buttonUpVote)
        public void upVote() {
            ApiClient.apiService().upVoteComment(String.valueOf(commentID), CurrentUser.apiToken()).enqueue(new Callback<SimpleResponseModel>() {
                @Override
                public void onResponse(Call<SimpleResponseModel> call, Response<SimpleResponseModel> response) {
                    if (!response.body().getError()) {
                        // Success.
                        // TODO: show snackbar?
                        setVote(textViewUpVoteCount);
                    }
                }

                @Override
                public void onFailure(Call<SimpleResponseModel> call, Throwable t) {

                }
            });
        }

        @OnClick(R.id.buttonDownVote)
        public void downVote() {
            ApiClient.apiService().downVoteComment(String.valueOf(commentID), CurrentUser.apiToken()).enqueue(new Callback<SimpleResponseModel>() {
                @Override
                public void onResponse(Call<SimpleResponseModel> call, Response<SimpleResponseModel> response) {
                    if (!response.body().getError()) {
                        // Success.
                        // TODO: show snackbar?
                        setVote(textViewDownVoteCount);
                    }
                }

                @Override
                public void onFailure(Call<SimpleResponseModel> call, Throwable t) {

                }
            });
        }

        private void setVote(TextView voteText) {
            voteText.setText(String.valueOf(Integer.valueOf(voteText.getText().toString()) + 1));
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
        holder.textViewUpVoteCount.setText(comment.getUpVote());
        holder.textViewDownVoteCount.setText(comment.getDownVote());
        holder.setCommentID(comment.getId());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
}
