package com.example.skin_save;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class cleanserprod2 extends Fragment {

    // Firebase Realtime Database reference
    private DatabaseReference cartReference;
    // Firebase Storage reference
    private StorageReference storageReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cleanserprod2, container, false);

        // Get a reference to the "cart" node in the Firebase Realtime Database
        cartReference = FirebaseDatabase.getInstance().getReference("cart");
        // Get a reference to the Firebase Storage
        storageReference = FirebaseStorage.getInstance().getReference();

        // Find the "Add to Cart" button in your layout
        Button addToCartButton = view.findViewById(R.id.addcl2);

        // Set the click listener for the "Add to Cart" button
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the name, price, and image URI of the product
                TextView productNameTextView = view.findViewById(R.id.productName);
                TextView productPriceTextView = view.findViewById(R.id.productPrice);
                ImageView productImageView = view.findViewById(R.id.productImage);
                String productName = productNameTextView.getText().toString();
                String productPrice = productPriceTextView.getText().toString();
                Uri imageUri = getImageUriFromImageView(productImageView);

                // Create a new cart item object
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String userEmail = user.getEmail();
                    CartItem cartItem = new CartItem(productName, productPrice, userEmail, imageUri.toString());

                    // Generate a new key for the cart item
                    String cartItemKey = cartReference.push().getKey();

                    // Store the cart item in the "cart" node in the Firebase Realtime Database
                    cartReference.child(cartItemKey).setValue(cartItem);

                    // Display a success message or perform any other necessary actions
                    Toast.makeText(getContext(), "Product added to cart", Toast.LENGTH_SHORT).show();
                } else {
                    // User is not logged in
                    Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
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

    private Uri getImageUriFromImageView(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            // Create a new bitmap based on the drawable dimensions
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        // Save the bitmap to a temporary file
        File imageFile = new File(getContext().getCacheDir(), "product_image.jpg");
        try {
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get the URI of the temporary file
        return Uri.fromFile(imageFile);
    }
}