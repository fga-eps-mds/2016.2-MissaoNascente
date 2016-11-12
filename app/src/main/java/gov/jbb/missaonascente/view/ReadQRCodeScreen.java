package gov.jbb.missaonascente.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import gov.jbb.missaonascente.R;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class ReadQRCodeScreen extends Activity implements DecoratedBarcodeView.TorchListener, View.OnClickListener{

    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    private ImageButton preferenceScreenButton;
    private ImageButton almanacScreenButton;

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
                Intent preferenceScreenIntent = new Intent(ReadQRCodeScreen.this, PreferenceScreenActivity.class);
                ReadQRCodeScreen.this.startActivity(preferenceScreenIntent);
                finish();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
