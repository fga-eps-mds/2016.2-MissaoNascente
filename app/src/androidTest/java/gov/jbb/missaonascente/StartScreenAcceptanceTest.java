package gov.jbb.missaonascente;

import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;
import org.junit.Test;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import gov.jbb.missaonascente.view.StartScreenActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class StartScreenAcceptanceTest {
    private final ActivityTestRule<StartScreenActivity> start = new ActivityTestRule<>(StartScreenActivity.class);


    @Test
    public void testClickNormalSignIn() throws Exception{
        start.launchActivity(new Intent());
        onView(withId(R.id.normalSignIn))
                .perform(click());
    }

    @Test
    public void testClickRegisterButton() throws Exception{
        start.launchActivity(new Intent());
        onView(withId(R.id.createAccount))
                .perform(click());
    }



}
