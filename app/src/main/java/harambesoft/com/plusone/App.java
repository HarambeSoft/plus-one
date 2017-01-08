package harambesoft.com.plusone;

import android.app.Application;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import harambesoft.com.plusone.fragments.CommentsFragment;
import harambesoft.com.plusone.fragments.PollFragment;

/**
 * Created by isa on 28.12.2016.
 */

public class App extends Application {
    public static Context context;
    static MainActivity mainActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static SharedPreferences settings() {
        return PreferenceManager.getDefaultSharedPreferences(App.context);
    }

    public static MainActivity mainActivity() {
        return mainActivity;
    }

    public static void setMainActivity(MainActivity activity) {
        mainActivity = activity;
    }

    public static FragmentManager getFragmentManager() {
        return mainActivity.getFragmentManager();
    }

    public static void showPoll(int pollID) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, PollFragment.newInstance(pollID), PollFragment.TAG)
                .commit();
    }

    public static void showComments(int pollID) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, CommentsFragment.newInstance(pollID), CommentsFragment.TAG)
                .commit();
    }

    public static StorageReference getFirebaseStorageRef() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        return storage.getReferenceFromUrl("gs://plus-one-59ee5.appspot.com");
    }
}