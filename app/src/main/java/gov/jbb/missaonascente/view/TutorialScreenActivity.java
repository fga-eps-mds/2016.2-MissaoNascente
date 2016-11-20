package gov.jbb.missaonascente.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.makeramen.roundedimageview.RoundedImageView;

import gov.jbb.missaonascente.R;

public class TutorialScreenActivity extends AppCompatActivity implements View.OnClickListener{
    private ShowcaseView showcaseView;
    private int passages = 0;
    private Target energyBarTutorial;
    private Target menuOptionTutorial;
    private Target almanacTutorial;
    private Target mapTutorial;
    private Target registerTutorial;
    private Target leafScoreTutorial;
    private RoundedImageView tutorialCurrentImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_screen);

        initViews();

        initializingShowCaseView();
    }

    @Override
    public void onClick(View view) {
        switch (passages){
            case 0:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.main_screen_image, null));
                showcaseView.setShowcase(energyBarTutorial,true);
                showcaseView.setContentTitle("Barra de Energia");
                showcaseView.setContentText("Essa é a sua energia, acompanhe quantos elemento pode registrar através dela!\nLembre-se que quando estiver sem energia poderá responder uma questão para tentar recuperar uma parte dela!");
                break;
            case 1:
                showcaseView.setShowcase(registerTutorial,true);
                showcaseView.setContentTitle("Botão de Registro");
                showcaseView.setContentText("Aqui você pode registar os elementos! Lembre-se que perde-se energia para cada elemento registrado, tendo ele ou não!");
                break;
            case 2:
                showcaseView.setShowcase(leafScoreTutorial,true);
                showcaseView.setContentTitle("Pontuação");
                showcaseView.setContentText("Aqui você pode acompanhar sua quantidade de pontos, ou seja, a soma de todos os pontos obtidos no decorrer da trilha!");
                break;
            case 3:
                showcaseView.setShowcase(mapTutorial,true);
                showcaseView.setContentTitle("Mapa de Progresso");
                showcaseView.setContentText("Aqui você pode acompanhar seu progresso perante o elementos especiais durante a trillha de acordo com o período!\nLembre-se que ele só aparece no momento em que você registrar o primeiro elemento especial!");
                break;
            case 4:
                showcaseView.setShowcase(almanacTutorial,true);
                showcaseView.setContentTitle("Almanaque");
                showcaseView.setContentText("Aqui você pode acessar todos os seus elementos já registrados!");
                break;
            case 5:
                showcaseView.setShowcase(menuOptionTutorial,true);
                showcaseView.setContentTitle("Mais Opções");
                showcaseView.setContentText("Aqui você pode acessar diversas opções que o aplicativo te oferece!");
                break;
            case 6:
                tutorialCurrentImage.setBackgroundColor(0x00000000);
                tutorialCurrentImage.setBorderWidth((float) 0);
                showcaseView.setTarget(Target.NONE);
                showcaseView.setContentTitle("Tutorial Concluído");
                showcaseView.setContentText("Tenha uma espetacular aventura, Explorador(a)!");
                showcaseView.setStyle(R.style.Final);
                showcaseView.setButtonText("Fim");
                break;
            case 7:
                Intent mainScreenIntent = new Intent(TutorialScreenActivity.this, MainScreenActivity.class);
                TutorialScreenActivity.this.startActivity(mainScreenIntent);
                finish();
                break;
        }
        passages++;
    }

    private void initializingShowCaseView(){
        showcaseView = new ShowcaseView.Builder(this)
                .setTarget(Target.NONE)
                .setOnClickListener(this)
                .setContentTitle("Tutorial")
                .setContentText(R.string.initialMessageTutorial)
                .setStyle(R.style.Transparency)
                .build();

        showcaseView.setButtonText("próximo");
    }

    private void initViews(){
        this.tutorialCurrentImage = (RoundedImageView) findViewById(R.id.tutorial_current_image);
        this.energyBarTutorial = new ViewTarget(R.id.energyBarTutorial, this);
        this.registerTutorial = new ViewTarget(R.id.registerTutorial, this);
        this.leafScoreTutorial = new ViewTarget(R.id.leafScoreTutorial, this);
        this.mapTutorial = new ViewTarget(R.id.mapTutorial, this);
        this.almanacTutorial = new ViewTarget(R.id.almanacTutorial, this);
        this.menuOptionTutorial = new ViewTarget(R.id.menuOptionTutorial, this);
    }

    private void leaveTutorial() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.signOut);
        alert.setMessage(R.string.tutorialSignOut);
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
