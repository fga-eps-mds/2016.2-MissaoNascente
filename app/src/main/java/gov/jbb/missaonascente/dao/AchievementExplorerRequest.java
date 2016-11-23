package gov.jbb.missaonascente.dao;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.jbb.missaonascente.model.Achievement;

public class AchievementExplorerRequest {

    private static final String ACHIEVEMENT_EXPLORER_REQUEST_URL = "http://rogerlenke.site88.net/AchievementExplorer.php";
    private static final String ACHIEVEMENT_EXPLORER_RETRIEVE_REQUEST_URL = "http://rogerlenke.site88.net/GetAchievementsExplorer.php";
    private static final String ACHIEVEMENT_EXPLORER_SEND_REQUEST_URL = "http://rogerlenke.site88.net/SendAchievementsExplorer.php";
    private Map<String,String> params;
    private String email;
    private int idAchievement;

    public AchievementExplorerRequest(String email, int idAchievement){
        this.email = email;
        this.idAchievement = idAchievement;
    }

    public AchievementExplorerRequest(String email){
        this.email = email;
    }

    public void requestUpdateAchievements(final Context context, final Callback callback){
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    callback.callbackResponse(jsonObject.getBoolean("success"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ACHIEVEMENT_EXPLORER_REQUEST_URL, listener, null){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                params = new HashMap<>();

                params.put("idAchievement",String.valueOf(idAchievement));
                params.put("email",email);
                Log.i("======",email+idAchievement);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    public void requestRetrieveAchievements(final Context context, final Callback callback){
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray= new JSONArray(response);
                    List<Achievement> achievements = new ArrayList<>();
                    for(int i = 0; i < jsonArray.length(); i++){
                        int idAchievement = jsonArray.getJSONObject(i).getInt("idAchievement");
                        Achievement achievement = new Achievement(idAchievement);
                        achievements.add(achievement);
                    }
                    callback.callbackResponse(achievements);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ACHIEVEMENT_EXPLORER_RETRIEVE_REQUEST_URL, listener, null){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                params = new HashMap<>();
                params.put("email",email);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    public void sendAchievementsExplorer(final Context context){
        AchievementDAO achievementDAO = new AchievementDAO(context);

        final List<Achievement> achievements = achievementDAO.findAllExplorerAchievements(this.email);

        if(achievements.size() != 0){

            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("RESPONSE: ",response);
                }
            };

            StringRequest stringRequest = new StringRequest(Request.Method.POST, ACHIEVEMENT_EXPLORER_SEND_REQUEST_URL, listener, null){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    JSONArray jsonArray = new JSONArray();

                    for(Achievement achievement : achievements){
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("idAchievement", achievement.getIdAchievement());
                                jsonObject.put("email", email);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            jsonArray.put(jsonObject);
                    }

                    params = new HashMap<>();
                    params.put("achievements", jsonArray.toString());

                    return params;
                }
            };

            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(stringRequest);
        }
    }

    public interface Callback{
        void callbackResponse(boolean response);
        void callbackResponse(List<Achievement> achievements);
    }
}