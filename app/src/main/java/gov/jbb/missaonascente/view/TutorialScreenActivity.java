package gov.jbb.missaonascente.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.makeramen.roundedimageview.RoundedImageView;

import gov.jbb.missaonascente.R;

public class TutorialScreenActivity extends AppCompatActivity implements View.OnClickListener{
    private ShowcaseView showcaseView;
    private int passages = 0;
    private Target energyBarTutorial;
    private Target questionTutorial;
    private Target registerTutorial;
    private Target registeredElementTutorial;
    private Target takePictureTutorial;
    private Target moreInformationElementTutorial;
    private Target historyElementTutorial;
    private Target leafScoreTutorial;
    private Target mapTutorial;
    private Target almanacTutorial;
    private Target almanacBook1Tutorial;
    private Target almanacBook2Tutorial;
    private Target elementIconTutorial;
    private Target elementTutorial;
    private Target elementCatchDateTutorial;
    private Target menuOptionTutorial;
    private Target achievementOptionTutorial;
    private Target rankingOptionTutorial;
    private Target rankingTutorial;
    private Target mapOptionTutorial;
    private Target preferenceOptionTutorial;
    private Target preferenceEditNickTutorial;
    private Target preferenceDeleteAccountTutorial;
    private Target preferenceSignOutTutorial;
    private Target tutorialOptionTutorial;
    private Target aboutOptionTutorial;
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
                moveShowCaseCircle(energyBarTutorial,"Barra de Energia","Essa é a sua energia, acompanhe quantos elemento pode registrar através dela!\nLembre-se que quando estiver sem energia poderá responder uma questão para tentar recuperar uma parte dela!",ShowcaseView.BELOW_SHOWCASE);
                break;
            case 1:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.question_image, null));
                moveShowCaseCircle(questionTutorial,"Questão","Aqui você pode responder uma questão para tentar recuperar parte da energia perdida, caso esteja com menos que o necessário para registrar um elemento!",ShowcaseView.BELOW_SHOWCASE);
                break;
            case 2:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.main_screen_image, null));
                moveShowCaseCircle(registerTutorial,"Botão de Registro","Aqui você pode registar os elementos! Lembre-se que perde-se energia para cada elemento registrado, tendo ele ou não!",ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 3:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.registered_element_image, null));
                moveShowCaseCircle(registeredElementTutorial,"Elemento Registrado","Aqui você pode visualizar o elemento registrado! Lembre-se que ganha-se uma pontuação ao registrar o elemento pela primeira vez!",ShowcaseView.BELOW_SHOWCASE);
                break;
            case 4:
                moveShowCaseCircle(takePictureTutorial,"Tirar fotografia","Aqui você pode tirar uma fotografia do elemento registrado!",ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 5:
                moveShowCaseCircle(moreInformationElementTutorial,"Mais informações","Aqui você pode obter mais informações sobre esse elemento!",ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 6:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.history_element_image, null));
                moveShowCaseCircle(historyElementTutorial,"Elemento da história","Ao registrar um elemento da história, ele é mostrado de maneira especial!",ShowcaseView.BELOW_SHOWCASE);
                break;
            case 7:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.main_screen_image, null));
                moveShowCaseCircle(leafScoreTutorial,"Pontuação","Aqui você pode acompanhar sua quantidade de pontos, ou seja, a soma de todos os pontos obtidos no decorrer da trilha!",ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 8:
                moveShowCaseCircle(mapTutorial,"Mapa de Progresso","Aqui você pode acompanhar seu progresso perante o elementos da história durante a trillha de acordo com o período!\nLembre-se que ele só aparece no momento em que você registrar o primeiro elemento da história!",ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 9:
                moveShowCaseCircle(almanacTutorial,"Almanaque","Aqui você pode acessar todos os seus elementos já registrados, de modo organizado em seus respectivos livros!",ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 10:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.book1_image, null));
                moveShowCaseCircle(almanacBook1Tutorial,"Livros do Almanaque","Aqui você pode acessar todos os 3 livros que compõem o Almanaque!\nLembre-se que eles representam períodos do ano, nos quais os elementos de cada um são exclusivos do livro ao qual pertencem!",ShowcaseView.BELOW_SHOWCASE);
                break;
            case 11:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.book2_image, null));
                moveShowCaseCircle(almanacBook2Tutorial,"Livros do Almanaque","Pode-se navegar entre os livros e checar todos os elementos já registrados no período desejado!",ShowcaseView.BELOW_SHOWCASE);
                break;
            case 12:
                moveShowCaseCircle(elementIconTutorial,"Elemento","Aqui você pode acessar mais informações sobre um elemento em específico!",ShowcaseView.BELOW_SHOWCASE);
                break;
            case 13:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.element_book_image, null));
                moveShowCaseCircle(elementTutorial,"Elemento","Ao clicar no elemento, você poderá contemplar tais informações, assim como uma imagem do elemento registrado!",ShowcaseView.BELOW_SHOWCASE);
                break;
            case 14:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.element_catch_date_book_image, null));
                moveShowCaseCircle(elementCatchDateTutorial,"Data de Registro","Aqui é possível visualizar a data em que você registrou esse elemento!",ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 15:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.main_screen_image, null));
                moveShowCaseCircle(menuOptionTutorial,"Mais Opções","Aqui você pode acessar diversas opções que o aplicativo te oferece, como:",ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 16:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.open_menu_image, null));
                moveShowCaseCircle(achievementOptionTutorial,"Conquistas","Aqui você tem acesso conquistas adquiridas ao alcançar determinados objetivos na trilha!",ShowcaseView.BELOW_SHOWCASE);
                break;
            case 17:
                moveShowCaseCircle(rankingOptionTutorial,"Ranking","Aqui você tem acesso ao ranking de pontuação dos 10 primeiros exploradores, incluindo a sua!",ShowcaseView.BELOW_SHOWCASE);
                break;
            case 18:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ranking_image, null));
                moveShowCaseCircle(rankingTutorial,"Ranking","Pode-se obervar os 10 primeros colocados e você aparecendo de forma destacada!",ShowcaseView.BELOW_SHOWCASE);
                break;
            case 19:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.open_menu_image, null));
                moveShowCaseCircle(mapOptionTutorial,"Mapa de Progresso","Aqui você tem acesso a mesma tela acessada através do botão mapa já mencionado!",ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 20:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.open_menu_image, null));
                moveShowCaseCircle(preferenceOptionTutorial,"Prefências","Aqui você tem acesso a algumas opções sobre sua conta!",ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 21:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.preference_screen_image, null));
                moveShowCaseCircle(preferenceEditNickTutorial,"Editar Nickname","Aqui você pode editar o seu Nickname!",ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 22:
                moveShowCaseCircle(preferenceDeleteAccountTutorial,"Deletar Conta","Aqui você pode deletar sua conta!\nLembre-se que esse processo é irreversível!",ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 23:
                moveShowCaseCircle(preferenceSignOutTutorial,"Deslogar do App","Aqui você pode deslogar da sua conta!\nLembre-se que ao deslogar, você perderá todos o seu progresso atual da história, mas seus elementos não serão perdidos!",ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 24:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.open_menu_image, null));
                moveShowCaseCircle(tutorialOptionTutorial,"Tutorial","Você está nessa opção, e pode acessar o tutorial através dela quando quiser!",ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 25:
                moveShowCaseCircle(aboutOptionTutorial,"Informações Sobre o App","E por último, aqui você pode acessar algumas informações adicionais sobre o Missão Nascente!",ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 26:
                tutorialCurrentImage.setBackgroundColor(0x00000000);
                tutorialCurrentImage.setBorderWidth((float) 0);
                showcaseView.setTarget(Target.NONE);
                showcaseView.setContentTitle("Tutorial Concluído");
                showcaseView.setContentText("Tenha uma espetacular aventura, Explorador(a)!");
                showcaseView.setStyle(R.style.Final);
                showcaseView.setButtonText("Fim");
                break;
            case 27:
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

    private void moveShowCaseCircle(Target currentTarget,String title,String text, int position){
        showcaseView.forceTextPosition(position);
        showcaseView.setShowcase(currentTarget,true);
        showcaseView.setContentTitle(title);
        showcaseView.setContentText(text);
    }

    private void initViews(){
        this.tutorialCurrentImage = (RoundedImageView) findViewById(R.id.tutorial_current_image);
        this.energyBarTutorial = new ViewTarget(R.id.energyBarTutorial, this);
        this.questionTutorial = new ViewTarget(R.id.questionTutorial, this);
        this.registerTutorial = new ViewTarget(R.id.registerTutorial, this);
        this.registeredElementTutorial = new ViewTarget(R.id.registeredElementTutorial, this);
        this.takePictureTutorial = new ViewTarget(R.id.takePictureTutorial, this);
        this.moreInformationElementTutorial = new ViewTarget(R.id.moreInformationElementTutorial, this);
        this.historyElementTutorial = new ViewTarget(R.id.historyElementTutorial, this);
        this.leafScoreTutorial = new ViewTarget(R.id.leafScoreTutorial, this);
        this.mapTutorial = new ViewTarget(R.id.mapTutorial, this);
        this.almanacTutorial = new ViewTarget(R.id.almanacTutorial, this);
        this.almanacBook1Tutorial = new ViewTarget(R.id.almanacBook1Tutorial, this);
        this.almanacBook2Tutorial = new ViewTarget(R.id.almanacBook2Tutorial, this);
        this.elementIconTutorial = new ViewTarget(R.id.elementIconTutorial, this);
        this.elementTutorial = new ViewTarget(R.id.elementTutorial, this);
        this.elementCatchDateTutorial = new ViewTarget(R.id.elementCatchDateTutorial, this);
        this.achievementOptionTutorial = new ViewTarget(R.id.achievementOptionTutorial, this);
        this.rankingOptionTutorial = new ViewTarget(R.id.rankingOptionTutorial, this);
        this.rankingTutorial = new ViewTarget(R.id.rankingTutorial, this);
        this.mapOptionTutorial = new ViewTarget(R.id.mapOptionTutorial, this);
        this.preferenceOptionTutorial = new ViewTarget(R.id.preferenceOptionTutorial, this);
        this.preferenceEditNickTutorial = new ViewTarget(R.id.preferenceEditNickTutorial, this);
        this.preferenceDeleteAccountTutorial = new ViewTarget(R.id.preferenceDeleteAccountTutorial, this);
        this.preferenceSignOutTutorial = new ViewTarget(R.id.preferenceSignOutTutorial, this);
        this.tutorialOptionTutorial = new ViewTarget(R.id.tutorialOptionTutorial, this);
        this.aboutOptionTutorial = new ViewTarget(R.id.aboutOptionTutorial, this);
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
