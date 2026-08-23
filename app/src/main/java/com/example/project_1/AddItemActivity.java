package com.example.project_1;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
public class AddItemActivity extends AppCompatActivity{
    EditText itemNameInput;
    EditText quantityInput;
    EditText locationInput;
    Button saveItemButton;
    Button cancelButton;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_item);

        // connect java to XML
        itemNameInput = findViewById(R.id.itemNameInput);
        quantityInput = findViewById(R.id.quantityInput);
        locationInput = findViewById(R.id.locationInput);
        saveItemButton = findViewById(R.id.saveItemButton);
        cancelButton = findViewById(R.id.cancelButton);

        // create db helper
        databaseHelper = new DatabaseHelper(this);

        // save item button functions
        saveItemButton.setOnClickListener(v -> {
            String itemName = itemNameInput.getText().toString().trim();
            String quantityTxt = quantityInput.getText().toString().trim();
            String location = locationInput.getText().toString().trim();

            // checks for empty fields
            if (itemName.isEmpty() || quantityTxt.isEmpty() || location.isEmpty()) {
                Toast.makeText(AddItemActivity.this, "Please fill in every field.", Toast.LENGTH_SHORT).show();
                return;
            }

            // convert quantity string to int
            int quantity;
            try {
                quantity = Integer.parseInt(quantityTxt);
            } catch (NumberFormatException e) {
                Toast.makeText(AddItemActivity.this, "Quantity must be a number.", Toast.LENGTH_SHORT).show();
                return;
            }

            // add item to database
            boolean added = databaseHelper.addInventoryItem(itemName, quantity, location);
            // if added successfully, send toast to user
            if (added) {
                Toast.makeText(AddItemActivity.this, "Item added successfully!", Toast.LENGTH_SHORT).show();
                // then return to inventory screen
                finish();
            } else {
                Toast.makeText(AddItemActivity.this, "Unable to add item!", Toast.LENGTH_SHORT).show();
            }
        });

        // cancel button function
        cancelButton.setOnClickListener(v -> {
            finish();
        });
    }
}
