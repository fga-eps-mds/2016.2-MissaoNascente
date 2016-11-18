package gov.jbb.missaonascente.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import gov.jbb.missaonascente.R;
import gov.jbb.missaonascente.controller.BooksController;
import gov.jbb.missaonascente.controller.ProfessorController;

public class TutorialScreenActivity extends AppCompatActivity implements View.OnClickListener{
    private ProfessorFragment professorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_screen);

        String initialMessage = "Bem vindo ao nosso tutorial!";
        String initialMessage1 = "Agora você irá aprender melhor como utilizar o aplicativo";
        callProfessor(initialMessage, initialMessage1);
    }

    @Override
    public void onClick(View view) {

    }

    public void callProfessor(String message, String message2) {
        ProfessorController professorController = new ProfessorController();
        BooksController book = new BooksController();
        ArrayList<String> s = new ArrayList<>();
        s.add(message);
        s.add(message2);

        book.currentPeriod();
        int period = book.getCurrentPeriod();

        switch (period) {
            case 1:
                Drawable drawable1 = ResourcesCompat.getDrawable(getResources(), R.drawable.professor_teste_1, null);
                professorFragment = professorController.createProfessorFragment(s, drawable1);
                break;
            case 2:
                Drawable drawable2 = ResourcesCompat.getDrawable(getResources(), R.drawable.professor_teste_2, null);
                professorFragment = professorController.createProfessorFragment(s, drawable2);
                break;
            case 3:
                Drawable drawable3 = ResourcesCompat.getDrawable(getResources(), R.drawable.professor_teste_3, null);
                professorFragment = professorController.createProfessorFragment(s, drawable3);
                break;
            default:
                break;
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.professor_fragment, professorFragment, "ProfessorFragment");
        fragmentTransaction.commitNow();
    }

    private void leaveTutorial() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.signOut);
        alert.setMessage("Deseja sair do tutorial?");
        alert.setPositiveButton(R.string.yesMessage, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent mainScreenIntent = new Intent(TutorialScreenActivity.this, MainScreenActivity.class);
                TutorialScreenActivity.this.startActivity(mainScreenIntent);
                finish();
            }
        });

        alert.setNegativeButton(R.string.noMessage, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alert.show();
    }

    @Override
    public void onBackPressed() {
        leaveTutorial();
    }
}
