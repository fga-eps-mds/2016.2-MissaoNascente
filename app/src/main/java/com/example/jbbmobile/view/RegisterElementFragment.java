package com.example.jbbmobile.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.MainController;
import com.example.jbbmobile.controller.RegisterElementController;
import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.model.Element;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.zip.Inflater;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterElementFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisterElementFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class RegisterElementFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "Fragment";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_TAKE_PHOTO = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private TextView nameText;
    private ImageButton closeButton;
    private ImageButton showElementButton;
    private ImageButton cameraButton;
    private ImageView elementImage;
    private int idElement;
    private RegisterElementController registerElementController = new RegisterElementController();

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterElementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterElementFragment newInstance(String param1, String param2) {
        RegisterElementFragment fragment = new RegisterElementFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public RegisterElementFragment() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView");
        view = inflater.inflate(R.layout.fragment_register_element, container, false);

        closeButton = (ImageButton) view.findViewById(R.id.close_button);
        closeButton.setOnClickListener(onCloseButtonClick());

        cameraButton = (ImageButton) view.findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(onCameraButtonClick());

        showElementButton = (ImageButton) view.findViewById(R.id.show_element_button);
        showElementButton.setOnClickListener(onShowElementButtonClick());

        elementImage = (ImageView) view.findViewById(R.id.element_image);
        nameText = (TextView) view.findViewById(R.id.name_text);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    public void showElement(Element element){
        int resID = getResources().getIdentifier(element.getDefaultImage(), "drawable", getActivity().getPackageName());
        idElement = element.getIdElement();
        elementImage.setImageResource(resID);
        nameText.setText(element.getNameElement());
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private View.OnClickListener onCloseButtonClick () {
       return new ImageButton.OnClickListener() {
           @Override
           public void onClick(View v) {
               getActivity().findViewById(R.id.register_fragment).setVisibility(View.GONE);
           }
       };
    }

    private View.OnClickListener onShowElementButtonClick () {
        return new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ElementScreenActivity.class);
                intent.putExtra("idElement", idElement);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener onCameraButtonClick () {
        return new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        };
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            elementImage.setImageBitmap(imageBitmap);
//        }
//    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            File storageDirectory = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            try {
                Log.d("Error Here!","ERROR");
                photoFile = registerElementController.createImageFile(storageDirectory, idElement);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(), "com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
}