package gov.jbb.missaonascente.dao;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static gov.jbb.missaonascente.BuildConfig.URL_WEBSERVICE;

public class DeleteRequest extends StringRequest {
    private static final String DELETE_REQUEST_URL = URL_WEBSERVICE + "Delete.php";
    private Map<String,String> params;

    public DeleteRequest(String email, Response.Listener<String> listener){
        super(Method.POST, DELETE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
