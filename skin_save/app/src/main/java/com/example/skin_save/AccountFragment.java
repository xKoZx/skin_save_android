package com.example.skin_save;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.skin_save.R;
import com.example.skin_save.LoginActivity;
import com.example.skin_save.myorders;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountFragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get the username from the shared preferences
        String username = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("username", "");
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
// If the username is not empty, display it
        if (user != null) {
            // Get the logged-in user's email
            String loggedInUserEmail = user.getEmail();

            // Set the text of the TextView to the username
            TextView usernameTextView = view.findViewById(R.id.usernameTextView);

            // Set the text of the TextView to the username
            usernameTextView.setText(loggedInUserEmail);

        }


        // Find the My Orders button
        Button myOrdersButton = view.findViewById(R.id.myOrdersButton);

        // Set a click listener for the My Orders button
        myOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an instance of the MyOrdersFragment
                myorders myOrdersFragment = new myorders();

                // Perform the fragment transaction to replace the current fragment with MyOrdersFragment
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.container, myOrdersFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        Button AboutFragment = view.findViewById(R.id.AboutUs);

        AboutFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an instance of the MyOrdersFragment
                AboutFragment aboutUs = new AboutFragment();

                // Perform the fragment transaction to replace the current fragment with MyOrdersFragment
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.container, aboutUs);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // Find the Logout button
        Button logoutButton = view.findViewById(R.id.logoutButton);

        // Set a click listener for the Logout button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a confirmation dialog for logout
                showLogoutConfirmationDialog();
            }
        });
    }

    // Method to show the logout confirmation dialog
    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Logout Confirmation")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Call the logout method or start the LoginActivity
                        logoutUser();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Cancel the dialog
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Method to logout the user and start LoginActivity
    private void logoutUser() {
        // Perform logout logic here
        // ...

        // Start LoginActivity
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}
