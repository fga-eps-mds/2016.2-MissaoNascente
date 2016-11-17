package gov.jbb.missaonascente.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import gov.jbb.missaonascente.R;
import gov.jbb.missaonascente.controller.HistoryController;
import gov.jbb.missaonascente.controller.MainController;
import gov.jbb.missaonascente.model.Element;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;
import static java.security.AccessController.getContext;

public class MapActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton chooseBookbutton;
    private ImageView map;
    private final int BOOK_WATER = 1;
    private final int BOOK_DORMANCY = 2;
    private final int BOOK_RENOVATION = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        chooseBookbutton = (ImageButton) findViewById(R.id.chooseBookButton);
        chooseBookbutton.setOnClickListener(this);
        map = (ImageView) findViewById(R.id.map);
        HistoryController historyController = new HistoryController(this);
        historyController.getElementsHistory();
        Element element = historyController.getElement();
        int imageIdMap = getResources().getIdentifier("ele" + element.getIdElement() + "book" + element.getIdBook() + "hist" + element.getHistory(), "drawable", getPackageName());
        map.setImageResource(imageIdMap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chooseBookButton:
                showPopup(findViewById(R.id.chooseBookButton));
        }
    }

    private void showPopup(View v) {
        Context layout = new ContextThemeWrapper(this, R.style.popupMenuStyle);
        PopupMenu popupMenu = new PopupMenu(layout, v);
        popupMenu.inflate(R.menu.map_menu);

        MainController mainController = new MainController();
        mainController.forceImageIcons(popupMenu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.water:
                        //exchange image set to water book
                        return true;
                    case R.id.dormancy:
                        //exchange image set to dormancy book
                        return true;
                    case R.id.renovation:
                        //exchange image set to renovation
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }
}
