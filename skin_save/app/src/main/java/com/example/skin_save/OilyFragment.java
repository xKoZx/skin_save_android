package com.example.skin_save;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class OilyFragment extends Fragment {

    private CardView s4, cl2, ss2, m3, b2, e2, c7;
    private ImageView imageViewNew;
    private TextView pName, pPrice;

    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_oily, container, false);

        s4 = view.findViewById(R.id.cardView1);
        cl2 = view.findViewById(R.id.cardView2);
        ss2 = view.findViewById(R.id.cardView3);
        m3 = view.findViewById(R.id.cardView4);
        b2 = view.findViewById(R.id.cardView5);
        e2 = view.findViewById(R.id.cardView6);
        c7 = view.findViewById(R.id.cardView7);

        imageViewNew = view.findViewById(R.id.imageViewNew);
        pName = view.findViewById(R.id.pName);
        pPrice = view.findViewById(R.id.pPrice);

        // Initialize Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("newProducts");

        c7.setVisibility(View.GONE); // Hide the cardView7 initially

        s4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of SerumPageFragment
                serumprod4 serumprod4 = new serumprod4();
                //  serumPageFragment.setOnProductAddedListener(productAddedListener);

                // Get the FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Begin the transaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the current fragment with SerumPageFragment
                fragmentTransaction.replace(R.id.container, serumprod4);

                // Optional: Add the transaction to the back stack
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();
            }
            //    serumPageFragment.setOnProductAddedListener(productAddedListener);
        });
        cl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of SerumPageFragment
                cleanserprod2 cleanserprod2 = new cleanserprod2();

                // Get the FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Begin the transaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the current fragment with SerumPageFragment
                fragmentTransaction.replace(R.id.container, cleanserprod2);

                // Optional: Add the transaction to the back stack
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();
            }
        });
        ss2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of SerumPageFragment
                ssprod2 ssprod2 = new ssprod2();

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
        });
        m3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of SerumPageFragment
                moistprod3 moistprod3 = new moistprod3();

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
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of SerumPageFragment
                bodyprod2 bodyprod2 = new bodyprod2();

                // Get the FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Begin the transaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the current fragment with SerumPageFragment
                fragmentTransaction.replace(R.id.container, bodyprod2);

                // Optional: Add the transaction to the back stack
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();
            }
        });
        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of SerumPageFragment
                eyeprod2 eyeprod2 = new eyeprod2();

                // Get the FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Begin the transaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the current fragment with SerumPageFragment
                fragmentTransaction.replace(R.id.container, eyeprod2);

                // Optional: Add the transaction to the back stack
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();
            }
        });
        c7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of SerumPageFragment
                oilynewprod newp = new oilynewprod();

                // Get the FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Begin the transaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the current fragment with SerumPageFragment
                fragmentTransaction.replace(R.id.container, newp);

                // Optional: Add the transaction to the back stack
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();
            }
        });

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        retrieveLatestProduct();
    }

    private void retrieveLatestProduct() {
        // Query the database to retrieve the latest product with category "Normal skin"
        Query query = databaseReference.orderByChild("category").equalTo("oily skin").limitToLast(1);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    NewProduct product = snapshot.getValue(NewProduct.class);
                    if (product != null) {
                        displayProduct(product);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
            }
        });
    }

    private void displayProduct(NewProduct product) {
        // Display the image, name, and price in the corresponding views
        Picasso.get().load(product.getImageUrl()).into(imageViewNew);
        pName.setText(product.getName());
        pPrice.setText(product.getPrice());

        c7.setVisibility(View.VISIBLE); // Show the cardView7 after retrieving the product
    }

}
