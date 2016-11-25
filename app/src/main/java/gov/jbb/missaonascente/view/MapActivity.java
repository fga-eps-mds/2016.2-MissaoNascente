package gov.jbb.missaonascente.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import gov.jbb.missaonascente.R;
import gov.jbb.missaonascente.controller.BooksController;
import gov.jbb.missaonascente.controller.ElementsController;
import gov.jbb.missaonascente.controller.HistoryController;
import gov.jbb.missaonascente.controller.MainController;
import gov.jbb.missaonascente.model.Element;

public class MapActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton chooseBookbutton;
    private ImageView map;
    private final int BOOK_WATER = 1;
    private final int BOOK_DORMANCY = 2;
    private final int BOOK_RENOVATION = 3;
    private HistoryController historyController;
    private BooksController booksController;
    private Element element;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        historyController = new HistoryController(this);
        booksController = new BooksController();
        booksController.currentPeriod();
        chooseBookbutton = (ImageButton) findViewById(R.id.chooseBookButton);
        chooseBookbutton.setOnClickListener(this);
        map = (ImageView) findViewById(R.id.map);
        historyController.getElementsHistory();
        element = historyController.getElement();
        try {
            if (historyController.getCurrentElement() == 1) {
                //Change "icon_water" to map without any element
                int imageIdMap = getResources().getIdentifier("map_history_" + booksController.getCurrentPeriod(), "drawable", getPackageName());
                Glide.with(this).load(imageIdMap).into(map);
            } else {
                int imageIdMap = getResources().getIdentifier("ele" + element.getIdElement() + "book" + element.getIdBook() + "hist" + element.getHistory(), "drawable", getPackageName());
                Glide.with(this).load(imageIdMap).into(map);

            }
        } catch (NullPointerException e) {
            //User completed history! set final image here.
            int imageIdMap = getResources().getIdentifier("eleendbook" + booksController.getCurrentPeriod() + "histend", "drawable", getPackageName());
            Glide.with(this).load(imageIdMap).into(map);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chooseBookButton:
                showPopup(findViewById(R.id.chooseBookButton));
        }
    }

    private void showPopup(View v) {
        Context layout = new ContextThemeWrapper(this, R.style.popupMenuStyle);
        PopupMenu popupMenu = new PopupMenu(layout, v);
        popupMenu.inflate(R.menu.map_menu);

        final MainController mainController = new MainController();
        mainController.forceImageIcons(popupMenu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.dormancy:
                        if (booksController.getCurrentPeriod() != BOOK_DORMANCY) {
                            int imageIdMap = getResources().getIdentifier("map_history_" + BOOK_DORMANCY, "drawable", getPackageName());
                            Glide.with(MapActivity.this).load(imageIdMap).into(map);

                        } else {
                            try {
                                element = historyController.getElement();
                                int imageIdMap = getResources().getIdentifier("ele" + element.getIdElement() + "book" + element.getIdBook() + "hist" + element.getHistory(), "drawable", getPackageName());
                            } catch (NullPointerException e) {
                                //User finished history!
                                int imageIdMap = getResources().getIdentifier("eleendbook" + booksController.getCurrentPeriod() + "histend", "drawable", getPackageName());
                                Glide.with(MapActivity.this).load(imageIdMap).into(map);

                            }
                        }
                        return true;
                    case R.id.water:
                        if (booksController.getCurrentPeriod() != BOOK_WATER) {
                            int imageIdMap = getResources().getIdentifier("map_history_" + BOOK_WATER, "drawable", getPackageName());
                            Glide.with(MapActivity.this).load(imageIdMap).into(map);

                        } else {
                            try {
                                element = historyController.getElement();
                                int imageIdMap = getResources().getIdentifier("ele" + element.getIdElement() + "book" + element.getIdBook() + "hist" + element.getHistory(), "drawable", getPackageName());
                                map.setImageResource(imageIdMap);
                                Glide.with(MapActivity.this).load(imageIdMap).into(map);

                            } catch (NullPointerException e) {
                                //User finished history!
                                int imageIdMap = getResources().getIdentifier("eleendbook" + booksController.getCurrentPeriod() + "histend", "drawable", getPackageName());
                                map.setImageResource(imageIdMap);
                                Glide.with(MapActivity.this).load(imageIdMap).into(map);

                            }
                        }
                        return true;
                    case R.id.renovation:
                        if (booksController.getCurrentPeriod() != BOOK_RENOVATION) {
                            int imageIdMap = getResources().getIdentifier("map_history_" + BOOK_RENOVATION, "drawable", getPackageName());
                            map.setImageResource(imageIdMap);
                            Glide.with(MapActivity.this).load(imageIdMap).into(map);

                        } else {
                            try {
                                Log.d("IDELEMENT", String.valueOf(historyController.getCurrentElement()));
                                element = historyController.getElement();
                                int imageIdMap = getResources().getIdentifier("ele" + element.getIdElement() + "book" + element.getIdBook() + "hist" + element.getHistory(), "drawable", getPackageName());
                                map.setImageResource(imageIdMap);
                                Glide.with(MapActivity.this).load(imageIdMap).into(map);

                            } catch (NullPointerException e) {
                                //User finished history!
                                int imageIdMap = getResources().getIdentifier("eleendbook" + booksController.getCurrentPeriod() + "histend", "drawable", getPackageName());
                                map.setImageResource(imageIdMap);
                                Glide.with(MapActivity.this).load(imageIdMap).into(map);
                            }
                        }
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }
}
