package gov.jbb.missaonascente.acceptance;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import gov.jbb.missaonascente.R;
import gov.jbb.missaonascente.controller.RegisterExplorerController;
import gov.jbb.missaonascente.dao.BookDAO;
import gov.jbb.missaonascente.dao.ElementDAO;
import gov.jbb.missaonascente.dao.ExplorerDAO;
import gov.jbb.missaonascente.model.Explorer;
import gov.jbb.missaonascente.view.LoginScreenActivity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LoginAcceptanceTest {
    public final ActivityTestRule<LoginScreenActivity> loginScreen = new ActivityTestRule<LoginScreenActivity>(LoginScreenActivity.class);
    private final Context context = InstrumentationRegistry.getTargetContext();;
    private final String EMAIL = "user@user.com";
    private final String PASSWORD = "000000";
    private final String NICKNAME = "testUser";

    @Before
    public void setup(){
        RegisterExplorerController register = new RegisterExplorerController();
        register.register(NICKNAME, EMAIL, PASSWORD, PASSWORD, context);
    }

    @Test
    public void testLogin(){
        loginScreen.launchActivity(new Intent());

        onView(ViewMatchers.withId(R.id.emailEditText))
                .perform(typeText(EMAIL))
                .perform(closeSoftKeyboard());
        onView(withId(R.id.passwordEditText))
                .perform(typeText(PASSWORD))
                .perform(closeSoftKeyboard());
        onView(withId(R.id.loginButton))
                .perform(click());

        new ExplorerDAO(context).deleteExplorer(new Explorer(EMAIL, PASSWORD));
    }
}
