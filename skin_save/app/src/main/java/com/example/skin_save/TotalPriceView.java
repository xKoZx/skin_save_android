package com.example.skin_save;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.skin_save.R;

public class TotalPriceView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_price_view);

        // Create an instance of the ViewOrdersFragment
        paymentprice paymentpriceFragment = new paymentprice();

        // Perform the fragment transaction to add ViewOrdersFragment to the activity
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.container, paymentpriceFragment);
        transaction.commit();
    }
}
