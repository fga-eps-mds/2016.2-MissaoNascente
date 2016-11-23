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
                moveShowCaseCircle(energyBarTutorial,getString(R.string.energyBar),getString(R.string.energyBarTutorial),ShowcaseView.BELOW_SHOWCASE);
                break;
            case 1:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.question_image, null));
                moveShowCaseCircle(questionTutorial,getString(R.string.question),getString(R.string.questionTutorial),ShowcaseView.BELOW_SHOWCASE);
                break;
            case 2:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.main_screen_image, null));
                moveShowCaseCircle(registerTutorial,getString(R.string.registerTutorialTitle),getString(R.string.registerTutorial),ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 3:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.registered_element_image, null));
                moveShowCaseCircle(registeredElementTutorial,getString(R.string.registeredElement),getString(R.string.registeredElementTutorial),ShowcaseView.BELOW_SHOWCASE);
                break;
            case 4:
                moveShowCaseCircle(takePictureTutorial,getString(R.string.takePicture),getString(R.string.takePictureTutorial),ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 5:
                moveShowCaseCircle(moreInformationElementTutorial,getString(R.string.moreInformationElement),getString(R.string.moreInformationElementTutorial),ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 6:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.history_element_image, null));
                moveShowCaseCircle(historyElementTutorial,getString(R.string.historyElement),getString(R.string.historyElementTutorial),ShowcaseView.BELOW_SHOWCASE);
                break;
            case 7:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.main_screen_image, null));
                moveShowCaseCircle(leafScoreTutorial,getString(R.string.leafScore),getString(R.string.leafScoreTutorial),ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 8:
                moveShowCaseCircle(mapTutorial,getString(R.string.map),getString(R.string.mapTutorial),ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 9:
                moveShowCaseCircle(almanacTutorial,getString(R.string.almanac),getString(R.string.almanacTutorial),ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 10:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.book1_image, null));
                moveShowCaseCircle(almanacBook1Tutorial,getString(R.string.almanacBook1),getString(R.string.almanacBook1Tutorial),ShowcaseView.BELOW_SHOWCASE);
                break;
            case 11:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.book2_image, null));
                moveShowCaseCircle(almanacBook2Tutorial,getString(R.string.almanacBook2),getString(R.string.almanacBook2Tutorial),ShowcaseView.BELOW_SHOWCASE);
                break;
            case 12:
                moveShowCaseCircle(elementIconTutorial,getString(R.string.elementIcon),getString(R.string.elementIconTutorial),ShowcaseView.BELOW_SHOWCASE);
                break;
            case 13:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.element_book_image, null));
                moveShowCaseCircle(elementTutorial,getString(R.string.element),getString(R.string.elementTutorial),ShowcaseView.BELOW_SHOWCASE);
                break;
            case 14:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.element_catch_date_book_image, null));
                moveShowCaseCircle(elementCatchDateTutorial,getString(R.string.elementCatchDate),getString(R.string.elementCatchDateTutorial),ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 15:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.main_screen_image, null));
                moveShowCaseCircle(menuOptionTutorial,getString(R.string.menuOption),getString(R.string.menuOptionTutorial),ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 16:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.open_menu_image, null));
                moveShowCaseCircle(achievementOptionTutorial,getString(R.string.achievementOption),getString(R.string.achievementOptionTutorial),ShowcaseView.BELOW_SHOWCASE);
                break;
            case 17:
                moveShowCaseCircle(rankingOptionTutorial,getString(R.string.rankingOption),getString(R.string.rankingOptionTutorial),ShowcaseView.BELOW_SHOWCASE);
                break;
            case 18:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ranking_image, null));
                moveShowCaseCircle(rankingTutorial,getString(R.string.rankingTutorialTitle),getString(R.string.rankingTutorial),ShowcaseView.BELOW_SHOWCASE);
                break;
            case 19:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.open_menu_image, null));
                moveShowCaseCircle(mapOptionTutorial,getString(R.string.mapOption),getString(R.string.mapOptionTutorial),ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 20:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.open_menu_image, null));
                moveShowCaseCircle(preferenceOptionTutorial,getString(R.string.preferenceOption),getString(R.string.preferenceOptionTutorial),ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 21:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.preference_screen_image, null));
                moveShowCaseCircle(preferenceEditNickTutorial,getString(R.string.preferenceEditNick),getString(R.string.preferenceEditNickTutorial),ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 22:
                moveShowCaseCircle(preferenceDeleteAccountTutorial,getString(R.string.preferenceDeleteAccount),getString(R.string.preferenceDeleteAccountTutorial),ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 23:
                moveShowCaseCircle(preferenceSignOutTutorial,getString(R.string.preferenceSignOut),getString(R.string.preferenceSignOutTutorial),ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 24:
                tutorialCurrentImage.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.open_menu_image, null));
                moveShowCaseCircle(tutorialOptionTutorial,getString(R.string.tutorialOption),getString(R.string.tutorialOptionTutorial),ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 25:
                moveShowCaseCircle(aboutOptionTutorial,getString(R.string.aboutOption),getString(R.string.aboutOptionTutorial),ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 26:
                tutorialCurrentImage.setBackgroundColor(0x00000000);
                tutorialCurrentImage.setBorderWidth((float) 0);
                showcaseView.setTarget(Target.NONE);
                showcaseView.setContentTitle(getString(R.string.tutorialCompleted));
                showcaseView.setContentText(getString(R.string.tutorialCompletedMessage));
                showcaseView.setStyle(R.style.Final);
                showcaseView.setButtonText(getString(R.string.theEnd));
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
                .setContentTitle(getString(R.string.tutorialOption))
                .setContentText(R.string.initialMessageTutorial)
                .setStyle(R.style.Transparency)
                .build();

        showcaseView.setButtonText(getString(R.string.next));
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
