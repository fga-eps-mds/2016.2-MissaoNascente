package gov.jbb.missaonascente;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Root;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.OngoingStubbing;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import gov.jbb.missaonascente.controller.EnergyController;
import gov.jbb.missaonascente.controller.LoginController;
import gov.jbb.missaonascente.controller.QuestionController;
import gov.jbb.missaonascente.dao.BookDAO;
import gov.jbb.missaonascente.dao.ElementDAO;
import gov.jbb.missaonascente.dao.ExplorerDAO;
import gov.jbb.missaonascente.model.Explorer;
import gov.jbb.missaonascente.view.MainScreenActivity;
import org.hamcrest.Matcher;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.Intents.release;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withInputType;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)

public class MainScreenAcceptanceTest{
    @Rule
    public final IntentsTestRule<MainScreenActivity> main = new IntentsTestRule<>(MainScreenActivity.class);

    private static final Context context = InstrumentationRegistry.getTargetContext();;
    private static final String EMAIL = "user@user.com";
    private static final String PASSWORD = "000000";
    private static final String NICKNAME = "userTest";
    private final long MIN_TIME = 120000;
    private final int MIN_ENERGY = 0;
    private static LoginController loginController;
    private static EnergyController energyController;
    private static QuestionController questionController;

    @BeforeClass
    public static void setup() throws Exception{
        ExplorerDAO databaseExplorer = new ExplorerDAO(context);
        BookDAO databaseBook = new BookDAO(context);
        ElementDAO databaseElement = new ElementDAO(context);
        loginController = new LoginController();
        energyController = new EnergyController(context);
        questionController = new QuestionController();

        databaseExplorer.onUpgrade(databaseExplorer.getWritableDatabase(), 1, 1);
        databaseBook.onUpgrade(databaseBook.getWritableDatabase(), 1, 1);
        databaseElement.onUpgrade(databaseElement.getWritableDatabase(), 1, 1);

        Explorer explorer = new Explorer(NICKNAME, EMAIL, PASSWORD, PASSWORD);
        databaseExplorer.insertExplorer(explorer);
        loginController.realizeLogin(EMAIL, context);
    }

    @Test
    public void testCamera() throws InterruptedException {
        release();
        main.launchActivity(new Intent());

        //mockup camera result
        Intent resultData = new Intent();
        resultData.putExtra(com.google.zxing.client.android.Intents.Scan.RESULT, "1");
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
        Matcher<Intent> matcher  = IntentMatchers.hasAction(com.google.zxing.client.android.Intents.Scan.ACTION);

        OngoingStubbing ongoingStubbing  = Intents.intending(matcher);
        ongoingStubbing.respondWith(result);
        //end mockup camera result

        onView(withId(R.id.readQrCodeButton))
                .perform(click());

        onView(withId(R.id.professor_fragment))
                .perform(click())
                .perform(click());
    }

    @Test
    public void testIfRankingIsDisplayed(){
        final String menuMoreRanking = "Ranking";
        release();
        main.launchActivity(new Intent());
        onView(withId(R.id.menuMoreButton))
                .perform(click());
        onView(withText(menuMoreRanking))
                .inRoot(isPopupWindow()).perform(click());
    }

    @Test
    public void testIfAchievementIsDisplayed(){
        final String menuMoreAchievement = "Conquistas";
        release();
        main.launchActivity(new Intent());
        onView(withId(R.id.menuMoreButton))
                .perform(click());
        onView(withText(menuMoreAchievement))
                .inRoot(isPopupWindow()).perform(click());
    }


    @Test
    public void testIfPreferenceScreenIsDisplayed(){
        release();
        main.launchActivity(new Intent());
        onView(withId(R.id.menuMoreButton))
                .perform(click());
        String menuMorePreferences = "Preference";
        onView(withText(menuMorePreferences))
                .inRoot(isPopupWindow()).perform(click());
    }

    @Test
    public void testIfAlmanacScreenIsDisplayed(){
        release();
        main.launchActivity(new Intent());
        onView(withId(R.id.almanacButton))
                .perform(click())
                .inRoot(isPopupWindow()).perform(click());

    }

    @Test
    public void testIfMapScreenIsDisplayed(){
        release();
        main.launchActivity(new Intent());
        onView(withId(R.id.menuMoreButton))
                .perform(click());
        String mapMenuMore = "Mapa da história";
        onView(withText(mapMenuMore))
                .perform(click());

        new ExplorerDAO(context).deleteExplorer(new Explorer(EMAIL, PASSWORD));
    }

    @Test
    public void testIfQuestionIsCalled(){
        questionController.setElapsedQuestionTime(MIN_TIME);
        energyController.setExplorerEnergyInDataBase(1,1);
        energyController.getExplorer().setEnergy(0);

        release();
        main.launchActivity(new Intent());

        onView(withId(R.id.readQrCodeButton))
                .perform(click());
    }

    @Test
    public void testIfQuestionMessageTimeIsCalled(){
        questionController.setElapsedQuestionTime(100);
        energyController.setExplorerEnergyInDataBase(1,1);
        energyController.getExplorer().setEnergy(MIN_ENERGY);

        release();
        main.launchActivity(new Intent());

        onView(withId(R.id.readQrCodeButton))
                .perform(click());
    }

    public static Matcher<Root> isPopupWindow() {
        return isPlatformPopup();
    }

}
