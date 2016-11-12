package gov.jbb.missaonascente.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import gov.jbb.missaonascente.R;
import gov.jbb.missaonascente.model.Alternative;
import gov.jbb.missaonascente.model.Question;

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
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View listItem;
        listItem = inflater.inflate(R.layout.question_adapter,null);

        Holder holder = new Holder();
        holder.alternativeText = (TextView) listItem.findViewById(R.id.choice);
        final List<Alternative> alternativeList = question.getAlternativeList();
        holder.alternativeText.setText(alternativeList.get(position).getAlternativeDescription());

        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userAnswer = alternativeList.get(position).getAlternativeLetter();
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