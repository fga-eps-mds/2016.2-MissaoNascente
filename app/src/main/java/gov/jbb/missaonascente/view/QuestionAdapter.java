package gov.jbb.missaonascente.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import gov.jbb.missaonascente.R;
import gov.jbb.missaonascente.controller.AchievementController;
import gov.jbb.missaonascente.controller.EnergyController;
import gov.jbb.missaonascente.controller.LoginController;
import gov.jbb.missaonascente.controller.QuestionController;
import gov.jbb.missaonascente.model.Achievement;
import gov.jbb.missaonascente.model.Alternative;
import gov.jbb.missaonascente.model.Explorer;
import gov.jbb.missaonascente.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Question question;
    private TextView itemQuestion;
    private Explorer explorer = new Explorer();
    private LoginController loginController = new LoginController();
    private QuestionFragment questionFragment;
    private QuestionController questionController;
    private MainScreenActivity mainScreenActivity;
    private EnergyController energyController;
    private Integer energyQuestion = 10;
    
    public QuestionAdapter(QuestionFragment questionFragment, Question question){

        this.question = question;
        this.questionFragment = questionFragment;
        this.questionController = questionFragment.getQuestionController();
        this.mainScreenActivity = (MainScreenActivity)questionFragment.getActivity();
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        explorer = loginController.getExplorer();
        energyController = new EnergyController(getContext());
        //mainScreenActivity = new MainScreenActivity();
    }

    public Context getContext(){
        return mainScreenActivity;
    }


    @Override
    public int getCount() {
        return question.getAlternativeQuantity();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View listItem;
        listItem = inflater.inflate(R.layout.question_adapter,null);
        itemQuestion = (TextView) listItem.findViewById(R.id.choice);
        int correctColor = R.drawable.choice_correct;
        int wrongColor = R.drawable.choice_wrong;

        final Holder holder = new Holder();
        holder.alternativeText = (TextView) listItem.findViewById(R.id.choice);
        final List<Alternative> alternativeList = question.getAlternativeList();
        holder.alternativeText.setText(alternativeList.get(position).getAlternativeDescription());
        String userAnswer = alternativeList.get(position).getAlternativeLetter();
        String correctAnswer = question.getCorrectAnswer();
        if(userAnswer.equals(correctAnswer)) {
            holder.alternativeText.findViewById(R.id.choice).setBackground(ContextCompat.getDrawable(getContext(), correctColor));
        } else {
            holder.alternativeText.findViewById(R.id.choice).setBackground(ContextCompat.getDrawable(getContext(), wrongColor));
        }
        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userAnswer = alternativeList.get(position).getAlternativeLetter();
                String correctAnswer = question.getCorrectAnswer();

                int isRight;
                if(userAnswer.equals(correctAnswer)){
                    isRight = 1;
                    mainScreenActivity.questionEnergy();
                    mainScreenActivity.callProfessor("Parabéns, você acertou!");

                }else{
                    isRight = 0;
                    mainScreenActivity.callProfessor("Que pena, você errou!");
                }

                ArrayList<Achievement> newAchievements =
                        questionController.checkForNewAchievements(getContext(), explorer);

                for(Achievement newAchievement : newAchievements){
                    mainScreenActivity.createAchievementToast(newAchievement);
                }

                questionController.updateQuestionCounters(getContext(), explorer, isRight);

                questionFragment.removeFragment();
            }

        });

        return listItem;
    }

    private class Holder{
        private TextView alternativeText;
    }

}