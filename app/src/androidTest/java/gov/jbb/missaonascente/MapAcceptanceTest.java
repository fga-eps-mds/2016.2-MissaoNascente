package gov.jbb.missaonascente;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

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
    public void testIfBookWaterIsDisplayed(){
        onView(withId(R.id.chooseBookButton))
                .perform(click());
        String water = "Águas";
        onView(withText(water))
                .perform(click());
    }

    @Test
    public void testIfBookDormancyIsDisplayed(){
        onView(withId(R.id.chooseBookButton))
                .perform(click());
        String water = "Dormência";
        onView(withText(water))
                .perform(click());
    }

    @Test
    public void testIfBookRenovationIsDisplayed(){
        onView(withId(R.id.chooseBookButton))
                .perform(click());
        String water = "Renovação";
        onView(withText(water))
                .perform(click());
    }
}
