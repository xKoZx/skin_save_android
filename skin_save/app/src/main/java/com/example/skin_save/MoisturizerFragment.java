package com.example.skin_save;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MoisturizerFragment extends Fragment {

        private CardView m1,m2,m3,c4;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_moisturizer, container, false);


            m1 = view.findViewById(R.id.cardView1);
            m2 = view.findViewById(R.id.cardView2);
            m3 = view.findViewById(R.id.cardView3);
            c4 = view.findViewById(R.id.cardView4);

            m1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Create an instance of SerumPageFragment
                     moistprod1 moistprod1= new moistprod1();
                    //  serumPageFragment.setOnProductAddedListener(productAddedListener);

                    // Get the FragmentManager
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                    // Begin the transaction
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    // Replace the current fragment with SerumPageFragment
                    fragmentTransaction.replace(R.id.container,moistprod1);

                    // Optional: Add the transaction to the back stack
                    fragmentTransaction.addToBackStack(null);

                    // Commit the transaction
                    fragmentTransaction.commit();
                }
                //    serumPageFragment.setOnProductAddedListener(productAddedListener);
            });
            m2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Create an instance of SerumPageFragment
                    moistprod2 moistprod2 = new moistprod2();
                    //  serumPageFragment.setOnProductAddedListener(productAddedListener);

                    // Get the FragmentManager
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                    // Begin the transaction
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    // Replace the current fragment with SerumPageFragment
                    fragmentTransaction.replace(R.id.container, moistprod2);

                    // Optional: Add the transaction to the back stack
                    fragmentTransaction.addToBackStack(null);

                    // Commit the transaction
                    fragmentTransaction.commit();
                }
                //    serumPageFragment.setOnProductAddedListener(productAddedListener);
            });
            m3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Create an instance of SerumPageFragment
                    moistprod3 moistprod3 = new moistprod3();
                    //  serumPageFragment.setOnProductAddedListener(productAddedListener);

                    // Get the FragmentManager
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                    // Begin the transaction
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    // Replace the current fragment with SerumPageFragment
                    fragmentTransaction.replace(R.id.container, moistprod3);

                    // Optional: Add the transaction to the back stack
                    fragmentTransaction.addToBackStack(null);

                    // Commit the transaction
                    fragmentTransaction.commit();
                }
                //    serumPageFragment.setOnProductAddedListener(productAddedListener);
            });
            c4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Create an instance of SerumPageFragment
                    np4 np4 = new np4();

                    // Get the FragmentManager
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                    // Begin the transaction
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    // Replace the current fragment with SerumPageFragment
                    fragmentTransaction.replace(R.id.container, np4);

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