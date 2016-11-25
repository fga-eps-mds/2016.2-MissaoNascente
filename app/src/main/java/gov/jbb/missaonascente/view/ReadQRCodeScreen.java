package gov.jbb.missaonascente.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import gov.jbb.missaonascente.R;
import gov.jbb.missaonascente.controller.MainController;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import static java.security.AccessController.getContext;

public class ReadQRCodeScreen extends Activity implements DecoratedBarcodeView.TorchListener, View.OnClickListener{

    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    private ImageButton preferenceScreenButton;
    private ImageButton almanacScreenButton;
    //private MainScreenActivity mainScreenActivity;

//    public Context getContext(){
//        return mainScreenActivity;
//    }

    private void showPopup(View v) {
        Context layout = new ContextThemeWrapper(this, R.style.popupMenuStyle);
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
                        Intent achievementIntent = new Intent(ReadQRCodeScreen.this, AchievementsScreenActivity.class);
                        ReadQRCodeScreen.this.startActivity(achievementIntent);
                        finish();
                        return true;
                    case R.id.rankingIcon:
                        Intent rankingIntent = new Intent(ReadQRCodeScreen.this, RankingScreenActivity.class);
                        ReadQRCodeScreen.this.startActivity(rankingIntent);
                        finish();
                        return true;
                    case R.id.preferenceIcon:
                        goToPreferenceScreen();
                        return true;
                    case R.id.aboutIcon:
                        Intent aboutIntent = new Intent(ReadQRCodeScreen.this, AboutActivity.class);
                        ReadQRCodeScreen.this.startActivity(aboutIntent);
                        return true;
                    case R.id.mapIcon:
                        Intent mapIntent = new Intent(ReadQRCodeScreen.this, MapActivity.class);
                        ReadQRCodeScreen.this.startActivity(mapIntent);
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
        setContentView(R.layout.activity_read_qrcode_screen);

        almanacScreenButton = (ImageButton)findViewById(R.id.almanacButton);
        preferenceScreenButton = (ImageButton)findViewById(R.id.menuMoreButton);
        almanacScreenButton.setOnClickListener(this);
        preferenceScreenButton.setOnClickListener(this);
        barcodeScannerView = (DecoratedBarcodeView)findViewById(R.id.zxing_barcode_scanner);
        barcodeScannerView.setTorchListener(this);
        barcodeScannerView.getViewFinder().setVisibility(View.INVISIBLE);
        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }


    @Override
    public void onTorchOn() {

    }

    @Override
    public void onTorchOff() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.almanacButton:
                Intent almanacScreenIntent = new Intent(ReadQRCodeScreen.this, AlmanacScreenActivity.class);
                ReadQRCodeScreen.this.startActivity(almanacScreenIntent);
                finish();
                break;
            case R.id.menuMoreButton:
                showPopup(findViewById(R.id.menuMoreButton));

                break;
        }
    }

    private void goToPreferenceScreen() {
        Intent registerIntent = new Intent(ReadQRCodeScreen.this, PreferenceScreenActivity.class);
        ReadQRCodeScreen.this.startActivity(registerIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
