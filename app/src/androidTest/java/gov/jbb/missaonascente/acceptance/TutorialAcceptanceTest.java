package gov.jbb.missaonascente.acceptance;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.Root;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.OngoingStubbing;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import gov.jbb.missaonascente.controller.LoginController;
import gov.jbb.missaonascente.dao.BookDAO;
import gov.jbb.missaonascente.dao.ElementDAO;
import gov.jbb.missaonascente.dao.ExplorerDAO;
import gov.jbb.missaonascente.model.Explorer;
import gov.jbb.missaonascente.view.MainScreenActivity;
import gov.jbb.missaonascente.view.TutorialScreenActivity;

import org.hamcrest.Matcher;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.Intents.release;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)

public class TutorialAcceptanceTest{
    @Rule
    public final IntentsTestRule<TutorialScreenActivity> tutorial = new IntentsTestRule<>(TutorialScreenActivity.class);

    private static final Context context = InstrumentationRegistry.getTargetContext();;
    private static final String EMAIL = "user@user.com";
    private static final String PASSWORD = "000000";
    private static final String NICKNAME = "userTest";
    private static final String NEXT = "próximo";
    private static final String END = "Fim";
    private static LoginController loginController;

    @BeforeClass
    public static void setup() throws Exception{
        ExplorerDAO databaseExplorer = new ExplorerDAO(context);
        BookDAO databaseBook = new BookDAO(context);
        ElementDAO databaseElement = new ElementDAO(context);
        loginController = new LoginController();

        databaseExplorer.onUpgrade(databaseExplorer.getWritableDatabase(), 1, 1);
        databaseBook.onUpgrade(databaseBook.getWritableDatabase(), 1, 1);
        databaseElement.onUpgrade(databaseElement.getWritableDatabase(), 1, 1);

        Explorer explorer = new Explorer(NICKNAME, EMAIL, PASSWORD, PASSWORD);
        databaseExplorer.insertExplorer(explorer);
        loginController.realizeLogin(EMAIL, context);
    }

    @Test
    public void testIfTutorialIsDisplayed(){
        release();
        tutorial.launchActivity(new Intent());
        for(int i = 0; i<27; i++) {
            onView(withText(NEXT))
                    .perform(click());
        }
        onView(withText(END))
                .perform(click());
    }

    @Test
    public void testOnBackPressed(){
        release();
        tutorial.launchActivity(new Intent());
        pressBack();
    }

    public static Matcher<Root> isPopupWindow() {
        return isPlatformPopup();
    }

    @AfterClass
    public static void tearDown(){
        new ExplorerDAO(context).deleteExplorer(new Explorer(EMAIL, PASSWORD));
        LoginController loginController = new LoginController();
        loginController.deleteFile(context);
    }
}
