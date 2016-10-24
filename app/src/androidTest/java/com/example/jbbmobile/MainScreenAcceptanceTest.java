package com.example.jbbmobile;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Root;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.jbbmobile.controller.LoginController;
import com.example.jbbmobile.controller.RegisterExplorerController;
import com.example.jbbmobile.dao.BookDAO;
import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Explorer;
import com.example.jbbmobile.view.LoginScreenActivity;
import com.example.jbbmobile.view.MainScreenActivity;
import com.example.jbbmobile.view.RegisterScreenActivity;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)

public class MainScreenAcceptanceTest{
    private final ActivityTestRule<MainScreenActivity> main = new ActivityTestRule<>(MainScreenActivity.class);
    private static final Context context = InstrumentationRegistry.getTargetContext();;
    private static final String EMAIL = "user@user.com";
    private static final String PASSWORD = "000000";
    private static final String NICKNAME = "testUser";

    @BeforeClass
    public static void setup(){
        ExplorerDAO databaseExplorer = new ExplorerDAO(context);
        databaseExplorer.onUpgrade(databaseExplorer.getWritableDatabase(), 1, 1);
        BookDAO databaseBook = new BookDAO(context);
        databaseBook.onUpgrade(databaseBook.getWritableDatabase(), 1, 1);
        ElementDAO databaseElement = new ElementDAO(context);
        databaseElement.onUpgrade(databaseElement.getWritableDatabase(), 1, 1);
        RegisterExplorerController register = new RegisterExplorerController();
        register.register(NICKNAME, EMAIL, PASSWORD, PASSWORD, context);
    }

    @Test
    public void testIfRankingIsDisplayed(){
        final String menuMoreRanking = "Ranking";
        LoginController login = new LoginController();
        login.doLogin(EMAIL, PASSWORD, context);

        while(!login.isAction());

        main.launchActivity(new Intent());
        onView(withId(R.id.menuMoreButton))
                .perform(click());
        onView(withText(menuMoreRanking))
                .inRoot(isPopupWindow());
    }

    @Test
    public void testIfPreferenceScreenIsDisplayed(){
        main.launchActivity(new Intent());
        onView(withId(R.id.menuMoreButton))
                .perform(click());
        String menuMorePreferences = "Preference";
        onView(withText(menuMorePreferences))
                .inRoot(isPopupWindow());
    }

    @Test
    public void testIfAlmanacScreenIsDisplayed(){
        main.launchActivity(new Intent());
        onView(withId(R.id.almanacButton))
                .perform(click())
                .inRoot(isPopupWindow());

        new ExplorerDAO(context).deleteExplorer(new Explorer(EMAIL, PASSWORD));
    }

    public static Matcher<Root> isPopupWindow() {
        return isPlatformPopup();
    }


}
