package com.example.project_1;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
public class InventoryActivity extends AppCompatActivity {
    RecyclerView inventoryRecyclerView;
    Button addButton;
    Button smsButton;

    DatabaseHelper databaseHelper;
    InventoryAdapter inventoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.inventory);

        // connect to xml
        inventoryRecyclerView = findViewById(R.id.inventoryRecyclerView);
        addButton = findViewById(R.id.addButton);
        smsButton = findViewById(R.id.smsButton);

        // connect to db
        databaseHelper = new DatabaseHelper(this);

        // recyclerview
        inventoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadInventory();

        // clicking add item button sends user to add item screen
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(InventoryActivity.this, AddItemActivity.class);
            startActivity(intent);
        });

        // sms button sends user to sms screen
        smsButton.setOnClickListener(v -> {
            Intent intent = new Intent(InventoryActivity.this, SmsNotificationActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (databaseHelper != null) {
            loadInventory();
        }
    }

    private void loadInventory() {
        Cursor cursor = databaseHelper.getAllInventory();
        inventoryAdapter = new InventoryAdapter(this, cursor, databaseHelper);
        inventoryRecyclerView.setAdapter(inventoryAdapter);
    }
}
