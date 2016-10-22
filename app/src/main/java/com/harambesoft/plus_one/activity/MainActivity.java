package com.harambesoft.plus_one.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.harambesoft.plus_one.R;
import com.harambesoft.plus_one.adapter.ViewPagerAdapter;
import com.harambesoft.plus_one.fragment.CategoryListFragment;
import com.harambesoft.plus_one.fragment.MapFragment;
import com.harambesoft.plus_one.fragment.PollListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.tabs)
    protected TabLayout tabLayout;
    @BindView(R.id.viewpager)
    protected ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(CategoryListFragment.newInstance(), getString(R.string.category));
        adapter.addFragment(PollListFragment.newInstance(), getString(R.string.poll));
        adapter.addFragment(MapFragment.newInstance(), getString(R.string.map));

        viewPager.setAdapter(adapter);
    }
}
