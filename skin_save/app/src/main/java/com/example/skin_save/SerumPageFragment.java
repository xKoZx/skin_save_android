package com.example.skin_save;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.skin_save.R;

public class  SerumPageFragment extends Fragment {

    private TextView productName;
    private TextView productPrice;
    private Button addToCartButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_serum_page, container, false);

        productName = view.findViewById(R.id.productName);
        productPrice = view.findViewById(R.id.productPrice);
        addToCartButton = view.findViewById(R.id.addToCartButton);

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform action when the button is clicked
                // For example, add the product to the cart
            }
        });

        return view;
    }
}
