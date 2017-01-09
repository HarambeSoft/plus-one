package harambesoft.com.plusone.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.ganfra.materialspinner.MaterialSpinner;
import harambesoft.com.plusone.App;
import harambesoft.com.plusone.Constants;
import harambesoft.com.plusone.CurrentUser;
import harambesoft.com.plusone.R;
import harambesoft.com.plusone.helpers.ActivityStream;
import harambesoft.com.plusone.models.CategoryModel;
import harambesoft.com.plusone.models.PollModel;
import harambesoft.com.plusone.models.RequestOptionModel;
import harambesoft.com.plusone.models.ResponseModel;
import harambesoft.com.plusone.models.SimpleResponseModel;
import harambesoft.com.plusone.services.ApiClient;
import harambesoft.com.plusone.views.BackPressedListener;
import harambesoft.com.plusone.views.NewChoiceItemView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by isa on 18.12.2016.
 */
public class NewPollFragment extends Fragment implements BackPressedListener {
    public static final String TAG = NewPollFragment.class.getName();
    private static final int FILE_SELECT_CODE_FOR_CHOICE = 0;

    private HashMap<Integer, Uri> futureUploadUris = new HashMap<>();
    private int lastImageRequestOfChoice = -1;
    private Snackbar snackbar;

    @BindView(R.id.editTextQuestion)
    EditText editTextQuestion;
    @BindView(R.id.editTextDuration)
    EditText editTextDuration;
    @BindView(R.id.editTextDiameter)
    EditText editTextDiameter;
    @BindView(R.id.spinnerCategory)
    Spinner spinnerCategory;
    @BindView(R.id.spinnerOptionType)
    MaterialSpinner spinnerOptionType;
    @BindView(R.id.spinnerPollType)
    Spinner spinnerPollType;
    @BindView(R.id.layoutChoicesNewPoll)
    LinearLayout layoutChoicesNewPoll;
    @BindView(R.id.buttonCreate)
    Button buttonCreate;

    private HashMap<String, Integer> hashMapCategories;
    private ArrayList<NewChoiceItemView> choicesList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newpoll, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);

        loadSpinners();
        loadCategories();
        addNewChoice();

        return view;
    }

    private void loadSpinners() {
        String arrayOptionType[] = {"Multi", "Single"};
        ArrayAdapter<String> adapterOptionType = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, arrayOptionType);
        spinnerOptionType.setAdapter(adapterOptionType);

        String arrayPollType[] = {"Global", "Local"};
        ArrayAdapter<String> adapterPollType = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, arrayPollType);
        spinnerPollType.setAdapter(adapterPollType);
    }

    public void loadCategories() {
        hashMapCategories = new HashMap<>();
        ApiClient.apiService().getCategories(CurrentUser.apiToken()).enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                String[] arrayCategories = new String[response.body().size()];
                for (int i = 0; i < response.body().size(); i++) {
                    String categoryName = response.body().get(i).getName();
                    hashMapCategories.put(categoryName, response.body().get(i).getId());
                    arrayCategories[i] = categoryName;
                }
                ArrayAdapter<String> adapterCategories = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, arrayCategories);
                spinnerCategory.setAdapter(adapterCategories);

            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {

            }
        });
    }

    private void addNewChoice() {
        //TODO: add remove button
        final NewChoiceItemView newChoiceItemView = new NewChoiceItemView(getActivity());

        // EVENTS
        newChoiceItemView.editTextContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    int n = choicesList.indexOf(newChoiceItemView);
                    if (n + 1 == choicesList.size()) { // is this the last element
                        addNewChoice();
                    }
                }
            }
        });

        // Show image select dialog
        newChoiceItemView.buttonAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                try {
                    startActivityForResult(
                            Intent.createChooser(intent, "Select a File to Upload"),
                            FILE_SELECT_CODE_FOR_CHOICE);
                } catch (android.content.ActivityNotFoundException ex) {
                    // Potentially direct the user to the Market with a Dialog
                    Toast.makeText(App.context, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
                }

                int n = choicesList.indexOf(newChoiceItemView);
                lastImageRequestOfChoice = n;
            }
        });

        choicesList.add(newChoiceItemView);
        layoutChoicesNewPoll.addView(newChoiceItemView);
    }

    private void prepareForCreatingPoll() {
        buttonCreate.setEnabled(false);
        snackbar = Snackbar.make(buttonCreate, "Please wait...", Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

    private void pollAdded(int pollID) {
        //TODO: wait for images to upload, if any
        App.showPoll(pollID);
        snackbar.dismiss();
    }

    public void createPoll() {
        prepareForCreatingPoll();

        ApiClient.apiService().createPoll(editTextQuestion.getText().toString(),
                spinnerPollType.getSelectedItem().toString().toLowerCase(),
                spinnerOptionType.getSelectedItem().toString().toLowerCase(),
                editTextDuration.getText().toString(),
                CurrentUser.latitude(),
                CurrentUser.longitude(),
                editTextDiameter.getText().toString(),
                hashMapCategories.get(spinnerCategory.getSelectedItem().toString()).toString(),
                CurrentUser.apiToken()).
                enqueue(new Callback<ResponseModel<PollModel>>() {
                    @Override
                    public void onResponse(Call<ResponseModel<PollModel>> call, Response<ResponseModel<PollModel>> response) {
                        Log.d(TAG, "Poll added.");
                        Log.d(TAG, response.body().getMessage());

                        if (!response.body().getError()) {
                            addOptionsToPoll(response.body().getResponse().getId().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel<PollModel>> call, Throwable t) {
                        Log.d(TAG, "Can't create poll.");
                        Log.e(TAG, "Failed" + t.getLocalizedMessage() + " " + call.request());
                    }
                });
    }

    private void addOptionsToPoll(final String pollID) {
        List<RequestOptionModel> requestOptionModels = new ArrayList<>();
        for (NewChoiceItemView newChoiceItemView: choicesList) {
            String content = newChoiceItemView.getText();
            if (!content.isEmpty()) {
                RequestOptionModel requestOptionModel = new RequestOptionModel();
                requestOptionModel.setContent(content);
                requestOptionModels.add(requestOptionModel);
            }
        }

        ApiClient.apiService().createOption(pollID,
                requestOptionModels).enqueue(new Callback<ResponseModel<List<Integer>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<Integer>>> call, Response<ResponseModel<List<Integer>>> response) {
                Log.d(TAG, "Options added to poll.");
                if (!response.body().getError()) {
                    // Upload images according their option_id's
                    List<Integer> optionIDList = response.body().getResponse();
                    int i = 0;
                    for (int optionID: optionIDList) {
                        if (futureUploadUris.containsKey(i)) {
                            uploadImages(futureUploadUris.get(i),optionID);
                        }
                        i++;
                    }
                    pollAdded(Integer.valueOf(pollID));
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<Integer>>> call, Throwable t) {
                Log.d(TAG, "Can't add options to poll.");
                Log.e(TAG, "Failed " + t.getLocalizedMessage() + " " + call.request());
            }
        });
    }

    public void uploadImages(Uri uri, int id) {
        snackbar = Snackbar.make(buttonCreate, "Uploading images...", Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
        try {
            InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();


            int nRead;
            byte[] imageData = new byte[Constants.MAX_IMG_SIZE];
            while ((nRead = inputStream.read(imageData, 0, imageData.length)) != -1)
                buffer.write(imageData, 0, nRead);
            byte[] resultData = buffer.toByteArray();
            buffer.flush();


            StorageReference newImageRef = App.getFirebaseStorageRef().child("poll_images/" + id + ".jpg");

            UploadTask uploadTask = newImageRef.putBytes(resultData);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Log.d("FIREBASE", "UPLOADED");
                    snackbar.dismiss();
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_SELECT_CODE_FOR_CHOICE) {
            // Upload image to Firebase
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                futureUploadUris.put(lastImageRequestOfChoice, uri);

                for (int i = 0; i < choicesList.size(); ++i) {
                    if (futureUploadUris.containsKey(i)) {
                        choicesList.get(i).buttonAddImage.setImageResource(R.drawable.ic_menu_gallery);
                    }
                }

            } else {
                lastImageRequestOfChoice = -1;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.buttonCreate)
    public void onClickCreatePoll() {
        createPoll();
    }

    @OnClick(R.id.buttonCancel)
    public void onClickCancelPoll() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new ActivityStreamFragment(), ActivityStreamFragment.TAG)
                .commit();
    }

    @Override
    public void onBackPressed() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new ActivityStreamFragment(), ActivityStreamFragment.TAG)
                .commit();
    }
}
