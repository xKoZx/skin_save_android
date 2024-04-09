package com.example.skin_save;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class vieworders extends Fragment {
    private TextView textViewMessage;
    private TextView textViewOrderDetails;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vieworders, container, false);

        textViewMessage = view.findViewById(R.id.textViewMessage);
        textViewOrderDetails = view.findViewById(R.id.textViewOrderDetails);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("COD");

        Query query = databaseReference.orderByChild("orderID");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StringBuilder orderDetails = new StringBuilder();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String orderID = snapshot.child("orderID").getValue(String.class);
                    String name = snapshot.child("name").getValue(String.class);
                    String phoneNumber = snapshot.child("phoneNumber").getValue(String.class);
                    String pinCode = snapshot.child("pinCode").getValue(String.class);
                    String addressLine1 = snapshot.child("addressLine1").getValue(String.class);
                    String addressLine2 = snapshot.child("addressLine2").getValue(String.class);
                    String productName = snapshot.child("productName").getValue(String.class);
                    Double totalAmount = snapshot.child("totalAmount").getValue(Double.class);

                    orderDetails.append("Order ID: ").append(orderID).append("\n");
                    orderDetails.append("Name: ").append(name).append("\n");
                    orderDetails.append("Phone Number: ").append(phoneNumber).append("\n");
                    orderDetails.append("Pin Code: ").append(pinCode).append("\n");
                    orderDetails.append("Address Line 1: ").append(addressLine1).append("\n");
                    orderDetails.append("Address Line 2: ").append(addressLine2).append("\n");
                    orderDetails.append("Product Name: ").append(productName).append("\n");
                    orderDetails.append("Total Price: ").append(totalAmount).append("\n\n");
                }

                if (orderDetails.length() > 0) {
                    textViewMessage.setText("All Orders");
                    textViewOrderDetails.setText(orderDetails.toString());
                } else {
                    textViewMessage.setText("No Orders Found");
                    textViewOrderDetails.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the database query cancellation or error scenario
            }
        });

        // Wrap the TextView inside a ScrollView
        ScrollView scrollView = view.findViewById(R.id.scrollView);
        scrollView.setVerticalScrollBarEnabled(true);

        return view;
    }
}
