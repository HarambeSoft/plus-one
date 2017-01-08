package harambesoft.com.plusone.views;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import harambesoft.com.plusone.App;
import harambesoft.com.plusone.R;
import harambesoft.com.plusone.models.OptionModel;

/**
 * Created by isa on 1/8/17.
 */



public class NewChoiceItemView extends LinearLayout {
    @BindView(R.id.buttonAddImage)
    public ImageButton buttonAddImage;

    @BindView(R.id.editTextContent)
    public EditText editTextContent;

    public NewChoiceItemView(Context context) {
        super(context);
        View view = View.inflate(context, R.layout.item_new_choice, this);
        ButterKnife.bind(this, view);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        setLayoutParams(params);
    }

    public String getText() {
        return editTextContent.getText().toString();
    }
}
