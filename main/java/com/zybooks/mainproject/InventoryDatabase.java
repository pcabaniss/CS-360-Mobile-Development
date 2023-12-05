package com.zybooks.mainproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class InventoryDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "inventory.db";
    private static final int VERSION = 1;

    // Variable to check for existing instance
    private static InventoryDatabase inventoryDatabase;

    // Return current or new instance
    public static InventoryDatabase getInstance(Context context) {
        if (inventoryDatabase == null) {
            inventoryDatabase = new InventoryDatabase(context);
        }

        return  inventoryDatabase;
    }


    // Constructor
    public InventoryDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    // Database table for inventory items
    private static final class InventoryTable {
        private static final String TABLE = "inventory";
        private static final String COL_ID = "_id";
        private static final String COL_NAME = "name";
        private static final String COL_QUANTITY = "quantity";
    }

    // Database table for user information
    private static final class UserTable {
        private static final String TABLE = "users";
        private static final String COL_ID = "_id";
        private static final String COL_USERNAME = "username";
        private static final String COL_PASSWORD = "password";
    }
    //-------------------------------Account Functions--------------------------------------------\\

    // Add a user
    public boolean addAccount(String username, String password) {
        // Check for existing user
        if (userExists(username)) {
            System.out.println("User Exists!");
            return false;
        }
        System.out.println("New user.");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues val = new ContentValues();

        val.put(UserTable.COL_USERNAME, username);
        val.put(UserTable.COL_PASSWORD, password);

        long id = db.insert(UserTable.TABLE, null, val);

        return id != -1;
    }

    // Log in user
    public boolean login(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + UserTable.TABLE + " WHERE username = ? " +
                "AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        return cursor.getCount() > 0;
    }

    // Check if a user exists
    public boolean userExists(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + UserTable.TABLE + " WHERE username = ?";

        Cursor cursor = db.rawQuery(query, new String[]{username});

        return cursor.getCount() > 0;
    }

    //-------------------------------On create/upgrade--------------------------------------------\\


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Instantiate databases
            db.execSQL("CREATE TABLE " + InventoryTable.TABLE + " (" +
                    InventoryTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    InventoryTable.COL_NAME + " TEXT, " +
                    InventoryTable.COL_QUANTITY + " INTEGER)" );

            db.execSQL("CREATE TABLE " + UserTable.TABLE + " (" +
                UserTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserTable.COL_USERNAME + " TEXT, " +
                UserTable.COL_PASSWORD + " TEXT)" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Check for existing database
        db.execSQL("DROP TABLE IF EXISTS " + UserTable.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + InventoryTable.TABLE);
        onCreate(db);

    }

    //----------------------------------Item Functions--------------------------------------------\\

    // Return list if it exists
    public List<ListItem> getItemList() {
        List<ListItem> itemList = new ArrayList<ListItem>();

        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + InventoryTable.TABLE;
        Cursor cursor = db.rawQuery(query, new String[]{});
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(0);
                String name = cursor.getString(1);
                int quantity = cursor.getInt(2);
                itemList.add(new ListItem(id, name, quantity));
            }
        }
            cursor.close();

            return itemList;
    }

    // Add a new item
    public boolean addItem(String name, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues val = new ContentValues();
        val.put(InventoryTable.COL_NAME, name);
        val.put(InventoryTable.COL_QUANTITY, quantity);

        long id = db.insert(InventoryTable.TABLE, null, val);

        return id != -1;
    }

    // Update an existing item
    public boolean updateItem(ListItem listItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues val = new ContentValues();
        val.put(InventoryTable.COL_NAME, listItem.getName());
        val.put(InventoryTable.COL_QUANTITY, listItem.getQuantity());

        int update = db.update(InventoryTable.TABLE, val,
                InventoryTable.COL_ID + " = ?",
                new String[]{String.valueOf(listItem.getId())});

        return update > 0;
    }

    // Delete an item
    public boolean deleteItem(ListItem listItem) {
        SQLiteDatabase db = getWritableDatabase();

        int deleted = db.delete(InventoryTable.TABLE, InventoryTable.COL_ID + " = ?",
                new String[]{String.valueOf(listItem.getId())});

        return deleted > 0;
    }


}
