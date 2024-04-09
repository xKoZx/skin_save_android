package com.example.skin_save;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SunscreenFragment extends Fragment {
    private CardView ss1,ss2,ss3,c3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sunscreen, container, false);


        ss1 = view.findViewById(R.id.cardView1);
        ss2 = view.findViewById(R.id.cardView2);
        ss3 = view.findViewById(R.id.cardView3);
        c3 = view.findViewById(R.id.cardView4);

        ss1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of SerumPageFragment
                ssprod1 ssprod1 = new ssprod1();
                //  serumPageFragment.setOnProductAddedListener(productAddedListener);

                // Get the FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Begin the transaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the current fragment with SerumPageFragment
                fragmentTransaction.replace(R.id.container,ssprod1);

                // Optional: Add the transaction to the back stack
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();
            }
            //    serumPageFragment.setOnProductAddedListener(productAddedListener);
        });
        ss2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of SerumPageFragment
                ssprod2 ssprod2 = new ssprod2();
                //  serumPageFragment.setOnProductAddedListener(productAddedListener);

                // Get the FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Begin the transaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the current fragment with SerumPageFragment
                fragmentTransaction.replace(R.id.container, ssprod2);

                // Optional: Add the transaction to the back stack
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();
            }
            //    serumPageFragment.setOnProductAddedListener(productAddedListener);
        });
        ss3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of SerumPageFragment
                ssprod3 ssprod3 = new ssprod3();
                //  serumPageFragment.setOnProductAddedListener(productAddedListener);

                // Get the FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Begin the transaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the current fragment with SerumPageFragment
                fragmentTransaction.replace(R.id.container, ssprod3);

                // Optional: Add the transaction to the back stack
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();
            }
            //    serumPageFragment.setOnProductAddedListener(productAddedListener);
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of SerumPageFragment
                np3 np3 = new np3();

                // Get the FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Begin the transaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the current fragment with SerumPageFragment
                fragmentTransaction.replace(R.id.container, np3);

                // Optional: Add the transaction to the back stack
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}