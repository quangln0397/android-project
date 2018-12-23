package com.example.renluyensuckhoe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;


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
    //private TextView tvCanNang;
    //private TextView tvChieuCao;
    //private TextView tvBMI;

    private Button button_height;
    private Button button_weight;
    private Button button_bmi;
    private ImageView imageview_body;
    private Button button_danhgiabody;
    private Button button_date;



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
        view.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tvHoTen = (TextView)view.findViewById(R.id.textViewHoTen);
       // tvCanNang = (TextView) view.findViewById(R.id.textViewCanNang);
       // tvChieuCao = (TextView) view.findViewById(R.id.textViewChieuCao);

        button_height = (Button)view.findViewById(R.id.button_chieucao);
        button_weight = (Button)view.findViewById(R.id.button_cannang);
        button_bmi = (Button)view.findViewById(R.id.button_chisobmi);
        imageview_body = (ImageView)view.findViewById(R.id.imageView_body);
        button_danhgiabody = (Button)view.findViewById(R.id.button_danhgia);
        button_date = (Button)view.findViewById(R.id.button_date);



        SharedPreferences sharepr = this.getActivity().getSharedPreferences("thongtin",0);
        final SharedPreferences.Editor editor= sharepr.edit();
        String sDate = (sharepr.getString("NgayBatDau","NULL"));
        sDate = "Ngày bắt đầu: "+ sDate;
        String HoTen = (sharepr.getString("Name","xin chao"));
        int Gender = (sharepr.getInt("spinnerSelection",0));
        int iCanNang = (sharepr.getInt("Weight", 0));
        int iChieuCao = (sharepr.getInt("Height",0));
        float fBMI = (sharepr.getFloat("BMI",0));
        double dBMI = Math.round(fBMI*100.0)/100.0;
        String sCanNang = "Chiều Cao:"+ String.valueOf(iChieuCao)+" cm";
        String sChieuCao = "Cân Nặng:"+String.valueOf(iCanNang)+" kg";
        String sBMI = "Chỉ số BMI:" + String.valueOf(dBMI);
        long milionseconds = (sharepr.getLong("yourmilliseconds",0));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date resultdate = new Date(milionseconds);
        String sResultdate = sdf.format(resultdate);
        String sMilionseconds = String.valueOf(milionseconds);



        tvHoTen.setText(HoTen);
        //tvCanNang.setText(sCanNang);
        //tvChieuCao.setText(sChieuCao);
        button_height.setText(sChieuCao);
        button_weight.setText(sCanNang);
        button_bmi.setText(sBMI);
        //button_date.setText(sDate);
        button_date.setText("NGÀY BẮT ĐẦU:"+sResultdate);



        if(Gender == 0) {
            ImageView avatar = (ImageView)view.findViewById(R.id.imageViewAvatar);
            avatar.setImageResource(R.mipmap.male_gym);
        }
        if(Gender == 1) {
            ImageView avatar = (ImageView)view.findViewById(R.id.imageViewAvatar);
            avatar.setImageResource(R.mipmap.female_gym);
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

        if(fBMI<18.5){
            if(fBMI<17) {
                imageview_body.setImageResource(R.drawable.thieuan);
                button_danhgiabody.setText("!!!Thiếu ăn 'SOS'!!!");
            }
            else{
                imageview_body.setImageResource(R.drawable.hoiom);
                button_danhgiabody.setText("Hơi ốm :((");
            }
        }
        else if(fBMI>=18.5 && fBMI<=24.9)
        {
            imageview_body.setImageResource(R.drawable.chuan);
            button_danhgiabody.setText("Chuẩn rồi =)))");
        }
        else if(fBMI>=25 && fBMI<=30){
            imageview_body.setImageResource(R.drawable.hoimap);
            button_danhgiabody.setText("Hơi mập :(");
        }
        else{
            imageview_body.setImageResource(R.drawable.beoqua);
            button_danhgiabody.setText("Béo quá rồi *_*!");
        }
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
