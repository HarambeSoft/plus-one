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

/**
 * Created by yucel on 16.12.2016.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {
    private List<CategoryModel> categoryList;

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewCategoryName)
        public TextView textViewCategoryName;

        @BindView(R.id.imageViewCategory)
        public ImageView imageViewCategory;

        public CategoryViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public CategoriesAdapter(List<CategoryModel> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);

        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        CategoryModel category = categoryList.get(position);
        holder.textViewCategoryName.setText(category.getName());

        String resName = "cat_" + category.getName().toLowerCase().replace(" ", "_");
        int resourceId = App.context.getResources().getIdentifier(resName, "drawable", App.context.getPackageName());
        holder.imageViewCategory.setImageResource(resourceId);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
