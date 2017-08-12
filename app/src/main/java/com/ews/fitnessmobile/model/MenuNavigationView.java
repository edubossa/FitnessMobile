package com.ews.fitnessmobile.model;

/**
 * Created by wallace on 11/07/17.
 */
public enum MenuNavigationView {

    UNITS(10),
    STUDENT(11),
    TRAINING(12),
    ACCOUNT(13),
    ABOUT(14),
    EXIT(15),
    MAP(16);

    private int itemId;

    MenuNavigationView(int itemId) {
        this.itemId = itemId;
    }

    public int getItemId() {
        return itemId;
    }

    public static MenuNavigationView findMenu(int itemId) {
        for (MenuNavigationView m : values()) {
            if (m.getItemId() == itemId) return m;
        }
        return EXIT;
    }

}
