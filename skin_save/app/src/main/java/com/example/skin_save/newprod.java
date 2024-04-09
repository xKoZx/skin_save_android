package com.example.skin_save;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class newprod extends Fragment {
    private DatabaseReference cartReference;
    // Firebase Storage reference
    private StorageReference storageReference;

    private ImageView productImage;
    private TextView productName, productPrice, productDescription;
    private Button addToCartButton;

    private DatabaseReference databaseReference;
    private NewProduct product; // NewProduct object to store the fetched product data

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newprod, container, false);

        productImage = view.findViewById(R.id.productImage);
        productName = view.findViewById(R.id.productName);
        productPrice = view.findViewById(R.id.productPrice);
        productDescription = view.findViewById(R.id.productDescription);
        addToCartButton = view.findViewById(R.id.addToCartButton);

        // Initialize Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("newProducts");
        cartReference = firebaseDatabase.getReference("cart");

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        retrieveLatestProduct();
    }

    private void retrieveLatestProduct() {
        // Query the database to retrieve the latest product with category "Normal skin"
        Query query = databaseReference.orderByChild("category").equalTo("Normal skin").limitToLast(1);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    product = snapshot.getValue(NewProduct.class);
                    if (product != null) {
                        displayProduct(product);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }

    private void displayProduct(NewProduct product) {
        // Display the product data in the respective views
        Glide.with(requireContext())
                .load(product.getImageUrl())
                .apply(RequestOptions.centerCropTransform())
                .into(productImage);
        productName.setText(product.getName());
        productPrice.setText("₹ " + product.getPrice() + ".00");
        productDescription.setText(product.getDescription());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
    }

    private void addToCart() {
        if (product != null) {
            TextView productNameTextView = getView().findViewById(R.id.productName);
            TextView productPriceTextView = getView().findViewById(R.id.productPrice);
            ImageView productImageView = getView().findViewById(R.id.productImage);
            String productName = productNameTextView.getText().toString();
            String productPrice = productPriceTextView.getText().toString();
            String imageUrl = product.getImageUrl();

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String userEmail = user.getEmail();
                CartItem cartItem = new CartItem(productName, productPrice, userEmail, imageUrl);
                String cartItemKey = cartReference.push().getKey();
                cartReference.child(cartItemKey).setValue(cartItem);
                Toast.makeText(getContext(), "Product added to cart", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Define a CartItem class to represent the product in the cart
    private static class CartItem {
        public String name;
        public String price;
        public String email;
        public String imageURL;

        public CartItem() {
            // Default constructor required for Firebase Realtime Database
        }

        public CartItem(String name, String price, String email, String imageURL) {
            this.name = name;
            this.price = price;
            this.email = email;
            this.imageURL = imageURL;
        }
    }
}
