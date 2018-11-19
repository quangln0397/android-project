package com.example.renluyensuckhoe;


import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity implements Exercises.OnFragmentInteractionListener,Profile.OnFragmentInteractionListener,Nutrition.OnFragmentInteractionListener,More.OnFragmentInteractionListener,Statistics.OnFragmentInteractionListener {

    private BottomNavigationView bottomNavigation;

    private Exercises exercises_fragment;
    private Profile profile_fragment;
    private Nutrition nutrition_fragment;
    private Statistics chart_fragment;
    private More more_fragment;

    FragmentManager mn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fm = getSupportFragmentManager();
       // final FragmentTransaction transaction = fm.beginTransaction();
        profile_fragment = new Profile();
        exercises_fragment = new Exercises();
        nutrition_fragment = new Nutrition();
        chart_fragment = new Statistics();
        more_fragment = new More();
        //transaction.replace(R.id.container, exercises_fragment);//với container là id của framelayout trong xml
       // transaction.commit();
        setFragment(profile_fragment);
        bottomNavigation = (BottomNavigationView) findViewById(R.id.navigationView);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem Item) {
                switch(Item.getItemId()){
                    case R.id.navigation_profile:
                     setFragment(profile_fragment);
                        return true;
                    case R.id.navigation_exercises:
                        setFragment(exercises_fragment);
                        return true;
                    case R.id.navigation_nutritition:
                        setFragment(nutrition_fragment);
                        return true;
                    case R.id.navigation_chart:
                        setFragment(chart_fragment);
                        return true;
                    case R.id.navigation_more:
                        setFragment(more_fragment);
                        return true;
                    default: return false;
                }
            }//khởi tạo một constructor(NavigationView và gọi đến phương thức constructor đó


        });

    }
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
