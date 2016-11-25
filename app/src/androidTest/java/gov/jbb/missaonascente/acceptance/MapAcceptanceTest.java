package gov.jbb.missaonascente.acceptance;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import gov.jbb.missaonascente.R;
import gov.jbb.missaonascente.controller.LoginController;
import gov.jbb.missaonascente.dao.BookDAO;
import gov.jbb.missaonascente.dao.ElementDAO;
import gov.jbb.missaonascente.dao.ExplorerDAO;
import gov.jbb.missaonascente.model.Explorer;
import gov.jbb.missaonascente.view.MapActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class MapAcceptanceTest {
    @Rule
    public final IntentsTestRule<MapActivity> map = new IntentsTestRule<>(MapActivity.class);

    private static final Context context = InstrumentationRegistry.getTargetContext();
    private static final String EMAIL = "user@user.com";
    private static final String PASSWORD = "000000";
    private static final String NICKNAME = "userTest";
    private static LoginController loginController;

    @BeforeClass
    public static void setup() throws Exception{
        ExplorerDAO databaseExplorer = new ExplorerDAO(context);
        loginController = new LoginController();

        databaseExplorer.onUpgrade(databaseExplorer.getWritableDatabase(), 1, 1);

        Explorer explorer = new Explorer(NICKNAME, EMAIL, PASSWORD, PASSWORD);
        databaseExplorer.insertExplorer(explorer);
        loginController.realizeLogin(EMAIL, context);
    }


    @Test
    public void testIfBookWaterIsDisplayed(){
        try {
            onView(ViewMatchers.withId(R.id.chooseBookButton))
                    .perform(click());
            String water = "Águas";
            onView(withText(water))
                    .perform(click());
        }catch(OutOfMemoryError e){
            e.printStackTrace();
        }
    }

    @Test
    public void testIfBookDormancyIsDisplayed(){
        try {
            onView(withId(R.id.chooseBookButton))
                    .perform(click());
            String water = "Dormência";
            onView(withText(water))
                    .perform(click());
        }catch (OutOfMemoryError e){
            e.printStackTrace();
        }
    }

    @Test
    public void testIfBookRenovationIsDisplayed(){
        try {
            onView(withId(R.id.chooseBookButton))
                    .perform(click());
            String water = "Renovação";
            onView(withText(water))
                    .perform(click());
        }catch(OutOfMemoryError e){
            e.printStackTrace();
        }
    }
}
