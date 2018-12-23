package com.example.renluyensuckhoe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Profile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView tvHoTen;
    private TextView tvCanNang;
    private TextView tvChieuCao;
    private TextView tvBMI;

    private OnFragmentInteractionListener mListener;

    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        tvHoTen = (TextView)view.findViewById(R.id.textViewHoTen);
        tvCanNang = (TextView) view.findViewById(R.id.textViewCanNang);
        tvChieuCao = (TextView) view.findViewById(R.id.textViewChieuCao);
        tvBMI = (TextView)view.findViewById(R.id.textViewBMI);


        SharedPreferences sharepr = this.getActivity().getSharedPreferences("thongtin",0);
        final SharedPreferences.Editor editor= sharepr.edit();
        String HoTen = (sharepr.getString("Name","xin chao"));
        int Gender = (sharepr.getInt("spinnerSelection",0));
        int iCanNang = (sharepr.getInt("Weight", 0));
        int iChieuCao = (sharepr.getInt("Height",0));
        float fBMI = (sharepr.getFloat("BMI",0));
        String sCanNang = "Chiều Cao:"+ String.valueOf(iChieuCao)+" cm";
        String sChieuCao = "Cân Nặng:"+String.valueOf(iCanNang)+" kg";
        String sBMI = "Chỉ số BMI:" + String.valueOf(fBMI);


        tvHoTen.setText(HoTen);
        tvCanNang.setText(sCanNang);
        tvChieuCao.setText(sChieuCao);
        tvBMI.setText(sBMI);
        if(Gender == 0) {
            ImageView avatar = (ImageView)view.findViewById(R.id.imageViewAvatar);
            avatar.setImageResource(R.mipmap.ic_avatar_male);
        }
        if(Gender == 1) {
            ImageView avatar = (ImageView)view.findViewById(R.id.imageViewAvatar);
            avatar.setImageResource(R.mipmap.ic_avatar_female);
        }

        Button btnNhapLai = (Button)view.findViewById(R.id.buttonNhapLai);
        btnNhapLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("isFilledOut",false);
                editor.commit();
                Intent i = new Intent(getActivity(),Main2Activity.class);
                startActivity(i);
            }
        });
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
}
