package harambesoft.com.plusone.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import harambesoft.com.plusone.R;
import harambesoft.com.plusone.models.OptionModel;
import harambesoft.com.plusone.models.PollModel;

/**
 * Created by isa on 1/8/17.
 */

public class ChartFragment extends Fragment {
    public static final String TAG = ChartFragment.class.getName();

    @BindView(R.id.pieChart)
    PieChart pieChart;

    private PollModel poll;

    public static Fragment newInstance(PollModel poll) {
        ChartFragment fragment = new ChartFragment();
        fragment.setPoll(poll);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        drawPie();
    }

    private void drawPie() {
        ArrayList<PieEntry> entries = new ArrayList<>();

        for (OptionModel choice: poll.getOptionModels()) {
            entries.add(new PieEntry(Float.valueOf(choice.getVote()), choice.getContent()));
        }

        PieDataSet dataSet = new PieDataSet(entries, " # of votes");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
    }

    public PollModel getPoll() {
        return poll;
    }

    public void setPoll(PollModel poll) {
        this.poll = poll;
    }
}
