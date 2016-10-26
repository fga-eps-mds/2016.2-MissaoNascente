/*
package com.example.jbbmobile;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.example.jbbmobile.controller.RegisterExplorerController;
import com.example.jbbmobile.dao.BookDAO;
import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Explorer;
import com.example.jbbmobile.view.LoginScreenActivity;
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
    public void testLogin(){
        loginScreen.launchActivity(new Intent());

        onView(withId(R.id.emailEditText))
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
*/
