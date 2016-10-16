package com.example.jbbmobile;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.example.jbbmobile.controller.ElementsController;
import com.example.jbbmobile.controller.MainController;
import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.model.Element;
import com.example.jbbmobile.view.ElementScreenActivity;
import com.example.jbbmobile.view.MainScreenActivity;
import com.example.jbbmobile.view.StartScreenActivity;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by hugo on 10/10/16.
 */

public class ElementsControllerTest {
    @Rule
    public final ActivityTestRule<StartScreenActivity> start = new ActivityTestRule<>(StartScreenActivity.class);
    private Context context;
    private ElementsController elementsController;

    public ElementsControllerTest(){
        Context context = InstrumentationRegistry.getTargetContext();
        this.context = context;
        elementsController = new ElementsController();
    }

    // TODO testar ElementController
}

















