package com.example.proyecto.NavigationController;


import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.proyecto.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    AppCompatTextView fragHomeTest;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("gla", "HOME: ");
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        fragHomeTest = view.findViewById(R.id.fragHomeTest);
        fragHomeTest.setText("Funciona");
        //return inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

}
