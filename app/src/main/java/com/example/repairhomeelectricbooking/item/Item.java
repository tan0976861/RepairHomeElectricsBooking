package com.example.repairhomeelectricbooking.item;

import java.io.Serializable;

public class Item implements Serializable {
    private int resourceID;
    private String title;

    public Item(int resourceID, String title) {
        this.resourceID = resourceID;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }
}
