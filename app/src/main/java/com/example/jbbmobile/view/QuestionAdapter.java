package com.example.jbbmobile.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.QuestionController;
import com.example.jbbmobile.model.Alternative;
import com.example.jbbmobile.model.Question;

import java.util.List;

public class QuestionAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Question question;
    private Context context;
    public QuestionAdapter(Context context, Question question){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.question = question;
        this.context = context;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View listItem;
        listItem = inflater.inflate(R.layout.question_adapter,null);

        Holder holder = new Holder();
        holder.alternativeText= (TextView) listItem.findViewById(R.id.choice);
        final List<Alternative> alternativeList = question.getAlternativeList();
        holder.alternativeText.setText(alternativeList.get(i).getAlternativeDescription());

        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userAnswer = alternativeList.get(i).getAlternativeLetter();
                String correctAnswer = question.getCorrectAnswer();
                if(userAnswer.equals(correctAnswer)){
                    Toast.makeText(context, "Parabéns, você acertou!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Parabéns, você errou!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return listItem;
    }

    private class Holder{
        private TextView alternativeText;
    }
}