package com.example.jbbmobile.view;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jbbmobile.R;
import com.example.jbbmobile.model.Professor;

import java.util.ArrayList;
import android.os.Handler;

public class ProfessorFragment extends Fragment {

    private View view;

    private ImageView professorImage;
    private TextView professorDialog;

    private ArrayList<String> dialogs;
    private ArrayList<Drawable> drawables;
    private Drawable drawable;

    private String currentDialog;
    private String dialog;

    Runnable runnable;
    Handler handler;

    public ProfessorFragment() {
        currentDialog = "";
        dialog = "";
        // Required empty public constructor
    }

    /*
    public static ProfessorFragment newInstance(ArrayList<String> dialogs, ArrayList<Drawable> drawables) {
        ProfessorFragment fragment = new ProfessorFragment();
        Bundle args = new Bundle();
        args.putSerializable("dialogs", dialogs);
        args.putSerializable("drawables", drawables);

        fragment.setArguments(args);
        return fragment;
    }
    */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_professor, container, false);

        currentDialog = "";
        dialog = dialogs.get(0);
        dialogs.remove(0);

        professorDialog = (TextView) view.findViewById(R.id.professorDialog);
        professorDialog.setText(currentDialog);

        drawable = drawables.get(0);
        drawables.remove(0);

        professorImage = (ImageView) view.findViewById(R.id.professorImage);
        professorImage.setImageDrawable(drawable);

        view.findViewById(R.id.professor).setOnClickListener(onClick());

        createHandler();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private View.OnClickListener onClick(){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(dialog.isEmpty()){
                    if(dialogs.isEmpty()){
                        stopHandler();
                        getActivity().getSupportFragmentManager().beginTransaction().remove(getFragment()).commit();
                    }else{
                        currentDialog = "";
                        dialog = dialogs.get(0);
                        dialogs.remove(0);
                        professorDialog.setText(currentDialog);

                        if(!drawables.isEmpty()) {
                            drawable = drawables.get(0);
                            drawables.remove(0);
                            professorImage.setImageDrawable(drawable);
                        }
                    }
                }else{
                    currentDialog += dialog;
                    dialog = "";
                    professorDialog.setText(currentDialog);
                }
            }
        };

        return listener;
    }

    private void createHandler(){
        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                if(!dialog.isEmpty()){
                    currentDialog += dialog.charAt(0);
                    dialog = dialog.substring(1);

                    professorDialog.setText(currentDialog);
                }
                handler.postDelayed(this, 50);
            }
        };

        handler.post(runnable);
    }

    private void stopHandler(){
        handler.removeCallbacks(runnable);
    }

    public ArrayList<String> getDialogs() {
        return dialogs;
    }

    public void setDialogs(ArrayList<String> dialogs) {
        this.dialogs = dialogs;
    }

    public ArrayList<Drawable> getDrawables() {
        return drawables;
    }

    public void setDrawables(ArrayList<Drawable> drawables) {
        this.drawables = drawables;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public Fragment getFragment(){
        return this;
    }
}
