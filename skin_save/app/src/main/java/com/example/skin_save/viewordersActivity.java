package com.example.skin_save;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.skin_save.R;

public class viewordersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vieworders);

        // Create an instance of the ViewOrdersFragment
       vieworders viewOrdersFragment = new vieworders();

        // Perform the fragment transaction to add ViewOrdersFragment to the activity
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.container, viewOrdersFragment);
        transaction.commit();
    }
}
