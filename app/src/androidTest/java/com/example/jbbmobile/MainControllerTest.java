package com.example.jbbmobile;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.example.jbbmobile.controller.BooksController;
import com.example.jbbmobile.controller.ElementsController;
import com.example.jbbmobile.controller.MainController;
import com.example.jbbmobile.model.Element;
import com.example.jbbmobile.view.StartScreenActivity;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainControllerTest {
    @Rule
    public final ActivityTestRule<StartScreenActivity> startScreen = new ActivityTestRule<>(StartScreenActivity.class) ;

    private Context context;
    private MainController mainController;
    private ElementsController elementsController;

    public MainControllerTest(){

        Context context = InstrumentationRegistry.getTargetContext();
        this.context = context;
        mainController = new MainController();
        elementsController = new ElementsController();
    }
}

