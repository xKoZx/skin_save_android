package com.example.skin_save;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class NormalFragment extends Fragment {

    private CardView c1, c2, c3, c4, c5, c6, c7;
    private ImageView imageViewNew;
    private TextView pName, pPrice;

    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_normal, container, false);

        c1 = view.findViewById(R.id.cardView1);
        c2 = view.findViewById(R.id.cardView2);
        c3 = view.findViewById(R.id.cardView3);
        c4 = view.findViewById(R.id.cardView4);
        c5 = view.findViewById(R.id.cardView5);
        c6 = view.findViewById(R.id.cardView6);
        c7 = view.findViewById(R.id.cardView7);

        imageViewNew = view.findViewById(R.id.imageViewNew);
        pName = view.findViewById(R.id.pName);
        pPrice = view.findViewById(R.id.pPrice);

        // Initialize Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("newProducts");

        c7.setVisibility(View.GONE); // Hide the cardView7 initially

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of SerumPageFragment
                serum serumPageFragment = new serum();
                //  serumPageFragment.setOnProductAddedListener(productAddedListener);

                // Get the FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Begin the transaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the current fragment with SerumPageFragment
                fragmentTransaction.replace(R.id.container, serumPageFragment);

                // Optional: Add the transaction to the back stack
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();
            }
            //    serumPageFragment.setOnProductAddedListener(productAddedListener);
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of SerumPageFragment
                np2 np2 = new np2();

                // Get the FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Begin the transaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the current fragment with SerumPageFragment
                fragmentTransaction.replace(R.id.container, np2);

                // Optional: Add the transaction to the back stack
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();
            }
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
        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of SerumPageFragment
                np5 np5 = new np5();

                // Get the FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Begin the transaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the current fragment with SerumPageFragment
                fragmentTransaction.replace(R.id.container, np5);

                // Optional: Add the transaction to the back stack
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();
            }
        });
        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of SerumPageFragment
                np6 np6 = new np6();

                // Get the FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Begin the transaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the current fragment with SerumPageFragment
                fragmentTransaction.replace(R.id.container, np6);

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
                newprod newp = new newprod();

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
        Query query = databaseReference.orderByChild("category").equalTo("Normal skin").limitToLast(1);
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
