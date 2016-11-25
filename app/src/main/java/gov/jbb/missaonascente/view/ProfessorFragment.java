package gov.jbb.missaonascente.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import gov.jbb.missaonascente.R;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ProfessorFragment extends Fragment {

    private View view;

    private ImageView professorImage;
    private TextView professorDialog;

    private ArrayList<String> dialogs;
    private ArrayList<Drawable> drawables;
    private Drawable drawable;

    private String currentDialog;
    private String dialog;

    private Timer timer;
    private TimerTask timerTask;

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
                        getActivity().getSupportFragmentManager().beginTransaction().remove(getFragment()).commitNow();
                    }else{
                        currentDialog = "";
                        dialog = dialogs.get(0);
                        dialogs.remove(0);
                        professorDialog.setText(currentDialog);
                    }
                }else{
                    currentDialog += dialog;
                    dialog = "";
                    professorDialog.setText(currentDialog);
                }

                if(!drawables.isEmpty()) {
                    drawable = drawables.get(0);
                    drawables.remove(0);
                    professorImage.setImageDrawable(drawable);
                }
            }
        };

        return listener;
    }

    /*
    private void ativaTimer(){
        task = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                    }
                });
            }};

        timerAtual.schedule(task, 300, 300);
    }
*/

    private void createHandler(){
        handler = new Handler();
        timer = new Timer();

        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(!dialog.isEmpty()){
                            currentDialog += dialog.charAt(0);
                            dialog = dialog.substring(1);

                            professorDialog.setText(currentDialog);
                        }
                    }
                });
            }
        };

        timer.schedule(timerTask, 0, 5);
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
