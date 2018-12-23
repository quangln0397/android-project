package com.example.renluyensuckhoe;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.lang.reflect.Array;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Statistics.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Statistics#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Statistics extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button btn_kgtoday;
    EditText CanNangHomNay;

    int dem=0;
    private OnFragmentInteractionListener mListener;

    public Statistics() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Statistics.
     */
    // TODO: Rename and change types and number of parameters
    public static Statistics newInstance(String param1, String param2) {
        Statistics fragment = new Statistics();
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
        final View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        LineChart mpLineChart = view.findViewById(R.id.line_chart);

        LineDataSet lineDataSet1 = new LineDataSet(dataValues1(),"Cân Nặng (Kg)");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);
        lineDataSet1.setLineWidth(2);
        //lineDataSet1.setColor(Color.RED);

        LineData data = new LineData(dataSets);
        mpLineChart.setData(data);
        mpLineChart.invalidate();
        mpLineChart.setDrawGridBackground(true);
        mpLineChart.setDrawBorders(true);
        mpLineChart.setBorderColor(Color.RED);
        mpLineChart.setBorderWidth(3);

        Description description = new Description();
        description.setText("ngày/kg");
        description.setTextSize(16);
        description.setTextColor(Color.BLUE);
        mpLineChart.setDescription(description);

        final SharedPreferences sharedpreferences = this.getActivity().getSharedPreferences("thongtin",0);
        final SharedPreferences.Editor editor = sharedpreferences.edit();
        CanNangHomNay = (EditText)view.findViewById(R.id.editTextCanNangToday);
        btn_kgtoday = (Button)view.findViewById(R.id.btn_cannanghomnay);


        btn_kgtoday.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int dem = (sharedpreferences.getInt("dem",0));
                dem++;
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String sdate = formatter.format(date);// Dùng ngày tháng hiện tại, để có key tương ứng ngày hôm đó

                String sCanNangHomNay = CanNangHomNay.getText().toString();
                float fCanNangHomNay = Integer.parseInt(sCanNangHomNay);
                String CanNangHomNay_key = "CanNangHomNay" + sdate;

                long thoigianhientai = System.currentTimeMillis();

                editor.putLong("thoigianhientai",thoigianhientai);
                editor.putFloat("CanNangHomNay_key", fCanNangHomNay);
                editor.putString("NgayHomNay", sdate);
                editor.putInt("dem", dem);
                editor.commit();

            }
        });

        return view;
        }




    private ArrayList<Entry> dataValues1(){
        SharedPreferences sharepr = this.getActivity().getSharedPreferences("thongtin",0);
        final SharedPreferences.Editor editor= sharepr.edit();
        //float[] fCanNangHomNay = new float[100];
        int Dem = (sharepr.getInt("dem",88));
        float[] fCanNangHomNay = new float[100];
        int x=0;
        for(int i=1;i<=Dem;i++)
        {
            x++;
            if(Dem == x )
            {
                fCanNangHomNay[x] = (sharepr.getFloat("CanNangHomNay_key",0));
                editor.putFloat("CanNang"+x,fCanNangHomNay[x]);
                editor.commit();
            }
        }
        long lThoiGianHienTai = (sharepr.getLong("thoigianhientai",0));
        long lThoigianBatDau = (sharepr.getLong("thoigianbatdau",0));
        long lThoiGianHienTai1Ngay = lThoiGianHienTai + 86400000;
        long lkhoangcach = lThoiGianHienTai - lThoigianBatDau;
        long lkhoangcach2 = lThoiGianHienTai1Ngay - lThoigianBatDau;
        int days = (int) (lkhoangcach / (1000*60*60*24));
        int days2 = (int) (lkhoangcach2 / (1000*60*60*24));
        System.out.println("Thời gian bắt đầu là"+lThoigianBatDau);
        System.out.println("Thời gian hiện tại là"+lThoiGianHienTai);
        System.out.println("Thời gian khoảng cách là:"+lkhoangcach);
        System.out.println("Thời gian khoảng cách 2 là:"+lkhoangcach2);
       // SimpleDateFormat formatter = new SimpleDateFormat("dd");
       // String sdate = formatter.format(lkhoangcach);
        System.out.println("So ngay la:"+days);
        System.out.println("So ngay la:"+days2);

        float CanNang[] = new float[100];
        for(int i=1;i<=Dem;i++) {
            CanNang[i] = (sharepr.getFloat("CanNang" + i, 0));
            //CanNang[1] = (sharepr.getFloat("CanNang2", 0));
            //CanNang[2] = (sharepr.getFloat("CanNang3", 0));
        }

        //float CanNang2 = (sharepr).getFloat("CanNang2",0);
        //float CanNang3 = (sharepr).getFloat("CanNang3",0);
        //float CanNang4 = (sharepr).getFloat("CanNang4",0);
        //float CanNang5 = (sharepr).getFloat("CanNang5",0);

        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        //for(int i=0;i<fCanNangHomNay.length;i++) {
            //dataVals.add(new Entry(0, fCanNangHomNay[0]));
        /*System.out.println("Can Nang 1:"+fCanNangHomNay[1]);
        System.out.println("Can Nang 2:"+fCanNangHomNay[2]);
        System.out.println("Can Nang 3:"+fCanNangHomNay[3]);
        System.out.println("Can Nang 4:"+fCanNangHomNay[4]);*/
        System.out.println("Can Nang 1:"+CanNang[1]);
        System.out.println("Can Nang 2:"+CanNang[2]);
        System.out.println("Can Nang 3:"+CanNang[3]);
        System.out.println("Can Nang 3:"+CanNang[4]);
        System.out.println("Can Nang 3:"+CanNang[5]);
        //System.out.println("Can Nang 3:"+CanNang3);
        //System.out.println("Can Nang 4:"+CanNang4);
       // System.out.println("Can Nang 5:"+CanNang5);


        for(int i=1;i<=Dem;i++) {
            dataVals.add(new Entry(i, CanNang[i]));
           // dataVals.add(new Entry(2, CanNang[2]));
           // dataVals.add(new Entry(3, CanNang[3]));
           // dataVals.add(new Entry(4, CanNang[4]));
           // dataVals.add(new Entry(5, CanNang[5]));
           // dataVals.add(new Entry(6, CanNang[6]));
        }

            /*dataVals.add(new Entry(1, CanNang1));
            dataVals.add(new Entry(2, CanNang2));
            dataVals.add(new Entry(3, CanNang3));
            dataVals.add(new Entry(4, CanNang4));
            dataVals.add(new Entry(4, CanNang5));*/








        //dataVals.add(new Entry(5,24));
            //dataVals.add(new Entry(2,2));
            //dataVals.add(new Entry(3,10));
        //}
        return dataVals;

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
