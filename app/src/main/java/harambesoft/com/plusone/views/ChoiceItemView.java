package harambesoft.com.plusone.views;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import harambesoft.com.plusone.R;
import harambesoft.com.plusone.models.OptionModel;

/**
 * Created by isa on 1/7/17.
 */


public class ChoiceItemView extends LinearLayout {
    OptionModel option;

    @BindView(R.id.textViewChoice)
    TextView textViewChoice;

    public ChoiceItemView(Context context, OptionModel option) {
        super(context);
        View view = View.inflate(context, R.layout.item_choice, this);
        ButterKnife.bind(this, view);


        // Set attributes
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT,
                1.0f);
        params.setMargins(20, 10, 20, 10);

        this.setLayoutParams(params);
        this.setBackgroundColor(Color.WHITE);
        this.setOrientation(LinearLayout.HORIZONTAL);

        this.option = option;
        textViewChoice.setText(option.getContent());
    }
}