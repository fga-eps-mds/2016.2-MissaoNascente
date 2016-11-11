package gov.jbb.missaonascente.controller;

import android.content.Context;
import android.util.Log;

import gov.jbb.missaonascente.dao.AlternativeDAO;
import gov.jbb.missaonascente.dao.AlternativeRequest;
import gov.jbb.missaonascente.model.Alternative;

import java.util.List;

public class AlternativeController {
    private List<Alternative> listAlternatives;
    private boolean action = false;

    public AlternativeController(){}

    public void downloadAllAlternatives(final Context context){
        AlternativeRequest alternativeRequest = new AlternativeRequest(new AlternativeRequest.Callback() {
            @Override
            public void callbackResponse(List<Alternative> listAlternatives) {
                if (listAlternatives.size() != 0) {
                    setListAlternatives(listAlternatives);
                    AlternativeDAO alternativeDAO = new AlternativeDAO(context);
                    for (int i = 0; i < listAlternatives.size(); i++) {
                        int insertResponse = alternativeDAO.insertAlternative(listAlternatives.get(i));
                        Log.d("AlternativeRequest", String.valueOf(insertResponse));
                    }
                    setAction(true);
                }
            }
        });

        alternativeRequest.requestAllAlternatives(context);
    }

    public List<Alternative> getListAlternatives() {
        return listAlternatives;
    }

    public void setListAlternatives(List<Alternative> listAlternatives) {
        this.listAlternatives = listAlternatives;
    }

    public boolean isAction() {
        return action;
    }

    public void setAction(boolean action) {
        this.action = action;
    }
}