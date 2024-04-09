
        package com.example.skin_save;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class cod extends Fragment {
    private TextView textViewMessage;
    private TextView textViewOrderDetails;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cod, container, false);

        textViewMessage = view.findViewById(R.id.textViewMessage);
        textViewOrderDetails = view.findViewById(R.id.textViewOrderDetails);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            String loggedInUserEmail = user.getEmail();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("COD");

            Query query = databaseReference.orderByChild("userEmail").equalTo(loggedInUserEmail).limitToLast(1);
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
                        String Status = snapshot.child("payment").getValue(String.class);

                        orderDetails.append("Order ID: ").append(orderID).append("\n");
                        orderDetails.append("Name: ").append(name).append("\n");
                        orderDetails.append("Phone Number: ").append(phoneNumber).append("\n");
                        orderDetails.append("Pin Code: ").append(pinCode).append("\n");
                        orderDetails.append("Address Line 1: ").append(addressLine1).append("\n");
                        orderDetails.append("Address Line 2: ").append(addressLine2).append("\n");
                        orderDetails.append("Product Name: ").append(productName).append("\n");
                        orderDetails.append("Total Price: ").append(totalAmount).append("\n\n");
                        orderDetails.append("Payment: ").append(Status).append("\n\n");
                    }

                    if (orderDetails.length() > 0) {
                        textViewMessage.setText("Order Placed!");
                        textViewOrderDetails.setText(orderDetails.toString());

                        // Delete all data from the "checkout" table
                        deleteUserData(loggedInUserEmail);
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
        }

        return view;
    }

    private void deleteUserData(String userEmail) {
        DatabaseReference checkoutReference = FirebaseDatabase.getInstance().getReference("checkout");
        DatabaseReference cartReference = FirebaseDatabase.getInstance().getReference("cart");

        Query checkoutQuery = checkoutReference.orderByChild("userEmail").equalTo(userEmail);
        Query cartQuery = cartReference.orderByChild("email").equalTo(userEmail);

        checkoutQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the database query cancellation or error scenario
            }
        });

        cartQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the database query cancellation or error scenario
            }
        });
    }
}