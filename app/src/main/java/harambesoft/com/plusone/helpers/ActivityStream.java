package harambesoft.com.plusone.helpers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import harambesoft.com.plusone.App;
import harambesoft.com.plusone.models.ActivityModel;

/**
 * Created by isa on 28.12.2016.
 */

public class ActivityStream {
    private final static String TAG = "ActivityStream";

    public static List<ActivityModel> get() {
        List<ActivityModel> activityList;
        Type listType = new TypeToken<ArrayList<ActivityModel>>(){}.getType();

        String json = App.settings().getString(ActivityStream.TAG, "");
        activityList = new Gson().fromJson(json, listType);

        return activityList != null ? activityList:(new ArrayList<ActivityModel>());
    }

    public static void add(String title, String body) {
        ActivityModel activityModel = new ActivityModel();
        activityModel.setTitle(title);
        activityModel.setBody(body);

        add(activityModel);
    }

    public static void add(String title, String body, int pollID) {
        ActivityModel activityModel = new ActivityModel();
        activityModel.setTitle(title);
        activityModel.setBody(body);
        activityModel.setPollID(pollID);

        add(activityModel);
    }

    public static void add(ActivityModel activityModel) {
        List<ActivityModel> activityList = ActivityStream.get();
        activityList.add(activityModel);

        String activityJson = new Gson().toJson(activityList);
        App.settings().edit().putString(ActivityStream.TAG, activityJson).apply();
    }
}
