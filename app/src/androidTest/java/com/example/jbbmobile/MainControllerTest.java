package com.example.jbbmobile;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import com.example.jbbmobile.controller.LoginController;
import com.example.jbbmobile.controller.MainController;
import com.example.jbbmobile.controller.RegisterElementController;
import com.example.jbbmobile.view.StartScreenActivity;
import org.junit.Rule;



public class MainControllerTest {
    @Rule
    public final ActivityTestRule<StartScreenActivity> startScreen = new ActivityTestRule<>(StartScreenActivity.class) ;

    private Context context;
    private MainController mainController;
    private RegisterElementController registerElementController;
    private LoginController loginController;


    public MainControllerTest(){

        Context context = InstrumentationRegistry.getTargetContext();
        this.context = context;
        mainController = new MainController();

    }

}

