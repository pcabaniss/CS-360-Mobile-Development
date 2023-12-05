package com.zybooks.mainproject;

import java.io.Serializable;

// Custom list item
public class ListItem implements Serializable{
    private long itemID;
    private String itemName;
    private int itemQuantity;

    /**
     * Default constructor
     */
    public ListItem() {}

    /**
     * Constructor that initializes new item at 0
     * @param id
     * @param name
     */
    public ListItem(long id, String name) {
        itemID = id;
        itemName = name;
        itemQuantity = 0;
    }

    /**
     * Constructor for full item creation
     * @param id
     * @param name
     * @param quantity
     */
    public ListItem(long id, String name, int quantity) {
        itemID = id;
        itemName = name;
        itemQuantity = quantity;
    }

    /**
     * Increment the amount by 1
     */
    public void increment() {
        this.itemQuantity++;
    }

    /**
     * Decrement the amount by 1
     */
    public void decrement() {
        if (itemQuantity > 0) {
            this.itemQuantity--;
        }
    }

    // Setters
    public void setId(long id) {
        this.itemID = id;
    }

    public void setName(String name) {
        this.itemName = name;
    }

    public void setQuantity(int quantity) {
        this.itemQuantity = quantity;
    }

    // Getters
    public long getId() {
        return itemID;
    }

    public String getName() {
        return itemName;
    }

    public int getQuantity() {
        return itemQuantity;
    }

}
