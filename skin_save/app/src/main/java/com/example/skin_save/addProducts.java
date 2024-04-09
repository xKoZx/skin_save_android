package com.example.skin_save;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class addProducts extends Fragment {

    private static final int REQUEST_IMAGE_GET = 1;

    private Button uploadImageButton;
    private EditText nameEditText;
    private EditText priceEditText;
    private EditText quantityEditText;
    private EditText descriptionEditText;
    private Spinner categorySpinner;
    private Button addButton;
    private ImageView previewImageView;

    private Uri selectedImageUri;

    public addProducts() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_products, container, false);

        uploadImageButton = view.findViewById(R.id.uploadImageButton);
        nameEditText = view.findViewById(R.id.nameEditText);
        priceEditText = view.findViewById(R.id.priceEditText);
        quantityEditText = view.findViewById(R.id.quantityEditView);
        descriptionEditText = view.findViewById(R.id.descriptionEditText);
        categorySpinner = view.findViewById(R.id.categorySpinner);
        addButton = view.findViewById(R.id.addProductButton);
        previewImageView = view.findViewById(R.id.previewImageView);

        // Set a click listener for the Upload Image button
        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        // Set up the category Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.product_category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        // Set a click listener for the Add button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered values
                String name = nameEditText.getText().toString();
                String price = priceEditText.getText().toString();
                String quantity = quantityEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                Uri imageUri = selectedImageUri;

                // Get the selected category
                String category = categorySpinner.getSelectedItem().toString();

                // Perform any necessary actions to add the product
                addProduct(name, price, quantity, description, imageUri, category);
            }
        });

        // Update the previewImageView when an image is selected
        if (selectedImageUri != null) {
            previewImageView.setImageURI(selectedImageUri);
        }

        return view;
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Image"), REQUEST_IMAGE_GET);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_GET && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                selectedImageUri = data.getData();
                // Perform any necessary actions with the selected image URI
                // For example, you can display the selected image in an ImageView
                previewImageView.setImageURI(selectedImageUri);
            }
        }
    }

    private void addProduct(String name, String price, String quantity, String description, Uri selectedImageUri, String category) {
        // Create a new DatabaseReference for the "newProducts" table
        DatabaseReference newProductsRef = FirebaseDatabase.getInstance().getReference("newProducts");
        Uri uploadImage = selectedImageUri;

        // Generate a unique key for the new product
        String productId = newProductsRef.push().getKey();

        // Create a new Product object with the provided details
        Product product = new Product(productId, name, price, quantity, description, category);

        // Store the product object in the "newProducts" table using the generated key
        newProductsRef.child(productId).setValue(product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Product added successfully
                        clearFields(); // Clear the input fields after adding the product

                        // Upload the selected image to Firebase Storage
                        if (uploadImage != null) {
                            uploadImage(uploadImage, productId);
                        } else {
                            Toast.makeText(getActivity(), "No images found", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to add the product
                        Toast.makeText(getActivity(), "Failed to add product: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadImage(Uri imageUri, String productId) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        // Generate a unique image name using UUID
        String imageName = UUID.randomUUID().toString() + ".jpg";

        // Create a child reference using the generated image name
        StorageReference imageRef = storageRef.child("productImages/" + imageName);

        // Upload the image file to Firebase Storage
        UploadTask uploadTask = imageRef.putFile(imageUri);

        // Add success and failure listeners to handle the upload result
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Image uploaded successfully
                Toast.makeText(getActivity(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();

                // Get the download URL of the uploaded image
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Image download URL retrieved successfully
                        String imageUrl = uri.toString();

                        // Update the product with the image URL in the database
                        updateProductImage(productId, imageUrl);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to retrieve the image download URL
                        Toast.makeText(getActivity(), "Failed to get image download URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Failed to upload the image
                Toast.makeText(getActivity(), "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateProductImage(String productId, String imageUrl) {
        // Create a new DatabaseReference for the specific product in "newProducts"
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference("newProducts").child(productId);

        // Update the "imageUrl" field of the product with the download URL
        productRef.child("imageUrl").setValue(imageUrl)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Image URL updated successfully
                        Toast.makeText(getActivity(), "Image URL updated successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to update the image URL
                        Toast.makeText(getActivity(), "Failed to update image URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearFields() {
        // Clear the input fields
        nameEditText.setText("");
        priceEditText.setText("");
        quantityEditText.setText("");
        descriptionEditText.setText("");
        selectedImageUri = null;
        previewImageView.setImageDrawable(null);
    }

    public class Product {
        private String id;
        private String name;
        private String price;
        private String quantity;
        private String description;
        private String category;

        public Product() {
            // Default constructor required for Firebase
        }

        public Product(String id, String name, String price, String quantity, String description, String category) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
            this.description = description;
            this.category = category;
        }

        // Getters and setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }
}
