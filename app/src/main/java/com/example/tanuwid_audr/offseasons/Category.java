package com.example.tanuwid_audr.offseasons;

import java.io.Serializable;

/**
 * Created by JERVEY_SAMU on 4/24/2018.
 */

public class Category implements Serializable {

    private String categoryName;

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName;}

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
}
