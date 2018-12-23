package com.example.renluyensuckhoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import static android.R.layout.simple_dropdown_item_1line;

public class Main2Activity extends AppCompatActivity {

    private EditText HoTen;
    private EditText ChieuCao;
    private EditText CanNang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        String[] listgioitinh  = getResources().getStringArray(R.array.gioitinh);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, simple_dropdown_item_1line,listgioitinh);
        final Spinner spgioitinh = (Spinner)findViewById(R.id.spinnergt);
        spgioitinh.setAdapter(adapter1);

        String[] listvandong  = getResources().getStringArray(R.array.vandong);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, simple_dropdown_item_1line,listvandong);
        Spinner spvandong = (Spinner)findViewById(R.id.spinnervd);
        spvandong.setAdapter(adapter2);

        Button btnNhap = (Button)findViewById(R.id.buttonNhap);

        // Luu thong tin nguoi dung
        SharedPreferences sharedpreferences = getSharedPreferences("thongtin", 0);
        final SharedPreferences.Editor editor = sharedpreferences.edit();
        HoTen = (EditText)findViewById(R.id.editTextHoTen);
        ChieuCao = (EditText)findViewById(R.id.editTextChieuCao);
        CanNang = (EditText)findViewById(R.id.editTextCanNang);



        btnNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("Name",HoTen.getText().toString());
                editor.putBoolean("isFilledOut", true);

                String sChieuCao = ChieuCao.getText().toString();
                String sCanNang = CanNang.getText().toString();
                int iChieuCao = Integer.parseInt(sChieuCao);
                int iCanNang = Integer.parseInt(sCanNang);
                float fChieuCao = (float)iChieuCao;
                float fCanNang = (float)iCanNang;
                float fBMI = (fCanNang/((fChieuCao/100)*(fChieuCao/100)));
                //String sGender = (String) spgioitinh.getSelectedItem().toString();

                int selectedPosition = spgioitinh.getSelectedItemPosition();
                editor.putInt("spinnerSelection", selectedPosition);

                editor.putInt("Height",iChieuCao);
                editor.putInt("Weight",iCanNang);
                editor.putFloat("BMI", fBMI);
                editor.commit();
                  Intent i  = new Intent(Main2Activity.this, MainActivity.class);
                  startActivity(i);
            }
        });
        if(sharedpreferences.getBoolean("isFilledOut",true)){
            Intent i  = new Intent(Main2Activity.this, MainActivity.class);
            startActivity(i);
        }
    }
}
