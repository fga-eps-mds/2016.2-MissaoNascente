package gov.jbb.missaonascente;

import org.hamcrest.Matcher;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Root;
import android.support.test.rule.ActivityTestRule;

import android.support.test.runner.AndroidJUnit4;

import gov.jbb.missaonascente.controller.LoginController;
import gov.jbb.missaonascente.controller.RegisterExplorerController;
import gov.jbb.missaonascente.dao.BookDAO;
import gov.jbb.missaonascente.dao.ElementDAO;
import gov.jbb.missaonascente.dao.ExplorerDAO;
import gov.jbb.missaonascente.model.Explorer;
import gov.jbb.missaonascente.view.PreferenceScreenActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;

import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)


public class PreferenceControllerAcceptanceTest {
    private final ActivityTestRule<PreferenceScreenActivity> preference = new ActivityTestRule<>(PreferenceScreenActivity.class);
    private static final Context context = InstrumentationRegistry.getTargetContext();
    private static final String EMAIL = "user@user.com";
    private static final String PASSWORD = "000000";
    private final String NICKNAME = "testUser2";
    private final String NEW_NICKNAME = "UserTest";
    private final String OK_BUTTON = "OK";
    private final String CANCEL_BUTTON = "Cancel";
    private final String YES_BUTTON = "Sim";


    @Before
    public void setup(){
        ExplorerDAO databaseExplorer = new ExplorerDAO(context);
        databaseExplorer.onUpgrade(databaseExplorer.getWritableDatabase(), 1, 1);
        BookDAO databaseBook = new BookDAO(context);
        databaseBook.onUpgrade(databaseBook.getWritableDatabase(), 1, 1);
        ElementDAO databaseElement = new ElementDAO(context);
        databaseElement.onUpgrade(databaseElement.getWritableDatabase(), 1, 1);
        RegisterExplorerController register = new RegisterExplorerController();
        register.register(NICKNAME, EMAIL, PASSWORD, PASSWORD, context);
        LoginController login = new LoginController();
        login.doLogin(EMAIL, PASSWORD, context);
        while(!login.isAction());
    }


    @Test
    public void testIfAccountIsDeleted(){
        preference.launchActivity(new Intent());
        new Thread(){
            @Override
            public void run() {
                onView(withId(R.id.deleteAccount))
                        .perform(click());
                onView(withId(R.id.deleteAccountEditText))
                        .perform(typeText(PASSWORD))
                        .perform(closeSoftKeyboard());
                onView(withText(OK_BUTTON))
                        .perform(click())
                        .inRoot(isPopupWindow());
            }
        }.run();
    }

    @Test
    public void testExplorerPasswordWrong(){
        preference.launchActivity(new Intent());
        onView(withId(R.id.deleteAccount))
                .perform(click());
        onView(withId(R.id.deleteAccountEditText))
                .perform(typeText("qqqqqq"))
                .perform(closeSoftKeyboard());
        onView(withText(OK_BUTTON))
                .perform(click())
                .inRoot(isPopupWindow());
    }

    @Test
    public void testIfNicknameChangedIsCancel() throws Exception{
        preference.launchActivity(new Intent());
        onView(withId(R.id.editNicknameButton))
                .perform(click());
        onView(withId(R.id.editNicknameEditText))
                .perform(typeText(NEW_NICKNAME));
        onView(withText(CANCEL_BUTTON))
                .perform(click());

    }

    @Test
    public void testNicknameWrong(){
        preference.launchActivity(new Intent());
        onView(withId(R.id.editNicknameButton))
                .perform(click());
        onView(withId(R.id.editNicknameEditText))
                .perform(typeText("t"));
        onView(withText(OK_BUTTON))
                .perform(click());
        onView(withText(OK_BUTTON))
                .perform(click()).inRoot(isPopupWindow());
    }

    @Test
    public void testIfSignOutApp() throws Exception{
        preference.launchActivity(new Intent());
        onView(withId(R.id.signOutButton)).perform(click());
        onView(withText(YES_BUTTON)).perform(click());
    }

    @Test
    public void testFormatPasswordWrong(){
        preference.launchActivity(new Intent());
        onView(withId(R.id.deleteAccount))
                .perform(click());
        onView(withId(R.id.deleteAccountEditText))
                .perform(typeText("qqq"))
                .perform(closeSoftKeyboard());
        onView(withText(OK_BUTTON))
                .perform(click())
                .inRoot(isPopupWindow());
        onView(withText(OK_BUTTON))
                .perform(click())
                .inRoot(isPopupWindow());
    }

    @Test
    public void testEditAccount(){
        RegisterExplorerController registerExplorerController = new RegisterExplorerController();
        registerExplorerController.register("Roger", "rogerlenke@gmail.com", "000000", "000000", this.context);

        while(!registerExplorerController.isAction());

        LoginController loginController = new LoginController();
        loginController.doLogin("rogerlenke@gmail.com", "000000", this.context);

        preference.launchActivity(new Intent());
        onView(withId(R.id.deleteAccount))
                .perform(click());
        onView(withText(OK_BUTTON))
                .perform(click())
                .inRoot(isPopupWindow());
    }

    @AfterClass
    public static void tearDown(){
        new ExplorerDAO(context).deleteExplorer(new Explorer(EMAIL, PASSWORD));
    }

    public static Matcher<Root> isPopupWindow() {
        return isPlatformPopup();
    }

}

