package gov.jbb.missaonascente.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.SQLException;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
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

import gov.jbb.missaonascente.R;
import gov.jbb.missaonascente.controller.BooksController;
import gov.jbb.missaonascente.controller.EnergyController;

import gov.jbb.missaonascente.controller.HistoryController;
import gov.jbb.missaonascente.controller.LoginController;
import gov.jbb.missaonascente.controller.MainController;
import gov.jbb.missaonascente.controller.NotificationController;
import gov.jbb.missaonascente.controller.PreferenceController;
import gov.jbb.missaonascente.controller.ProfessorController;
import gov.jbb.missaonascente.controller.RegisterElementController;
import gov.jbb.missaonascente.model.Element;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.util.ArrayList;

public class MainScreenActivity extends AppCompatActivity implements View.OnClickListener, QuestionFragment.OnFragmentInteractionListener {

    private final String APP_FIRST_TIME = "appFirstTime";
    private LoginController loginController;
    private ImageButton menuMoreButton;
    private ImageButton almanacButton;
    private ImageView readQrCodeButton;
    private TextView scoreViewText;
    private MainController mainController;
    private RegisterElementFragment registerElementFragment;
    private QuestionFragment questionFragment;
    private ProfessorFragment professorFragment;
    private RegisterElementController registerElementController;
    private RelativeLayout relativeLayoutUp;
    private ProgressBar energyBar;
    private EnergyController energyController;
    private Thread energyThread;
    private HistoryController historyController;

    private static final String TAG = "MainScreenActivity";


    private void showPopup(View v) {
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
            //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            registerElementFragment = new RegisterElementFragment();
            //fragmentTransaction.add(R.id.register_fragment, registerElementFragment, "RegisterElementFragment");

            //fragmentTransaction.commit();

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

        processQRCode();
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
                        energyController.setExplorerEnergyInDataBase(energyController.getExplorer().getEnergy(), energyController.INCREMENT_FOR_TIME);
                        Log.d("Final of While", String.valueOf(energyController.getExplorer().getEnergy()));
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } finally {
                    // Highlight bar!
                    updateEnergyProgress();
                    energyController.setExplorerEnergyInDataBase(energyController.getExplorer().getEnergy(), energyController.INCREMENT_FOR_TIME);
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
                if (mainController != null) {
                    mainController = null;
                }
                if (energyController.DECREASE_ENERGY <= energyController.getExplorer().getEnergy()) {
                    mainController = new MainController(MainScreenActivity.this);
                } else {
                    callQuestion();
                    callProfessor(getString(R.string.withoutEnergyMessage),
                                  getString(R.string.withoutEnergyMessage2));
                }

                break;
        }
    }

    public void callQuestion(){
        relativeLayoutUp = (RelativeLayout) findViewById(R.id.mainScreenUp);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        questionFragment = new QuestionFragment();
        ft.add(R.id.question_fragment, questionFragment, "QuestionFragment");
        ft.commitNow();
        findViewById(R.id.question_fragment).setVisibility(View.VISIBLE);
        findViewById(R.id.question_fragment).requestLayout();

        menuMoreButton.setClickable(false);
        almanacButton.setClickable(false);
        readQrCodeButton.setClickable(false);

        relativeLayoutUp.setBackgroundColor(0x4D000000);
    }

    protected void processQRCode(){
        int elementEnergy;
        boolean showScoreInFirstRegister;

        String code = mainController.getCode();

        registerElementController = registerElementFragment.getController();
        if(code != null) {
            try {
                registerElementController.associateElementByQrCode(code, getContext());
                elementEnergy = registerElementController.getElement().getEnergeticValue();
                energyController.checkEnergeticValueElement(elementEnergy);
                modifyEnergy();
                showScoreInFirstRegister = true;

                Element element = registerElementController.getElement();

                registerElementFragment.showElement(element, showScoreInFirstRegister);
                findViewById(R.id.readQrCodeButton).setVisibility(View.INVISIBLE);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.register_fragment, registerElementFragment).commitNow();
                findViewById(R.id.register_fragment).requestLayout();

                setScore();
            } catch (SQLException exception) {

                elementEnergy = registerElementController.getElement().getEnergeticValue();
                energyController.calculateElapsedElementTime(this, elementEnergy);
                modifyEnergy();
                //Toast.makeText(this,"Elemento já registrado!", Toast.LENGTH_SHORT).show();
                String existedElement = getString(R.string.existedElement);
                if (elementEnergy < 0) {
                    callProfessor(existedElement);
                }
                showScoreInFirstRegister = false;

            } catch (IllegalArgumentException exception) {
                //Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                String aux = exception.getMessage();
                callProfessor(aux);
                return;
            }

            Element element = registerElementController.getElement();

            findViewById(R.id.readQrCodeButton).setVisibility(View.INVISIBLE);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.register_fragment, registerElementFragment).commitNow();
            findViewById(R.id.register_fragment).requestLayout();

            element = verifyHistoryElement(element);

            registerElementFragment.showElement(element, showScoreInFirstRegister);



            setScore();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            mainController.setCode(result.getContents());
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void callProfessor(String message) {
        ProfessorController professorController = new ProfessorController();
        BooksController book = new BooksController();
        ArrayList<String> s = new ArrayList<>();
        s.add(message);

        book.currentPeriod();
        int period = book.getCurrentPeriod();

        Log.d("Period", " " + period);

        switch (period) {
            case 1:
                Drawable drawable1 = ResourcesCompat.getDrawable(getResources(), R.drawable.professor_teste_1, null);
                professorFragment = professorController.createProfessorFragment(this, s, drawable1);
                break;
            case 2:
                Drawable drawable2 = ResourcesCompat.getDrawable(getResources(), R.drawable.professor_teste_2, null);
                professorFragment = professorController.createProfessorFragment(this, s, drawable2);
                break;
            case 3:
                Drawable drawable3 = ResourcesCompat.getDrawable(getResources(), R.drawable.professor_teste_3, null);
                professorFragment = professorController.createProfessorFragment(this, s, drawable3);
                break;
            default:
                break;
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.professor_fragment, professorFragment, "ProfessorFragment");
        fragmentTransaction.commitNow();
    }

    public void callProfessor(String message, String message2) {
        ProfessorController professorController = new ProfessorController();
        BooksController book = new BooksController();
        ArrayList<String> s = new ArrayList<>();
        s.add(message);
        s.add(message2);

        book.currentPeriod();
        int period = book.getCurrentPeriod();

        Log.d("Period", " " + period);

        switch (period) {
            case 1:
                Drawable drawable1 = ResourcesCompat.getDrawable(getResources(), R.drawable.professor_teste_1, null);
                professorFragment = professorController.createProfessorFragment(this, s, drawable1);
                break;
            case 2:
                Drawable drawable2 = ResourcesCompat.getDrawable(getResources(), R.drawable.professor_teste_2, null);
                professorFragment = professorController.createProfessorFragment(this, s, drawable2);
                break;
            case 3:
                Drawable drawable3 = ResourcesCompat.getDrawable(getResources(), R.drawable.professor_teste_3, null);
                professorFragment = professorController.createProfessorFragment(this, s, drawable3);
                break;
            default:
                break;
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.professor_fragment, professorFragment, "ProfessorFragment");
        fragmentTransaction.commitNow();
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

    public void setScore() {
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

    public void updateEnergyProgress() {
        if (energyBar != null) {
            int progress = energyController.energyProgress(energyBar.getMax());
            energyBar.setProgress(progress);
            Log.d("Progress of Bar", Integer.toString(progress));
        }
    }

    public void modifyEnergy() {
        energyThread.interrupt();
        RegisterElementController registerElementController = registerElementFragment.getController();

        Integer elementEnergy = registerElementController.getElement().getEnergeticValue();
        Integer elementsEnergyType = energyController.checkElementsEnergyType(elementEnergy);

        if (elementsEnergyType == energyController.JUST_DECREASE_ENERGY) {
            String energyNegativeMessenger = Integer.toString(elementEnergy) + " de energia!";
            Toast.makeText(this, energyNegativeMessenger, Toast.LENGTH_SHORT).show();
        } else if (elementsEnergyType == energyController.JUST_INCREASE_ENERGY) {
            String energyPositiveMessenger = "+" + Integer.toString(elementEnergy)+ " de energia!";
            Toast.makeText(this, energyPositiveMessenger, Toast.LENGTH_SHORT).show();
        } else {
            String message = getString(R.string.existedElement);
            String num = energyController.getRemainingTimeInMinutes();
            String message2 = "Aguarde" + num + "minutos para ganhar energia novamente com este elemento";
            callProfessor(message , message2);
        }

        updateEnergyProgress();

        //energyController.sendEnergy(this);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void onBackPressed() {
        if (questionFragment != null && questionFragment.isVisible()) {
            this.getSupportFragmentManager().beginTransaction().remove(questionFragment).commit();
            relativeLayoutUp.setBackgroundColor(0x00000000);
            menuMoreButton.setClickable(true);
            almanacButton.setClickable(true);
            readQrCodeButton.setClickable(true);
        } else {
            super.onBackPressed();
        }
    }

    public Element verifyHistoryElement(Element element){
        historyController = new HistoryController(this);
        historyController.getElementsHistory();

        historyController.loadSave();
        boolean sequence = historyController.sequenceElement(element.getIdElement(), loginController.getExplorer());
        if(sequence){
            element.setElementScore(element.getElementScore()*2);
            //Toast.makeText(this,element.getHistoryMessage(), Toast.LENGTH_SHORT).show();
            callProfessor(element.getHistoryMessage());
        }

        changeColorElementHistory(element,sequence);

        return  element;
    }

    private void changeColorElementHistory(Element element, boolean sequence){
        int colorBackground = R.drawable.background_catched_element;
        int colorButton = R.color.colorGreen;
        if(sequence){
            colorBackground = R.drawable.background_catched_element_history;
            colorButton = R.color.colorPrimaryText;

            //Toast.makeText(this,element.getHistoryMessage(), Toast.LENGTH_SHORT).show();
            callProfessor(element.getHistoryMessage());
        }
        Log.d("Entrou", "entrou");
        findViewById(R.id.fragment_element).setBackground(ContextCompat.getDrawable(this, colorBackground));
        findViewById(R.id.name_text).getBackground().setColorFilter(ContextCompat.getColor(this, colorButton), PorterDuff.Mode.SRC_ATOP);
        findViewById(R.id.close_button).getBackground().setColorFilter(ContextCompat.getColor(this, colorButton), PorterDuff.Mode.SRC_ATOP);
        findViewById(R.id.camera_button).getBackground().setColorFilter(ContextCompat.getColor(this, colorButton), PorterDuff.Mode.SRC_ATOP);
        findViewById(R.id.show_element_button).getBackground().setColorFilter(ContextCompat.getColor(this, colorButton), PorterDuff.Mode.SRC_ATOP);
    }
}