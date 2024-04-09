package com.example.skin_save;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class paymentprice extends Fragment {

    private TextView textViewPaidOrderDetails;
    private TextView textViewNotPaidOrderDetails;
    private TextView textViewTotalRevenue;
    private TextView textViewYetToPay;
    private DatabaseReference databaseReference;

    private double totalRevenue = 0.0;
    private double pendingPayment = 0.0;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paymentprice, container, false);

        textViewPaidOrderDetails = view.findViewById(R.id.textViewPaidOrderDetails);
        textViewNotPaidOrderDetails = view.findViewById(R.id.textViewNotPaidOrderDetails);
        textViewTotalRevenue = view.findViewById(R.id.textViewTotalRevenue);
        textViewYetToPay = view.findViewById(R.id.textViewYetToPay);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("COD");

        Query paidQuery = databaseReference.orderByChild("payment").equalTo("paid");
        paidQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StringBuilder paidOrderDetails = new StringBuilder();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String orderID = snapshot.child("orderID").getValue(String.class);
                    String email = snapshot.child("userEmail").getValue(String.class);
                    String payment = snapshot.child("payment").getValue(String.class);
                    Double totalPrice = snapshot.child("totalAmount").getValue(Double.class);

                    if (orderID != null && email != null && totalPrice != null) {
                        paidOrderDetails.append("Order ID: ").append(orderID).append("\n");
                        paidOrderDetails.append("Email: ").append(email).append("\n");
                        paidOrderDetails.append("Payment: ").append(payment).append("\n");
                        paidOrderDetails.append("Total Price: ₹").append(totalPrice).append("\n\n");

                        totalRevenue += totalPrice;
                    }
                }

                if (paidOrderDetails.length() > 0) {
                    textViewPaidOrderDetails.setText(paidOrderDetails.toString());
                } else {
                    textViewPaidOrderDetails.setText("No paid orders found.");
                }

                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                String formattedTotalRevenue = decimalFormat.format(totalRevenue);
                textViewTotalRevenue.setText("Total Revenue: ₹" + formattedTotalRevenue);

                // Calculate pending payment
                Query notPaidQuery = databaseReference.orderByChild("payment").equalTo("not paid");
                notPaidQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        StringBuilder notPaidOrderDetails = new StringBuilder();
                        double pending = 0.0;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String orderID = snapshot.child("orderID").getValue(String.class);
                            String email = snapshot.child("userEmail").getValue(String.class);
                            String payment = snapshot.child("payment").getValue(String.class);
                            Double totalPrice = snapshot.child("totalAmount").getValue(Double.class);

                            if (orderID != null && email != null && totalPrice != null) {
                                notPaidOrderDetails.append("Order ID: ").append(orderID).append("\n");
                                notPaidOrderDetails.append("Email: ").append(email).append("\n");
                                notPaidOrderDetails.append("Payment: ").append(payment).append("\n");
                                notPaidOrderDetails.append("Total Price: ₹").append(totalPrice).append("\n\n");

                                pending += totalPrice;
                            }
                        }

                        if (notPaidOrderDetails.length() > 0) {
                            textViewNotPaidOrderDetails.setText(notPaidOrderDetails.toString());
                        } else {
                            textViewNotPaidOrderDetails.setText("No pending orders found.");
                        }

                        pendingPayment = pending;
                        String formattedPendingPayment = decimalFormat.format(pendingPayment);
                        textViewYetToPay.setText("Yet to Pay(COD): ₹" + formattedPendingPayment);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the database query cancellation or error scenario
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the database query cancellation or error scenario
            }
        });

        return view;
    }
}
