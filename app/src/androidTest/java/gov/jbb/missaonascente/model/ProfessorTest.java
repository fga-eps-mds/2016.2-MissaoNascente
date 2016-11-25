package gov.jbb.missaonascente.model;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import gov.jbb.missaonascente.model.Professor;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class ProfessorTest {

    public ProfessorTest(){

    }

    @Test
    public void testIfProfessorIsCreatedWithNoParameters() throws Exception{
        Professor professor = new Professor();

        assertNotNull(professor.getDialogs());
        assertNotNull(professor.getDrawables());
    }

    @Test
    public void testIfProfessorIsCreatedWithParameters() throws Exception{
        ArrayList<String> dialogs = new ArrayList<>();
        dialogs.add("First dialog");
        dialogs.add("Second dialog");

        ArrayList<Drawable> drawables = new ArrayList<>();
        drawables.add(new ColorDrawable(Color.BLUE));

        Professor professor = new Professor(dialogs, drawables);

        assertEquals(professor.getDialogs().size(), 2);
        assertEquals(professor.getDrawables().size(), 1);
    }

    @Test
    public void testIfSetDialogsAcceptsValidArray() throws Exception{
        Professor professor = new Professor();

        ArrayList<String> dialogs = new ArrayList<>();
        professor.setDialogs(dialogs);
    }

    @Test
    public void testIfSetDrawablesAcceptsValidArray() throws Exception{
        Professor professor = new Professor();

        ArrayList<Drawable> drawables = new ArrayList<>();
        professor.setDrawables(drawables);
    }

    @Test
    public void testIfGetDialogsReturnsValidArray() throws Exception{
        Professor professor = new Professor();
        ArrayList<String> dialogs = professor.getDialogs();
        assertNotNull(dialogs);
    }

    @Test
    public void testIfGetDrawablesReturnsValidArray() throws Exception{
        Professor professor = new Professor();
        ArrayList<Drawable> drawables = professor.getDrawables();
        assertNotNull(drawables);
    }
}
