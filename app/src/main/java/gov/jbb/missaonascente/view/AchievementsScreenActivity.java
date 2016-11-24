package gov.jbb.missaonascente.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import gov.jbb.missaonascente.R;
import gov.jbb.missaonascente.controller.AchievementController;
import gov.jbb.missaonascente.controller.LoginController;
import gov.jbb.missaonascente.model.Achievement;

public class AchievementsScreenActivity extends AppCompatActivity {
    private ArrayList<Achievement> achievements;
    private RecyclerView recyclerView;
    private AchievementController achievementController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements_screen);
        Log.d("onCreateAchievement", "onCreateAchievement");

        recyclerView = (RecyclerView) findViewById(R.id.achievementsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        LoginController loginController = new LoginController();
        loginController.loadFile(getApplicationContext());

        achievementController = new AchievementController(this);
        achievements = achievementController.getAllAchievements(this, loginController.getExplorer());
    }

    @Override
    protected void onResume(){
        super.onResume();
        recyclerView.setAdapter(new AchievementAdapter(this, achievements));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mainScreenIntent = new Intent(AchievementsScreenActivity.this, MainScreenActivity.class);
        AchievementsScreenActivity.this.startActivity(mainScreenIntent);
        finish();
    }
}
