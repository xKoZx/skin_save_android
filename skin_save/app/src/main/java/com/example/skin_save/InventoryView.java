package com.example.skin_save;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InventoryView extends Fragment {
    private LinearLayout linearLayout;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inventory_view, container, false);

        linearLayout = rootView.findViewById(R.id.linearLayout);

        // Initialize Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("newProducts");

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        attachDatabaseListener();
    }

    @Override
    public void onStop() {
        super.onStop();
        detachDatabaseListener();
    }

    private void attachDatabaseListener() {
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                NewProduct product = dataSnapshot.getValue(NewProduct.class);
                displayProduct(product);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                // Handle any changes to the child data if needed
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                // Handle any removal of child data if needed
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                // Handle any movement of child data if needed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that occur while trying to read the database
            }
        };

        databaseReference.addChildEventListener(childEventListener);
    }

    private void detachDatabaseListener() {
        if (databaseReference != null && childEventListener != null) {
            databaseReference.removeEventListener(childEventListener);
        }
    }

    private void displayProduct(NewProduct product) {
        // Create ImageView
        ImageView imageView = new ImageView(requireContext());

        // Load the image from the URL using Glide
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL); // Caches both the original and resized image
        Glide.with(requireContext())
                .load(product.getImageUrl())
                .apply(requestOptions)
                .into(imageView);

        linearLayout.addView(imageView);

        // Create TextView for Name
        TextView nameTextView = new TextView(requireContext());
        nameTextView.setText(product.getName());
        linearLayout.addView(nameTextView);

        // Create TextView for Price
        TextView priceTextView = new TextView(requireContext());
        priceTextView.setText("Price: " + product.getPrice());
        linearLayout.addView(priceTextView);

        // Create TextView for Quantity
        TextView quantityTextView = new TextView(requireContext());
        quantityTextView.setText("Quantity: " + product.getQuantity());
        linearLayout.addView(quantityTextView);
    }
}
