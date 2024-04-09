package com.example.skin_save;

public class NewProduct {
    private String imageUrl;
    private String name;
    private String price;
    private String quantity;
    private String description;

    public NewProduct() {
        // Default constructor required for Firebase
    }

    public NewProduct(String name, String price, String quantity, String imageUrl) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
