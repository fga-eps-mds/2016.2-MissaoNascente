package gov.jbb.missaonascente;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Root;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import gov.jbb.missaonascente.controller.LoginController;
import gov.jbb.missaonascente.dao.BookDAO;
import gov.jbb.missaonascente.dao.ElementDAO;
import gov.jbb.missaonascente.dao.ExplorerDAO;
import gov.jbb.missaonascente.model.Explorer;
import gov.jbb.missaonascente.view.MainScreenActivity;

import org.hamcrest.Matcher;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)

public class MainScreenAcceptanceTest{
    private final ActivityTestRule<MainScreenActivity> main = new ActivityTestRule<>(MainScreenActivity.class);
    private static final Context context = InstrumentationRegistry.getTargetContext();;
    private static final String EMAIL = "user@user.com";
    private static final String PASSWORD = "000000";
    private static final String NICKNAME = "userTest";
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
