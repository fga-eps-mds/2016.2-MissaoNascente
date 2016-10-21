package com.example.jbbmobile.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.BooksController;
import com.example.jbbmobile.controller.EnergyController;
import com.example.jbbmobile.controller.LoginController;
import com.example.jbbmobile.controller.MainController;
import com.example.jbbmobile.controller.PreferenceController;
import com.example.jbbmobile.controller.RegisterElementController;
import com.example.jbbmobile.model.Element;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;

public class MainScreenActivity extends AppCompatActivity  implements View.OnClickListener {

    private TextView textViewNickname;
    private LoginController loginController;
    private ImageButton menuMoreButton;
    private ImageButton almanacButton;
    private ImageView readQrCodeButton;
    private MainController mainController;
    private RegisterElementFragment registerElementFragment;
    private ProgressBar energyBar;
    private EnergyController energyController;
    private Thread energyThread;

    private static final String TAG = "MainScreenActivity";

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
        }

        initViews();
        this.loginController = new LoginController();
        this.loginController.loadFile(this.getApplicationContext());
        this.energyController = new EnergyController(this.getApplicationContext());

        BooksController booksController = new BooksController(this);
        booksController.currentPeriod();
    }

    @Override
    protected void onStart() {
        super.onStart();
        textViewNickname = (TextView) findViewById(R.id.titleID);

        if (loginController.checkIfUserHasGoogleNickname()) {
            enterNickname();
        } else {
            textViewNickname.setText("");
            textViewNickname.setText(getString(R.string.en_explorer) + " " + loginController.getExplorer().getNickname());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        energyController.calculateElapsedTime(this);

        energyThread = new Thread() {
            @Override
            public void run() {
                try {
                    while (energyController.getExplorer().getEnergy() < energyController.getMaxEnergy()) {
                        updateEnergyProgress();
                        sleep(5000);
                        energyController.setExplorerEnergyInDataBase(energyController.getExplorer().getEnergy());
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } finally {
                    // Highlight bar!
                    updateEnergyProgress();
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

        energyController.addTimeOnPreferences();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.almanacButton:
                goToAlmanacScreen();
                break;
            case R.id.menuMoreButton:
                goToPreferenceScreen();
                break;
            case R.id.readQrCodeButton:
                mainController = new MainController(MainScreenActivity.this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        try{
            RegisterElementController registerElementController = registerElementFragment.getController();
            if (result != null) {
                if (result.getContents() == null) {
                    mainController.setCode(null);
                } else {
                    try {
                        registerElementController.associateElementByQrCode(result.getContents(), getContext());
                    } catch(SQLException exception){
                        Toast.makeText(this,"Elemento já registrado!", Toast.LENGTH_SHORT).show();
                    } catch(IllegalArgumentException exception){
                        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Element element = registerElementController.getElement();

                    registerElementFragment.showElement(element);
                    findViewById(R.id.readQrCodeButton).setVisibility(View.INVISIBLE);
                    findViewById(R.id.register_fragment).setVisibility(View.VISIBLE);
                    findViewById(R.id.register_fragment).requestLayout();
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }catch (IllegalArgumentException exception){
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void invalidNicknameError() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("ERROR");
        alert.setMessage("Invalid nickname!");
        alert.setCancelable(false);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
        alert.setTitle("NICKNAME");
        alert.setCancelable(false);
        alert.setMessage("Enter your new Nickname");
        alert.setView(input);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
            Toast.makeText(MainScreenActivity.this, "Error!", Toast.LENGTH_SHORT).show();
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
            Log.i(TAG,Integer.toString(progress));
            energyBar.setProgress(progress);
        }
    }
}