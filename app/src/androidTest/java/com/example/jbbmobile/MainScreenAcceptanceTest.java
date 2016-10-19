package com.example.jbbmobile;

import android.support.test.espresso.Root;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.jbbmobile.view.LoginScreenActivity;
import com.example.jbbmobile.view.MainScreenActivity;
import com.example.jbbmobile.view.RegisterScreenActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)

public class MainScreenAcceptanceTest{
    @Rule
    public final ActivityTestRule<MainScreenActivity> main = new ActivityTestRule<>(MainScreenActivity.class);

    @LargeTest
    public class ExplorerAcceptanceTest {
        @Rule
        public final ActivityTestRule<RegisterScreenActivity> register = new ActivityTestRule<>(RegisterScreenActivity.class);

        @Test
        public void registerUser() {
            onView(withId(R.id.nicknameEditText))
                    .perform(typeText("user"));
            onView(withId(R.id.passwordEditText))
                    .perform(typeText("12345678"));
            onView(withId(R.id.passwordConfirmEditText))
                    .perform(typeText("12345678"))
                    .perform(closeSoftKeyboard());
            onView(withId(R.id.emailEditText))
                    .perform(typeText("user@email.com"))
                    .perform(closeSoftKeyboard());
            onView(withId(R.menu.settings_menu))
                    .perform(click());
        }
    }

    @LargeTest
    public class LogInAcceptanceTest{

        @Rule
        public final ActivityTestRule<LoginScreenActivity> login = new ActivityTestRule<>(LoginScreenActivity.class);

        @Test
        public void logUser() {
            onView(withId(R.id.emailEditText))
                    .perform(typeText("user@email.com"));
            onView(withId(R.id.passwordEditText))
                    .perform(typeText("12345678"))
                    .perform(closeSoftKeyboard());
            onView(withId(R.id.loginButton))
                    .perform(click());
        }
    }

    public static Matcher<Root> isPopupWindow() {
        return isPlatformPopup();
    }

    @Test
    public void testIfRankingIsDisplayed(){
        onView(withId(R.id.menuMoreButton))
                .perform(click());
        onView(withText("Ranking"))
                .inRoot(isPopupWindow());
    }

}