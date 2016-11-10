package com.example.jbbmobile.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.AppUpdateReceiver;
import com.example.jbbmobile.controller.BooksController;
import com.example.jbbmobile.controller.EnergyController;
import com.example.jbbmobile.controller.LoginController;
import com.example.jbbmobile.controller.MainController;
import com.example.jbbmobile.controller.NotificationController;
import com.example.jbbmobile.controller.PreferenceController;
import com.example.jbbmobile.controller.ProfessorController;
import com.example.jbbmobile.controller.RegisterElementController;
import com.example.jbbmobile.model.Element;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import java.io.IOException;
import java.util.ArrayList;

public class MainScreenActivity extends AppCompatActivity  implements View.OnClickListener, QuestionFragment.OnFragmentInteractionListener{

    private final String APP_FIRST_TIME = "appFirstTime";
    private LoginController loginController;
    private ImageButton menuMoreButton;
    private ImageButton almanacButton;
    private ImageView readQrCodeButton;
    private TextView scoreViewText;
    private MainController mainController;
    private RegisterElementFragment registerElementFragment;
    private QuestionFragment questionFragment;
    private RegisterElementController registerElementController;
    private RelativeLayout relativeLayoutUp;
    private ProgressBar energyBar;
    private EnergyController energyController;
    private Thread energyThread;

    private static final String TAG = "MainScreenActivity";

    private void showPopup(View v){
        Context layout = new ContextThemeWrapper(getContext(), R.style.popupMenuStyle);
        PopupMenu popupMenu = new PopupMenu(layout, v);
        popupMenu.inflate(R.menu.settings_menu);

        MainController mainController = new MainController();
        mainController.forceImageIcons(popupMenu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.achievements:
                        //call achievement activity
                        return true;
                    case R.id.rankingIcon:
                        Intent rankingIntent = new Intent(MainScreenActivity.this, RankingScreenActivity.class);
                        MainScreenActivity.this.startActivity(rankingIntent);
                        return true;
                    case R.id.preferenceIcon:
                        goToPreferenceScreen();
                        return true;
                    case R.id.aboutIcon:
                        //call about activity
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        if (savedInstanceState == null) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            registerElementFragment = new RegisterElementFragment();
            fragmentTransaction.add(R.id.register_fragment, registerElementFragment, "RegisterElementFragment");
            fragmentTransaction.commit();

            /* TODO remover esse exemplo de uso do Professor
            ProfessorController professorController = new ProfessorController();
            ArrayList<String> s =  new ArrayList<>();
            s.add("1 - asfddsfsdfalsdnaksnafdslfkgspifasodifvfkgsojdapsfmspojbpsofmasoapsodifvfkgsojdapsfmspojbpsofmasoap");
            s.add("2 - sodifvfkgsojdapsfmspojbpsofmasoasodifvfkgsojdapsfmspojbpsofmasoappsodifvfkgsojdapsfmspojbpsofmasoap");
            s.add("3 - sodifvfkgsojdapsfmspojbpsofmasoapsodifvfkgsojdapsfmspojbpsofmasoapsodifvfkgsojdapsfmspojbpsofmasoap");

            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.professor, null);
            professorController.createProfessorFragment(this, s, drawable);
            */
        }

        MainController mainController = new MainController();
        mainController.downloadDataFirstTime(this, getSharedPreferences(APP_FIRST_TIME, MODE_PRIVATE));

        NotificationController notificationController = new NotificationController(this);
        notificationController.notificationByPeriod();

        initViews();
        this.loginController = new LoginController();
        this.loginController.loadFile(this.getApplicationContext());
        registerElementFragment.createRegisterElementController(this.loginController);

        this.energyController = new EnergyController(this.getApplicationContext());
        this.mainController = new MainController();

        BooksController booksController = new BooksController(this);
        booksController.currentPeriod();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (this.loginController.checkIfUserHasGoogleNickname()) {
            enterNickname();
        }

        setScore();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("Initial MainScreen", String.valueOf(energyController.getExplorer().getEnergy()));

        /*if(mainController.checkIfUserHasInternet(getContext()) )
            energyController.synchronizeEnergy(getContext());

        Log.d("In Web","Energy: " + String.valueOf(energyController.getExplorer().getEnergy()));*/

        energyController.calculateElapsedEnergyTime(this);

        Log.d("In elapsed MainScreen", String.valueOf(energyController.getExplorer().getEnergy()));

        energyThread = new Thread() {
            @Override
            public void run() {
                try {
                    while (energyController.getExplorer().getEnergy() < energyController.getMAX_ENERGY()) {
                        Log.d("Initial of While", String.valueOf(energyController.getExplorer().getEnergy()));
                        updateEnergyProgress();
                        sleep(6000);
                        energyController.setExplorerEnergyInDataBase(energyController.getExplorer().getEnergy(),energyController.INCREMENT_FOR_TIME);
                        Log.d("Final of While", String.valueOf(energyController.getExplorer().getEnergy()));
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } finally {
                    // Highlight bar!
                    updateEnergyProgress();
                    energyController.setExplorerEnergyInDataBase(energyController.getExplorer().getEnergy(),energyController.INCREMENT_FOR_TIME);
                    Log.d(TAG, "END of Energy Bar!");
                }
            }
        };
        energyThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        energyThread.interrupt();

        energyController.addEnergyTimeOnPreferencesTime();
    }

    @Override
    protected void onStop() {
        super.onStop();

        /*if(mainController.checkIfUserHasInternet(getContext()))
            energyController.sendEnergy(getContext());*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.almanacButton:
                goToAlmanacScreen();

                break;
            case R.id.menuMoreButton:
                showPopup(findViewById(R.id.menuMoreButton));

                break;
            case R.id.readQrCodeButton:
                if(mainController != null) {
                    mainController = null;
                }
                if(energyController.DECREASE_ENERGY <= energyController.getExplorer().getEnergy()){
                    mainController = new MainController(MainScreenActivity.this);
                }else{
                    relativeLayoutUp = (RelativeLayout) findViewById(R.id.mainScreenUp);

                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    questionFragment = new QuestionFragment();
                    ft.add(R.id.question_fragment, questionFragment, "QuestionFragment");
                    ft.commit();
                    findViewById(R.id.question_fragment).setVisibility(View.VISIBLE);
                    findViewById(R.id.question_fragment).requestLayout();

                    menuMoreButton.setClickable(false);
                    almanacButton.setClickable(false);
                    readQrCodeButton.setClickable(false);

                    relativeLayoutUp.setBackgroundColor(0x4D000000);
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        int elementEnergy;
        boolean showScoreInFirstRegister;

        registerElementController = registerElementFragment.getController();
        if (result != null) {
            if (result.getContents() == null) {
                mainController.setCode(null);
            } else {
                try {
                    registerElementController.associateElementByQrCode(result.getContents(), getContext());
                    elementEnergy = registerElementController.getElement().getEnergeticValue();
                    energyController.checkEnergeticValueElement(elementEnergy);
                    modifyEnergy();
                    showScoreInFirstRegister = true;
                } catch(SQLException exception){
                    elementEnergy = registerElementController.getElement().getEnergeticValue();
                    energyController.calculateElapsedElementTime(this, elementEnergy);
                    modifyEnergy();
                    Toast.makeText(this,"Elemento já registrado!", Toast.LENGTH_SHORT).show();
                    showScoreInFirstRegister = false;

                } catch(IllegalArgumentException exception){
                    Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Element element = registerElementController.getElement();

                registerElementFragment.showElement(element,showScoreInFirstRegister);
                findViewById(R.id.readQrCodeButton).setVisibility(View.INVISIBLE);
                findViewById(R.id.register_fragment).setVisibility(View.VISIBLE);
                findViewById(R.id.register_fragment).requestLayout();

                setScore();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initViews() {
        this.menuMoreButton = (ImageButton) findViewById(R.id.menuMoreButton);
        this.almanacButton = (ImageButton) findViewById(R.id.almanacButton);
        this.readQrCodeButton = (ImageView) findViewById(R.id.readQrCodeButton);
        this.energyBar = (ProgressBar) findViewById(R.id.energyBar);

        this.menuMoreButton.setOnClickListener(this);
        this.almanacButton.setOnClickListener(this);
        this.readQrCodeButton.setOnClickListener(this);
    }

    public void setScore(){
        scoreViewText = (TextView) findViewById(R.id.explorerScore);
        scoreViewText.setText("");
        scoreViewText.setText(String.valueOf(loginController.getExplorer().getScore()));
    }

    private void invalidNicknameError() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.errorMessage);
        alert.setMessage(R.string.nicknameValidation);
        alert.setCancelable(false);
        alert.setPositiveButton(R.string.OKMessage, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterNickname();
            }
        });
        alert.show();
    }

    private void enterNickname() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        alert.setTitle(R.string.nickname);
        alert.setCancelable(false);
        alert.setMessage(R.string.putNewNickname);
        alert.setView(input);
        alert.setPositiveButton(R.string.OKMessage, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterNicknameOnClick(input);
            }
        });
        alert.show();
    }

    private void enterNicknameOnClick(EditText input) {
        try {
            String newNickname = input.getText().toString();
            PreferenceController preferenceController = new PreferenceController();
            preferenceController.updateNickname(newNickname, loginController.getExplorer().getEmail(), MainScreenActivity.this.getApplicationContext());
            loginController.deleteFile(MainScreenActivity.this);
            loginController.loadFile(MainScreenActivity.this);
            new LoginController().realizeLogin(loginController.getExplorer().getEmail(), MainScreenActivity.this);
            MainScreenActivity.this.recreate();
        } catch (IOException e) {
            Toast.makeText(MainScreenActivity.this, R.string.errorMessage, Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException i) {
            invalidNicknameError();
        }
    }

    private void goToPreferenceScreen() {
        Intent registerIntent = new Intent(MainScreenActivity.this, PreferenceScreenActivity.class);
        MainScreenActivity.this.startActivity(registerIntent);
        finish();
    }

    private void goToAlmanacScreen() {
        Intent almanacIntent = new Intent(MainScreenActivity.this, AlmanacScreenActivity.class);
        MainScreenActivity.this.startActivity(almanacIntent);
        finish();
    }

    private Context getContext() {
        return this;
    }

    public void updateEnergyProgress(){
        if(energyBar != null){
            int progress = energyController.energyProgress(energyBar.getMax());
            energyBar.setProgress(progress);
            Log.d("Progress of Bar", Integer.toString(progress));
        }
    }

    public void modifyEnergy(){
        energyThread.interrupt();
        RegisterElementController registerElementController = registerElementFragment.getController();

        int elementEnergy = registerElementController.getElement().getEnergeticValue();
        int elementsEnergyType = energyController.checkElementsEnergyType(elementEnergy);

        if(elementsEnergyType == energyController.JUST_DECREASE_ENERGY){
            Toast.makeText(this, "- " + energyController.DECREASE_ENERGY + " de Energia!" , Toast.LENGTH_SHORT).show();

        }else if(elementsEnergyType == energyController.JUST_INCREASE_ENERGY){
            Toast.makeText(this, "+ " + elementEnergy + " de Energia!" , Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "- " + energyController.DECREASE_ENERGY + " de Energia!" , Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Aguarde " + energyController.getRemainingTimeInMinutes() + " minutos para ganhar energia novamente com este elemento!" , Toast.LENGTH_LONG).show();
        }

        updateEnergyProgress();

        //energyController.sendEnergy(this);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {}

    @Override
    public void onBackPressed() {
        if(questionFragment != null && questionFragment.isVisible()){
            this.getSupportFragmentManager().beginTransaction().remove(questionFragment).commit();
            relativeLayoutUp.setBackgroundColor(0x00000000);
            menuMoreButton.setClickable(true);
            almanacButton.setClickable(true);
            readQrCodeButton.setClickable(true);
        } else {
            super.onBackPressed();
        }
    }
}