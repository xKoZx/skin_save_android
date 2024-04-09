package com.example.skin_save;

import static com.example.skin_save.R.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.skin_save.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class CartFragment extends Fragment {

    private LinearLayout cartContainer;
    private DatabaseReference cartReference;
    private DatabaseReference checkoutReference;
    private Context context;
    private String loggedInUserEmail;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(layout.fragment_cart, container, false);

        cartContainer = view.findViewById(id.cartContainer);
        cartReference = FirebaseDatabase.getInstance().getReference("cart");
        checkoutReference = FirebaseDatabase.getInstance().getReference("checkout");

        // Get the logged-in user's email
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            loggedInUserEmail = user.getEmail();
        }

        cartReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                displayCartItems(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
            }
        });

        return view;
    }

    private void displayCartItems(DataSnapshot dataSnapshot) {
        cartContainer.removeAllViews();

        double totalAmount = 0;
        boolean cartEmpty = true;

        for (DataSnapshot cartItemSnapshot : dataSnapshot.getChildren()) {
            CartItem cartItem = cartItemSnapshot.getValue(CartItem.class);

            if (cartItem != null && loggedInUserEmail != null && loggedInUserEmail.equals(cartItem.getEmail())) {
                cartEmpty = false;
                View cartItemView = LayoutInflater.from(context).inflate(layout.fragment_cart, null);

                TextView productNameTextView = cartItemView.findViewById(id.productName);
                ImageView productImageView = cartItemView.findViewById(id.productImage);
                TextView productPriceTextView = cartItemView.findViewById(id.productPrice);
                TextView quantityTextView = cartItemView.findViewById(id.quantityTextView);

                ImageButton incrementButton = cartItemView.findViewById(id.incrementButton);
                ImageButton decrementButton = cartItemView.findViewById(id.decrementButton);
                ImageButton removeButton = cartItemView.findViewById(id.removeButton);

                productNameTextView.setText(cartItem.getName());
                productPriceTextView.setText(cartItem.getPrice());
                quantityTextView.setText(String.valueOf(cartItem.getQuantity()));

                // Set the product image based on the product name or the image URL
                if (cartItem.getImageURL() != null && !cartItem.getImageURL().isEmpty()) {
                    // Load image from URL
                    Uri imageUri = Uri.parse(cartItem.getImageURL());
                    Glide.with(context)
                            .load(imageUri)
                            .into(productImageView);
                    if (cartItem.getName().equalsIgnoreCase("Centella and Niacinamide Serum")) {
                        productImageView.setImageResource(drawable.serum);
                    } else if (cartItem.getName().equalsIgnoreCase("Red Wine Face Wash With Vitamin C and Aloe|100ml")) {
                        productImageView.setImageResource(drawable.redvfacewash);
                    } else if (cartItem.getName().equalsIgnoreCase("Glow+ Dewy Sunscreen|50g")) {
                        productImageView.setImageResource(drawable.aqualogica);
                    } else if (cartItem.getName().equalsIgnoreCase("5% Cica-Glow Daily Face Moisturizer- 50 g")) {
                        productImageView.setImageResource(drawable.moiturizer);
                    }else if (cartItem.getName().equalsIgnoreCase("Moringa and Vitamin C Cleansing Oil-50ML")) {
                        productImageView.setImageResource(drawable.cleanoil);
                    }else if (cartItem.getName().equalsIgnoreCase("3% Niacinamide Toner 150Ml")) {
                        productImageView.setImageResource(drawable.toner);
                    }else if (cartItem.getName().equalsIgnoreCase("Vitamin C Night Serum")) {
                        productImageView.setImageResource(drawable.pil2);
                    }else if (cartItem.getName().equalsIgnoreCase("Minimalist 10% Niacinamide Face Serum -(30ml)")) {
                        productImageView.setImageResource(drawable.minimalist);
                    }else if (cartItem.getName().equalsIgnoreCase("1% Retinol Face Serum with Bakuchiol 20ml")) {
                        productImageView.setImageResource(drawable.plumserum);
                    }else if (cartItem.getName().equalsIgnoreCase("Cica and Ceramide Cleanser-125ml")) {
                        productImageView.setImageResource(drawable.cleanser1);
                    }else if (cartItem.getName().equalsIgnoreCase("Salicylic Acid + LHA 02% Face Cleanser-100ml")) {
                        productImageView.setImageResource(drawable.cleanser2);
                    }else if (cartItem.getName().equalsIgnoreCase("Centella And Vitamin E Cleanserl")) {
                        productImageView.setImageResource(drawable.cleanser3);
                    }else if (cartItem.getName().equalsIgnoreCase("Ceramide and Vitamin C Sunscreen")) {
                        productImageView.setImageResource(drawable.sunscreen);
                    }else if (cartItem.getName().equalsIgnoreCase("Squalane Sunscreen SPF 50 PA+++")) {
                        productImageView.setImageResource(drawable.sunscreen2);
                    }else if (cartItem.getName().equalsIgnoreCase("Sunscreen Aqua Gel-50g")) {
                        productImageView.setImageResource(drawable.sunscreen3);
                    }else if (cartItem.getName().equalsIgnoreCase("Ceramide and Vitamin C Moisturizer")) {
                        productImageView.setImageResource(drawable.moist1);
                    }else if (cartItem.getName().equalsIgnoreCase("Hyaluronic Acid Oil-Free Gel Moisturiser")) {
                        productImageView.setImageResource(drawable.moist2);
                    }else if (cartItem.getName().equalsIgnoreCase("Tea Tree Body Lotion")) {
                        productImageView.setImageResource(drawable.moist3);
                    }else if (cartItem.getName().equalsIgnoreCase("Avacardo Body Lotion")) {
                        productImageView.setImageResource(drawable.bodycare);
                    }else if (cartItem.getName().equalsIgnoreCase("Shea butter lotion")) {
                        productImageView.setImageResource(drawable.body2);
                    }else if (cartItem.getName().equalsIgnoreCase("Rawls eyecream")) {
                        productImageView.setImageResource(drawable.eyecream);
                    }else if (cartItem.getName().equalsIgnoreCase("Deyga eyecream")) {
                        productImageView.setImageResource(drawable.eye2);
                    }
                } else{
                    // Load image from drawable based on product name

                 //   setProductImageBasedOnName(cartItem.getName(), productImageView);
                }

                incrementButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int quantity = Integer.parseInt(quantityTextView.getText().toString());
                        quantity++;
                        quantityTextView.setText(String.valueOf(quantity));
                        cartItem.setQuantity(quantity);
                        cartReference.child(cartItemSnapshot.getKey()).setValue(cartItem);
                    }
                });

                decrementButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int quantity = Integer.parseInt(quantityTextView.getText().toString());
                        if (quantity > 1) {
                            quantity--;
                            quantityTextView.setText(String.valueOf(quantity));
                            cartItem.setQuantity(quantity);
                            cartReference.child(cartItemSnapshot.getKey()).setValue(cartItem);
                        }
                    }
                });

                removeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cartReference.child(cartItemSnapshot.getKey()).removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(context, "Product removed", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, "Failed to remove product", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });

                cartContainer.addView(cartItemView);

                // Calculate the total amount
                double price = Double.parseDouble(cartItem.getPrice().replaceAll("[₹,]", "").trim());
                totalAmount += price * cartItem.getQuantity();
            }
        }

        if (cartEmpty) {
            // Cart is empty, display appropriate message
            TextView emptyCartTextView = new TextView(context);
            emptyCartTextView.setText("Your cart is empty!");
            emptyCartTextView.setTextColor(ContextCompat.getColor(context, android.R.color.black));
            emptyCartTextView.setPadding(0, 16, 0, 0);
            emptyCartTextView.setTextSize(18);
            emptyCartTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            cartContainer.addView(emptyCartTextView);
        } else {
            // Cart is not empty, display the total amount and proceed to checkout button
            View totalView = LayoutInflater.from(context).inflate(layout.fragment_total_view, cartContainer, false);

            TextView totalAmountTextView = totalView.findViewById(id.totalAmountTextView);
            totalAmountTextView.setText(String.format(Locale.getDefault(), "Total Amount: ₹%.2f", totalAmount));

            Button proceedToCheckoutButton = totalView.findViewById(id.proceedToCheckoutButton);
            proceedToCheckoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        String userEmail = user.getEmail();

                        StringBuilder productNamesBuilder = new StringBuilder();
                        StringBuilder quantitiesBuilder = new StringBuilder();
                        double totalPrice = 0;

                        for (DataSnapshot cartItemSnapshot : dataSnapshot.getChildren()) {
                            CartItem cartItem = cartItemSnapshot.getValue(CartItem.class);
                            if (cartItem != null && loggedInUserEmail != null && loggedInUserEmail.equals(cartItem.getEmail())) {
                                productNamesBuilder.append(cartItem.getName()).append(",");
                                quantitiesBuilder.append(cartItem.getQuantity()).append(",");
                                double price = Double.parseDouble(cartItem.getPrice().replaceAll("[₹,]", "").trim());
                                totalPrice += price * cartItem.getQuantity();
                            }
                        }

                        // Remove the trailing commas if any
                        String productNames = productNamesBuilder.toString();
                        if (productNames.endsWith(",")) {
                            productNames = productNames.substring(0, productNames.length() - 1);
                        }

                        String quantities = quantitiesBuilder.toString();
                        if (quantities.endsWith(",")) {
                            quantities = quantities.substring(0, quantities.length() - 1);
                        }

                        // Update quantity values in the "newProducts" table
                        updateProductQuantities(productNames, quantities);

                        OrderItem orderItem = new OrderItem(productNames, quantities, userEmail, totalPrice);

                        // Generate a new key for the order item
                        String orderItemKey = checkoutReference.push().getKey();

                        // Store the order item in the "checkout" node in the Firebase Realtime Database
                        checkoutReference.child(orderItemKey).setValue(orderItem).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Toast.makeText(getContext(), "Order placed successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Failed to place order", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        // User is not logged in
                        Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
                    }

                    // Replace the current fragment with the CheckoutFragment
                    checkout checkoutFragment = new checkout();
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, checkoutFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });

            cartContainer.addView(totalView);
        }
    }

    private void updateProductQuantities(String productNames, String quantities) {
        DatabaseReference newProductsReference = FirebaseDatabase.getInstance().getReference("newProducts");

        String[] productNameArray = productNames.split(",");
        String[] quantityArray = quantities.split(",");

        for (int i = 0; i < productNameArray.length; i++) {
            String productName = productNameArray[i];
            int quantity = Integer.parseInt(quantityArray[i]);

            Query productQuery = newProductsReference.orderByChild("name").equalTo(productName);
            productQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                   //     NewProduct newProduct = snapshot.getValue(NewProduct.class);
                        if (quantity == 1) {
                            snapshot.getRef().child("quantity").setValue("9");
                        } else if (quantity == 2) {
                            snapshot.getRef().child("quantity").setValue("8");
                        } else if (quantity == 3) {
                            snapshot.getRef().child("quantity").setValue("7");
                        }
                        else if (quantity == 4) {
                            snapshot.getRef().child("quantity").setValue("6");
                        }
                        else if (quantity == 5) {
                            snapshot.getRef().child("quantity").setValue("5");
                        }
                        else if (quantity == 6) {
                            snapshot.getRef().child("quantity").setValue("4");
                        }
                        else if (quantity == 7) {
                            snapshot.getRef().child("quantity").setValue("3");
                        }
                        else if (quantity == 8) {
                            snapshot.getRef().child("quantity").setValue("2");
                        }
                        else if (quantity == 9) {
                            snapshot.getRef().child("quantity").setValue("1");
                        }
                        else if (quantity == 10) {
                            snapshot.getRef().child("quantity").setValue("0");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle the database query cancellation or error scenario
                }
            });
        }
    }

    private void setProductImageBasedOnName(String productName, ImageView productImageView) {
        int drawableResId = 0;

        if (productName.equalsIgnoreCase("Centella and Niacinamide Serum")) {
            drawableResId = drawable.serum;
        } else if (productName.equalsIgnoreCase("Red Wine Face Wash With Vitamin C and Aloe|100ml")) {
            drawableResId = drawable.redvfacewash;
        } else if (productName.equalsIgnoreCase("Glow+ Dewy Sunscreen|50g")) {
            drawableResId = drawable.aqualogica;
        } else if (productName.equalsIgnoreCase("5% Cica-Glow Daily Face Moisturizer- 50 g")) {
            drawableResId = drawable.moiturizer;
        } else if (productName.equalsIgnoreCase("Moringa and Vitamin C Cleansing Oil-50ML")) {
            drawableResId = drawable.cleanoil;
        } else if (productName.equalsIgnoreCase("3% Niacinamide Toner 150Ml")) {
            drawableResId = drawable.toner;
        } else if (productName.equalsIgnoreCase("Vitamin C Night Serum")) {
            drawableResId = drawable.pil2;
        } else if (productName.equalsIgnoreCase("Minimalist 10% Niacinamide Face Serum -(30ml)")) {
            drawableResId = drawable.minimalist;
        } else if (productName.equalsIgnoreCase("1% Retinol Face Serum with Bakuchiol 20ml")) {
            drawableResId = drawable.plumserum;
        }

        if (drawableResId != 0) {
            Drawable drawable = ContextCompat.getDrawable(context, drawableResId);
            productImageView.setImageDrawable(drawable);
        }
    }

    public static class OrderItem {
        private String productNames;
        private String quantities;
        private String userEmail;
        private double totalPrice;

        public OrderItem() {
            // Default constructor required for Firebase
        }

        public OrderItem(String productNames, String quantities, String userEmail, double totalPrice) {
            this.productNames = productNames;
            this.quantities = quantities;
            this.userEmail = userEmail;
            this.totalPrice = totalPrice;
        }

        public String getProductNames() {
            return productNames;
        }

        public void setProductNames(String productNames) {
            this.productNames = productNames;
        }

        public String getQuantities() {
            return quantities;
        }

        public void setQuantities(String quantities) {
            this.quantities = quantities;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }
    }

    public static class CartItem {
        private String name;
        private String price;
        private int quantity;
        private String email;
        private String imageURL;

        public CartItem() {
            // Default constructor required for Firebase
        }

        public CartItem(String name, String price, int quantity, String email, String imageURL) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
            this.email = email;
            this.imageURL = imageURL;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getImageURL() {
            return imageURL;
        }

        public void setImageURL(String imageURL) {
            this.imageURL = imageURL;
        }
    }
}
