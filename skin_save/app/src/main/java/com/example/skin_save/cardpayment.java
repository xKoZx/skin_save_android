package com.example.skin_save;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.skin_save.R;
import com.example.skin_save.cod;

import java.util.Calendar;

// Inside your Fragment or Activity class
public class cardpayment extends Fragment {

    private EditText nameEditText, cardNumberEditText, expiryDateEditText, cvvEditText;
    private Button proceedButton;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cardpayment, container, false);

        // Find views by their IDs
        nameEditText = view.findViewById(R.id.nameEditText);
        cardNumberEditText = view.findViewById(R.id.cardNumberEditText);
        expiryDateEditText = view.findViewById(R.id.expiryDateEditText);
        cvvEditText = view.findViewById(R.id.cvvEditText);
        proceedButton = view.findViewById(R.id.proceedButton);

        expiryDateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString().trim();
                if (input.length() == 2 && !input.contains("/")) {
                    s.append("/");
                }
            }
        });

        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate card details
                if (validateCardDetails()) {
                    // Show "Verifying details" loading window
                    showLoadingWindow("Verifying details...");

                    // Delay the "Contacting Bank" loading window for 5 seconds
                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Hide the "Verifying details" loading window
                            hideLoadingWindow();

                            // Show "Contacting Bank" loading window
                            showLoadingWindow("Contacting Bank...");

                            // Delay the transaction for another 5 seconds
                            v.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // Proceed with the payment
                                    performPayment();

                                    cod codFragment = new cod();
                                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.container, codFragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                    // Toast.makeText(getContext(), "Order placed successfully", Toast.LENGTH_SHORT).show();

                                    // Hide the "Contacting Bank" loading window
                                    hideLoadingWindow();
                                }
                            }, 5000); // 5000 milliseconds = 5 seconds
                        }
                    }, 5000); // 5000 milliseconds = 5 seconds
                }
            }
        });

        return view;
    }

    private boolean validateCardDetails() {
        // Get the input values from the EditText fields
        String nameOnCard = nameEditText.getText().toString().trim();
        String cardNumber = cardNumberEditText.getText().toString().trim();
        String expiryDate = expiryDateEditText.getText().toString().trim();
        String cvv = cvvEditText.getText().toString().trim();

        // Perform validation checks
        if (TextUtils.isEmpty(nameOnCard)) {
            nameEditText.setError("Enter name on card");
            return false;
        }

        if (TextUtils.isEmpty(cardNumber) || cardNumber.length() != 16) {
            cardNumberEditText.setError("Invalid card number");
            return false;
        }

        if (TextUtils.isEmpty(expiryDate) || !isValidExpiryDate(expiryDate)) {
            expiryDateEditText.setError("Invalid expiry date");
            return false;
        }

        if (TextUtils.isEmpty(cvv) || cvv.length() != 3) {
            cvvEditText.setError("Invalid CVV");
            return false;
        }

        // All validations passed
        return true;
    }

    private boolean isValidExpiryDate(String expiryDate) {
        // Validate the expiry date format (MM/YY)
        if (expiryDate.length() != 5 || expiryDate.charAt(2) != '/') {
            return false;
        }

        // Extract month and year values
        String[] parts = expiryDate.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);

        // Get the current year and month
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR) % 100; // Take last two digits
        int currentMonth = calendar.get(Calendar.MONTH) + 1; // Months are zero-based, so add 1

        // Validate the year
        if (year < currentYear || year < 23) {
            return false;
        }

        // Validate the month if the year is the current year
        if (year == currentYear && month < currentMonth) {
            return false;
        }

        return true;
    }

    private void performPayment() {
        // Perform the payment logic here
        // This method will be called when all the card details are valid
        Toast.makeText(getActivity(), "Payment Successful!", Toast.LENGTH_SHORT).show();
    }

    private void showLoadingWindow(String message) {
        progressDialog = ProgressDialog.show(getActivity(), "", message, true);
    }

    private void hideLoadingWindow() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
