package com.example.newsapk.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.example.newsapk.MainActivity;
import com.example.newsapk.R;
import com.google.android.material.snackbar.Snackbar;

public class Settings extends Fragment {
    private Spinner spinner1, spinner2;
    private Button btnSubmit;
    public Settings() {
        // Required empty public constructor
    }
    public SharedPreferences mPreferences;
    public String sharedPrefFile =
            "com.example.android.newsapkprefs";

    String Countryis="in";
    int selectedPosition=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_settings, container, false);
    //    addItemsOnSpinner2();
       final Spinner spinner1;
        mPreferences = this.getActivity().getSharedPreferences("sharedPrefFile", Context.MODE_PRIVATE);
        spinner1=view.findViewById(R.id.spinner1);
        spinner1.setSelection(mPreferences.getInt("spinner",0));
        Log.v("Spinner selected ","="+selectedPosition);
        spinner1.post(new Runnable() {
            @Override
            public void run() {
                spinner1.setSelection(mPreferences.getInt("spinner",0));
            }
        });
        Button button=view.findViewById(R.id.btnSubmit);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String selectedItem=spinner1.getSelectedItem().toString();
               Log.v("Selected item is"+selectedItem,"Selected item is"+selectedItem);

               if(selectedItem.equals("Canada")){
                   Countryis="ca";
               }
              else if(selectedItem.equals("United States")){
                   Countryis="us";
               }
              else if(selectedItem.equals("India")){
                   Countryis="in";
               }
              else if(selectedItem.equals("Japan")){
                   Countryis="jp";
               }
               onPause();
           Snackbar snackbar=Snackbar.make(getActivity().findViewById(R.id.settingslayout),"Selected Country is "+selectedItem,Snackbar.LENGTH_LONG);
             snackbar.show();
               selectedPosition=spinner1.getSelectedItemPosition();
           }

       });

        return view;
    }
    @Override
    public void onPause() {
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.clear();
        preferencesEditor.putString("COUNTRY_KEY", Countryis);
        preferencesEditor.putInt("spinner",selectedPosition);
        preferencesEditor.apply();
        super.onPause();
    }
}