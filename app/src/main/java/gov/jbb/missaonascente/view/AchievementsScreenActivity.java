package gov.jbb.missaonascente.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import gov.jbb.missaonascente.R;
import gov.jbb.missaonascente.model.Achievement;

public class AchievementsScreenActivity extends AppCompatActivity {
    private List<Achievement> achievements;
    private List<Achievement> explorerAchievements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements_screen);


    }


}
