package com.example.skin_save;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.skin_save.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class checkout extends Fragment {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private TextView textViewTotalAmount;
    private EditText editTextName, editTextPhoneNumber, editTextPinCode, editTextAddressLine1, editTextAddressLine2;
    private TextView productName; // Updated to TextView to display product name

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize Firebase Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("checkout");

        // Get the logged-in user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            // Get the logged-in user's email
            String loggedInUserEmail = user.getEmail();


            // Query the database to get the total amount from the checkout table for the logged-in user's email
            Query query = databaseReference.orderByChild("userEmail").equalTo(loggedInUserEmail);
            query.addValueEventListener(new ValueEventListener() {
                @SuppressLint("MissingInflatedId")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    double totalAmount = 0.0;
                    String product = ""; // Variable to store the product name
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Double totalPriceValue = snapshot.child("totalPrice").getValue(Double.class);
                        if (totalPriceValue != null) {
                            double totalPrice = totalPriceValue.doubleValue();
                            product = snapshot.child("productNames").getValue(String.class);
                            totalAmount = totalPrice;
                        }
                    }

                    // Update the value of the textViewTotalAmount label
                    textViewTotalAmount = view.findViewById(R.id.textViewTotalAmount);
                    textViewTotalAmount.setText("₹" + totalAmount);

                    // Update the product name TextView
                    productName = view.findViewById(R.id.textProductName);
                    productName.setText(product);

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference("COD");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle the database query cancellation or error scenario
                }
            });
        }

        // Initialize the views
        textViewTotalAmount = view.findViewById(R.id.textViewTotalAmount);
        editTextName = view.findViewById(R.id.editTextName);
        editTextPhoneNumber = view.findViewById(R.id.editTextPhoneNumber);
        editTextPinCode = view.findViewById(R.id.editTextPinCode);
        editTextAddressLine1 = view.findViewById(R.id.editTextAddressLine1);
        editTextAddressLine2 = view.findViewById(R.id.editTextAddressLine2);
        Spinner spinnerPaymentMode = view.findViewById(R.id.spinnerPaymentMode);

        // Handle the button click event
        Button buttonMakePayment = view.findViewById(R.id.buttonMakePayment);
        buttonMakePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    String paymentMode = spinnerPaymentMode.getSelectedItem().toString();

                    String totalAmountText = textViewTotalAmount.getText().toString().replaceAll("[₹,]", "").trim();
                    double totalAmount = Double.parseDouble(totalAmountText);

                    // Generate a random 4-digit order ID
                    Random random = new Random();
                    int orderID = random.nextInt(9000) + 1000;

                    // Update the database with the values
                    updateDatabase(paymentMode, totalAmount, orderID);
                }
            }
        });

        return view;
    }

    private boolean validateFields() {
        String name = editTextName.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        String pinCode = editTextPinCode.getText().toString().trim();
        String addressLine1 = editTextAddressLine1.getText().toString().trim();
        String addressLine2 = editTextAddressLine2.getText().toString().trim();

        if (name.isEmpty()) {
            editTextName.setError("Enter your name");
            return false;
        }

        if (phoneNumber.isEmpty()) {
            editTextPhoneNumber.setError("Enter your phone number");
            return false;
        }

        if (pinCode.isEmpty()) {
            editTextPinCode.setError("Enter your PIN code");
            return false;
        }

        if (addressLine1.isEmpty()) {
            editTextAddressLine1.setError("Enter address line 1");
            return false;
        }

        if (addressLine2.isEmpty()) {
            editTextAddressLine2.setError("Enter address line 2");
            return false;
        }

        return true;
    }

    private void updateDatabase(String paymentMode, double totalAmount, int orderID) {
        String loggedInUserEmail = firebaseAuth.getCurrentUser().getEmail();
        String name = editTextName.getText().toString();
        String phoneNumber = editTextPhoneNumber.getText().toString();
        String pinCode = editTextPinCode.getText().toString();
        String addressLine1 = editTextAddressLine1.getText().toString();
        String addressLine2 = editTextAddressLine2.getText().toString();

        DatabaseReference codTableRef = databaseReference.push(); // Use push() to generate a unique key for each record

        // Show a success message or perform any other necessary actions
        if (paymentMode.equalsIgnoreCase("COD")) {
            codTableRef.child("userEmail").setValue(loggedInUserEmail);
            codTableRef.child("name").setValue(name);
            codTableRef.child("phoneNumber").setValue(phoneNumber);
            codTableRef.child("pinCode").setValue(pinCode);
            codTableRef.child("addressLine1").setValue(addressLine1);
            codTableRef.child("addressLine2").setValue(addressLine2);
            codTableRef.child("paymentMode").setValue(paymentMode);
            codTableRef.child("totalAmount").setValue(totalAmount);
            codTableRef.child("orderID").setValue("#" + orderID);
            codTableRef.child("productName").setValue(productName.getText().toString()); // Set the product name from TextView
            codTableRef.child("payment").setValue("not paid");
            // Redirect to CODFragment
            cod codFragment = new cod();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, codFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            Toast.makeText(getContext(), "Order placed successfully", Toast.LENGTH_SHORT).show();
        } else {
            codTableRef.child("userEmail").setValue(loggedInUserEmail);
            codTableRef.child("name").setValue(name);
            codTableRef.child("phoneNumber").setValue(phoneNumber);
            codTableRef.child("pinCode").setValue(pinCode);
            codTableRef.child("addressLine1").setValue(addressLine1);
            codTableRef.child("addressLine2").setValue(addressLine2);
            codTableRef.child("paymentMode").setValue(paymentMode);
            codTableRef.child("totalAmount").setValue(totalAmount);
            codTableRef.child("orderID").setValue("#" + orderID);
            codTableRef.child("productName").setValue(productName.getText().toString()); // Set the product name from TextView
            codTableRef.child("payment").setValue("paid");
            cardpayment cardpayment = new cardpayment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, cardpayment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
