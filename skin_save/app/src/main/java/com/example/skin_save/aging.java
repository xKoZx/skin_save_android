package com.example.skin_save;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class aging extends Fragment {

        private CardView e1,ss1;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_aging, container, false);


            e1= view.findViewById(R.id.cardView1);
            ss1 = view.findViewById(R.id.cardView2);


            e1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Create an instance of SerumPageFragment
                    eyeprod1 eyeprod1 = new eyeprod1();
                    //  serumPageFragment.setOnProductAddedListener(productAddedListener);

                    // Get the FragmentManager
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                    // Begin the transaction
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    // Replace the current fragment with SerumPageFragment
                    fragmentTransaction.replace(R.id.container,eyeprod1);

                    // Optional: Add the transaction to the back stack
                    fragmentTransaction.addToBackStack(null);

                    // Commit the transaction
                    fragmentTransaction.commit();
                }
                //    serumPageFragment.setOnProductAddedListener(productAddedListener);
            });
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
                    fragmentTransaction.replace(R.id.container, ssprod1);

                    // Optional: Add the transaction to the back stack
                    fragmentTransaction.addToBackStack(null);

                    // Commit the transaction
                    fragmentTransaction.commit();
                }
                //    serumPageFragment.setOnProductAddedListener(productAddedListener);
            });

            // Inflate the layout for this fragment
            return view;
        }

}