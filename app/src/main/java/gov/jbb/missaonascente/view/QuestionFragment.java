package gov.jbb.missaonascente.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gov.jbb.missaonascente.R;
import gov.jbb.missaonascente.controller.QuestionController;
import gov.jbb.missaonascente.model.Question;

public class QuestionFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private QuestionController questionController;
    private Question question;

    public QuestionFragment() {}

    public static QuestionFragment newInstance(String param1, String param2) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        questionController = new QuestionController();
        question = questionController.getDraftQuestion(getContext());

        View view = inflater.inflate(R.layout.fragment_question, container, false);
        TextView questionTextView = (TextView) view.findViewById(R.id.questionTextView);
        questionTextView.setText(question.getDescription());

        ListView listView = (ListView) view.findViewById(R.id.questionListView);
        listView.setAdapter(new QuestionAdapter(this, question));

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void removeFragment(){

        if (this != null && this.isVisible()) {
            getActivity().findViewById(R.id.readQrCodeButton).setVisibility(View.VISIBLE);
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            //ft.remove(this).commitNow();

            getActivity().findViewById(R.id.mainScreenUp).setBackgroundColor(0x00000000);
            getActivity().findViewById(R.id.menuMoreButton).setClickable(true);
            getActivity().findViewById(R.id.almanacButton).setClickable(true);
            getActivity().findViewById(R.id.readQrCodeButton).setClickable(true);

            ft.remove(this).commitNow();
        }
    }
}