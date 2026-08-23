package com.example.project_1;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DatabaseHelper extends SQLiteOpenHelper {
    // database information
    private static final String DATABASE_NAME = "Warehouse.db";
    private static final int DATABASE_VERSION = 1;
    // user table
    private static final String USERS_TABLE = "users";
    // inventory table
    private static final String INVENTORY_TABLE = "inventory";

    // helper object to create/open/manage database
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create user table
        String createUsersTable = "CREATE TABLE " + USERS_TABLE + " (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "username TEXT UNIQUE, " + "password TEXT)";
        db.execSQL(createUsersTable);

        // create inventory table
        String createInventoryTable = "CREATE TABLE " + INVENTORY_TABLE + " (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "item_name TEXT, " + "quantity INTEGER, " + "location TEXT)";
        db.execSQL(createInventoryTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + INVENTORY_TABLE);
        onCreate(db);
    }

    // user functions
    // create new user
    public boolean addUser(String username, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);

        long result = db.insert(USERS_TABLE, null, values);
        return result != -1;
    }

    // check username and password
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE username = ? AND password = ?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // check whether username already exists in db
    public boolean usernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE username = ?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // CRUD inventory functions
    // create inventory item
    public boolean addInventoryItem(String itemName, int quantity, String location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("item_name", itemName);
        values.put("quantity", quantity);
        values.put("location", location);

        long result = db.insert(INVENTORY_TABLE, null, values);
        return result != -1;
    }

    // read inventory database
    public Cursor getAllInventory() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + INVENTORY_TABLE, null);
    }
    // update inventory item
    public boolean updateInventoryItem(int id, String itemName, int quantity, String location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("item_name", itemName);
        values.put("quantity", quantity);
        values.put("location", location);

        int result = db.update(INVENTORY_TABLE, values, "id = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }
    // delete inventory item
    public boolean deleteInventoryItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(INVENTORY_TABLE, "id = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

}
