package gov.jbb.missaonascente.acceptance;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Root;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.OngoingStubbing;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import gov.jbb.missaonascente.R;
import gov.jbb.missaonascente.controller.EnergyController;
import gov.jbb.missaonascente.controller.LoginController;
import gov.jbb.missaonascente.controller.MainController;
import gov.jbb.missaonascente.controller.QuestionController;
import gov.jbb.missaonascente.dao.AlternativeDAO;
import gov.jbb.missaonascente.dao.BookDAO;
import gov.jbb.missaonascente.dao.ElementDAO;
import gov.jbb.missaonascente.dao.ExplorerDAO;
import gov.jbb.missaonascente.dao.QuestionDAO;
import gov.jbb.missaonascente.model.Alternative;
import gov.jbb.missaonascente.model.Element;
import gov.jbb.missaonascente.model.Explorer;
import gov.jbb.missaonascente.model.Question;
import gov.jbb.missaonascente.view.MainScreenActivity;
import org.hamcrest.Matcher;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.Intents.release;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)

public class MainScreenAcceptanceTest{
    @Rule
    public final IntentsTestRule<MainScreenActivity> main = new IntentsTestRule<>(MainScreenActivity.class);

    private static final Context context = InstrumentationRegistry.getTargetContext();
    private static final String EMAIL = "user@user.com";
    private static final String PASSWORD = "000000";
    private static final String NICKNAME = "userTest";
    private static LoginController loginController;

    @BeforeClass
    public static void setup() throws Exception{
        ExplorerDAO databaseExplorer = new ExplorerDAO(context);
        loginController = new LoginController();

        //MainController mainController = new MainController();
        //mainController.checkIfUpdateIsNeeded(context);

        Explorer explorer = new Explorer(NICKNAME, EMAIL, PASSWORD, PASSWORD);
        databaseExplorer.insertExplorer(explorer);
        loginController.realizeLogin(EMAIL, context);

        Thread.sleep(5000);
    }

    @Test
    public void testRegisterNormalElement() throws InterruptedException {
        release();
        main.launchActivity(new Intent());

        createCameraMockup("3");

        onView(ViewMatchers.withId(R.id.readQrCodeButton))
                .perform(click());

        onView(withId(R.id.professor_fragment))
                .perform(click())
                .perform(click());

        onView(withId(R.id.show_element_button))
                .perform(click());
        pressBack();

    }

    /*
    @Test
    public void testTakePhotoOfElement() throws Exception{
        release();
        main.launchActivity(new Intent());

        createCameraMockup("4");

        onView(ViewMatchers.withId(R.id.readQrCodeButton))
                .perform(click());

        Bitmap icon = BitmapFactory.decodeResource(
                InstrumentationRegistry.getTargetContext().getResources(),
                R.mipmap.ic_launcher);

        Intent resultData = new Intent();
        resultData.putExtra("data", icon);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        intending(hasAction("android.media.action.IMAGE_CAPTURE")).respondWith(result);

        onView(withId(R.id.camera_button))
                .perform(click());

        intended(hasAction("android.media.action.IMAGE_CAPTURE"));

        onView(withId(R.id.show_element_button))
                .perform(click());
    }
    */

    @Test
    public void testInvalidElement() throws InterruptedException {
        release();
        main.launchActivity(new Intent());

        createCameraMockup("9999999");

        onView(withId(R.id.readQrCodeButton))
                .perform(click());

        onView(withId(R.id.professor_fragment))
                .perform(click())
                .perform(click());

    }

    @Test
    public void testRegisterEnergyElement() throws InterruptedException {
        release();
        main.launchActivity(new Intent());

        ElementDAO elementDAO = new ElementDAO(main.getActivity());
        Element element =  new Element(19000, 19000, 100, "ponto_2", "Teste", 3, "", 20, 0,"");

        elementDAO.insertElement(element);
        createCameraMockup("19000");

        onView(withId(R.id.readQrCodeButton))
                .perform(click());

        onView(withId(R.id.professor_fragment))
                .perform(click())
                .perform(click());

        onView(withId(R.id.show_element_button))
                .perform(click());
        pressBack();
    }

   /*@Test
    public void testAlreadyRegisteredElement() throws InterruptedException {
        release();
        main.launchActivity(new Intent());

        ElementDAO elementDAO = new ElementDAO(main.getActivity());
        Element element =  new Element(19000, 19000, 100, "ponto_2", "Teste", 3, "", -10, 0,"");

        elementDAO.insertElement(element);
        createCameraMockup("19000");

        onView(withId(R.id.readQrCodeButton))
                .perform(click());

        onView(withId(R.id.close_button))
                .perform(click());

        createCameraMockup("19000");

        onView(withId(R.id.readQrCodeButton))
                .perform(click());

        onView(withId(R.id.professor_fragment))
                .perform(click())
                .perform(click());

    }*/

    @Test
    public void test() throws InterruptedException {
        release();
        main.launchActivity(new Intent());

        ElementDAO elementDAO = new ElementDAO(main.getActivity());
        Element element =  new Element(19000, 19000, 100, "ponto_2", "Teste", 3, "", -10, 0,"");

        elementDAO.insertElement(element);
        createCameraMockup("19000");

        onView(withId(R.id.readQrCodeButton))
                .perform(click());

        onView(withId(R.id.professor_fragment))
                .perform(click())
                .perform(click());

        createCameraMockup("19000");

        onView(withId(R.id.close_button))
                .perform(click());

        onView(withId(R.id.readQrCodeButton))
                .perform(click());

        onView(withId(R.id.professor_fragment))
                .perform(click())
                .perform(click());

    }



    @Test
    public void testRegisterHistoryElement() throws InterruptedException {
        release();
        main.launchActivity(new Intent());

        createCameraMockup("1");

        onView(withId(R.id.readQrCodeButton))
                .perform(click());

        onView(withId(R.id.professor_fragment))
                .perform(click())
                .perform(click());
    }

    @Test
    public void testExplorerCorrectedAnsweredQuestionTrueOrFalse(){
        EnergyController energyController = new EnergyController(main.getActivity());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(main.getActivity());
        sharedPreferences.edit().remove("questionTime").apply();
        energyController.setExplorerEnergyInDataBase(0,0);

        QuestionDAO questionDAO = new QuestionDAO(main.getActivity());
        questionDAO.onUpgrade(questionDAO.getWritableDatabase(),1,1);

        Question question = new Question(1,"Teste","a",2);

        questionDAO.insertQuestion(question);

        release();

        main.launchActivity(new Intent());

        onView(withId(R.id.readQrCodeButton))
                .perform(click());

        onView(withId(R.id.professor_fragment))
                .perform(click())
                .perform(click());



        onView(withText(R.string.trueAlternative))
                .perform(click());

    }

    @Test
    public void testExplorerIncorrectAnsweredQuestionTrueOrFalse(){
        EnergyController energyController = new EnergyController(main.getActivity());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(main.getActivity());
        sharedPreferences.edit().remove("questionTime").apply();
        energyController.setExplorerEnergyInDataBase(0,0);

        QuestionDAO questionDAO = new QuestionDAO(main.getActivity());
        questionDAO.onUpgrade(questionDAO.getWritableDatabase(),1,1);

        Question question = new Question(1,"Teste","a",2);

        questionDAO.insertQuestion(question);

        release();

        main.launchActivity(new Intent());

        onView(withId(R.id.readQrCodeButton))
                .perform(click());

        onView(withId(R.id.professor_fragment))
                .perform(click())
                .perform(click());



        onView(withText(R.string.falseAlternative))
                .perform(click());

    }

    @Test
    public void testExplorerCorrectedAnsweredQuestionAndAlternatives(){
        EnergyController energyController = new EnergyController(main.getActivity());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(main.getActivity());
        sharedPreferences.edit().remove("questionTime").apply();
        energyController.setExplorerEnergyInDataBase(0,0);

        QuestionDAO questionDAO = new QuestionDAO(main.getActivity());
        questionDAO.onUpgrade(questionDAO.getWritableDatabase(),1,1);
        Question question = new Question(1,"Teste","a",4);
        questionDAO.insertQuestion(question);

        AlternativeDAO alternativeDAO = new AlternativeDAO(main.getActivity());
        alternativeDAO.onUpgrade(alternativeDAO.getWritableDatabase(),1,1);

        Alternative alternative1 = new Alternative(1, "a", "Correta",1);
        alternativeDAO.insertAlternative(alternative1);
        Alternative alternative2 = new Alternative(2, "b", "Errada B",1);
        alternativeDAO.insertAlternative(alternative2);
        Alternative alternative3 = new Alternative(3, "c", "Errada C",1);
        alternativeDAO.insertAlternative(alternative3);
        Alternative alternative4 = new Alternative(4, "d", "Errada D",1);
        alternativeDAO.insertAlternative(alternative4);


        release();

        main.launchActivity(new Intent());

        onView(withId(R.id.readQrCodeButton))
                .perform(click());

        onView(withId(R.id.professor_fragment))
                .perform(click())
                .perform(click());


        onView(withText("Correta"))
                .perform(click());
    }



    @Test
    public void testExplorerIncorrectedAnsweredQuestionAndAlternatives(){
        EnergyController energyController = new EnergyController(main.getActivity());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(main.getActivity());
        sharedPreferences.edit().remove("questionTime").apply();
        energyController.setExplorerEnergyInDataBase(0,0);

        QuestionDAO questionDAO = new QuestionDAO(main.getActivity());
        questionDAO.onUpgrade(questionDAO.getWritableDatabase(),1,1);
        Question question = new Question(1,"Teste","a",4);
        questionDAO.insertQuestion(question);

        AlternativeDAO alternativeDAO = new AlternativeDAO(main.getActivity());
        alternativeDAO.onUpgrade(alternativeDAO.getWritableDatabase(),1,1);

        Alternative alternative1 = new Alternative(1, "a", "Correta",1);
        alternativeDAO.insertAlternative(alternative1);
        Alternative alternative2 = new Alternative(2, "b", "Errada B",1);
        alternativeDAO.insertAlternative(alternative2);
        Alternative alternative3 = new Alternative(3, "c", "Errada C",1);
        alternativeDAO.insertAlternative(alternative3);
        Alternative alternative4 = new Alternative(4, "d", "Errada D",1);
        alternativeDAO.insertAlternative(alternative4);


        release();

        main.launchActivity(new Intent());

        onView(withId(R.id.readQrCodeButton))
                .perform(click());

        onView(withId(R.id.professor_fragment))
                .perform(click())
                .perform(click());



        onView(withText("Errada C"))
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
        pressBack();
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
        pressBack();
    }


    @Test
    public void testIfPreferenceScreenIsDisplayed(){
        release();
        main.launchActivity(new Intent());
        onView(withId(R.id.menuMoreButton))
                .perform(click());
        String menuMorePreferences = main.getActivity().getString(R.string.preferences);
        onView(withText(menuMorePreferences))
                .inRoot(isPopupWindow()).perform(click());
        pressBack();
    }

    @Test
    public void testIfMapScreenIsDisplayed(){
        release();
        main.launchActivity(new Intent());
        onView(withId(R.id.menuMoreButton))
                .perform(click());
        String menuMorePreferences = main.getActivity().getString(R.string.map);
        onView(withText(menuMorePreferences))
                .inRoot(isPopupWindow()).perform(click());
        pressBack();

    }

    @Test
    public void testIfAlmanacScreenIsDisplayed(){
        release();
        main.launchActivity(new Intent());
        onView(withId(R.id.almanacButton))
                .perform(click());
        pressBack();
    }

    @Test
    public void testPeriodsAlmanac(){
        release();
        main.launchActivity(new Intent());
        onView(withId(R.id.almanacButton))
                .perform(click());

        onView(withId(R.id.orangeBook))
                .perform(click());

        onView(withId(R.id.greenBook))
                .perform(click());

        onView(withId(R.id.blueBook))
                .perform(click());
        pressBack();
    }

    @AfterClass
    public static void tearDown(){
        new ExplorerDAO(context).deleteExplorer(new Explorer(EMAIL, PASSWORD));
        loginController.deleteFile(context);
    }

    private void createCameraMockup(String qrCode){
        //mockup camera result
        Intent resultData = new Intent();
        resultData.putExtra(com.google.zxing.client.android.Intents.Scan.RESULT, qrCode);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
        Matcher<Intent> matcher  = hasAction(com.google.zxing.client.android.Intents.Scan.ACTION);

        OngoingStubbing ongoingStubbing  = intending(matcher);
        ongoingStubbing.respondWith(result);
        //end mockup camera result
    }

    public static Matcher<Root> isPopupWindow() {
        return isPlatformPopup();
    }


}
