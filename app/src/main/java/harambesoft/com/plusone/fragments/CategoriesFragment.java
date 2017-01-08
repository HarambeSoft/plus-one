package harambesoft.com.plusone.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
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
import harambesoft.com.plusone.helpers.RecyclerTouchListener;
import harambesoft.com.plusone.models.CategoryModel;
import harambesoft.com.plusone.services.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gizemfitoz on 13/12/16.
 */

public class CategoriesFragment extends Fragment {
    public static final String TAG = CategoriesFragment.class.getName();

    @BindView(R.id.recyclerViewCategories)
    RecyclerView recyclerViewCategories;

    private CategoriesAdapter categoriesAdapter;
    private List<CategoryModel> categoryModelList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        loadAdapterAndRecyclerView();
        loadCategories();
    }

    private void loadAdapterAndRecyclerView() {
        categoriesAdapter = new CategoriesAdapter(categoryModelList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerViewCategories.setLayoutManager(mLayoutManager);
        recyclerViewCategories.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCategories.setAdapter(categoriesAdapter);

        recyclerViewCategories.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerViewCategories, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                CategoryModel category = categoryModelList.get(position);
                App.showPolls(category.getId());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public void loadCategories() {
        ApiClient.apiService().getCategories(CurrentUser.apiToken()).enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                categoryModelList.clear();
                categoryModelList.addAll(response.body());
                categoriesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {

            }
        });
    }
}
