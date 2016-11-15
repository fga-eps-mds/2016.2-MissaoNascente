package gov.jbb.missaonascente.dao;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import gov.jbb.missaonascente.model.Achievement;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AchievementRequest {
    private static final String ACHIEVEMENT_REQUEST_URL = "http://rogerlenke.site88.net/Achievement.php";
    private Map<String,String> params;


    public AchievementRequest(){

    }

    public void request(final Context context, final AchievementRequest.Callback callback) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<Achievement> achievementList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length() ; i++){
                        int idAchievement = jsonArray.getJSONObject(i).getInt(AchievementDAO.COLUMN_ID_ACHIEVEMENT);
                        String nameAchievement = jsonArray.getJSONObject(i).getString(AchievementDAO.COLUMN_NAME_ACHIEVEMENT);
                        String descriptionAchievement = jsonArray.getJSONObject(i).getString(AchievementDAO.COLUMN_DESCRIPTION_ACHIEVEMENT);
                        int quantity = jsonArray.getJSONObject(i).getInt(AchievementDAO.COLUMN_QUANTITY);
                        int keys = jsonArray.getJSONObject(i).getInt(AchievementDAO.COLUMN_KEYS);
                        achievementList.add(new Achievement(idAchievement, nameAchievement, descriptionAchievement, quantity, keys));
                    }
                    callback.callbackResponse(achievementList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, ACHIEVEMENT_REQUEST_URL, responseListener, null){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                params = new HashMap<>();

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    public interface Callback{
        void callbackResponse(List <Achievement> achievementsList);
    }
}
