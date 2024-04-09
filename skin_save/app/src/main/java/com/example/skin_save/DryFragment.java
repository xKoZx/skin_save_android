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

public class DryFragment extends Fragment {

    private CardView s2, cl1, ss3, m1, b1, e1, c7;
    private ImageView imageViewNew;
    private TextView pName, pPrice;

    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dry, container, false);

        s2 = view.findViewById(R.id.cardView1);
        cl1 = view.findViewById(R.id.cardView2);
        ss3 = view.findViewById(R.id.cardView3);
        m1 = view.findViewById(R.id.cardView4);
        b1 = view.findViewById(R.id.cardView5);
        e1 = view.findViewById(R.id.cardView6);
        c7 = view.findViewById(R.id.cardView7);

        imageViewNew = view.findViewById(R.id.imageViewNew);
        pName = view.findViewById(R.id.pName);
        pPrice = view.findViewById(R.id.pPrice);

        // Initialize Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("newProducts");

        c7.setVisibility(View.GONE); // Hide the cardView7 initially

        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of SerumPageFragment
                serumprod2 serumprod2 = new serumprod2();
                //  serumPageFragment.setOnProductAddedListener(productAddedListener);

                // Get the FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Begin the transaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the current fragment with SerumPageFragment
                fragmentTransaction.replace(R.id.container, serumprod2);

                // Optional: Add the transaction to the back stack
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();
            }
            //    serumPageFragment.setOnProductAddedListener(productAddedListener);
        });
        cl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of SerumPageFragment
                cleanserprod1 cleanserprod1 = new cleanserprod1();

                // Get the FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Begin the transaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the current fragment with SerumPageFragment
                fragmentTransaction.replace(R.id.container, cleanserprod1);

                // Optional: Add the transaction to the back stack
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();
            }
        });
        ss3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of SerumPageFragment
                ssprod3 ssprod3 = new ssprod3();

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
        });
        m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of SerumPageFragment
                moistprod1 moistprod1 = new moistprod1();

                // Get the FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Begin the transaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the current fragment with SerumPageFragment
                fragmentTransaction.replace(R.id.container, moistprod1);

                // Optional: Add the transaction to the back stack
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of SerumPageFragment
                bodyprod1 bodyprod1 = new bodyprod1();

                // Get the FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Begin the transaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the current fragment with SerumPageFragment
                fragmentTransaction.replace(R.id.container, bodyprod1);

                // Optional: Add the transaction to the back stack
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();
            }
        });
        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of SerumPageFragment
                eyeprod1 eyeprod1 = new eyeprod1();

                // Get the FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Begin the transaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the current fragment with SerumPageFragment
                fragmentTransaction.replace(R.id.container, eyeprod1);

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
                drynewprod newp = new drynewprod();

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
        Query query = databaseReference.orderByChild("category").equalTo("Dry skin").limitToLast(1);
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
