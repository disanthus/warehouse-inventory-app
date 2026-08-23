package com.example.project_1;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder> {
    private Context context;
    private Cursor cursor;
    private DatabaseHelper databaseHelper;
    public InventoryAdapter(Context context, Cursor cursor, DatabaseHelper databaseHelper) {
        this.context = context;
        this.cursor = cursor;
        this.databaseHelper = databaseHelper;
    }
    @NonNull
    @Override
    public InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inventory_row, parent, false);
        return new InventoryViewHolder(view);
    }
    
    @Override
    
    public void onBindViewHolder(@NonNull InventoryViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        String itemName = cursor.getString(cursor.getColumnIndexOrThrow("item_name"));
        int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
        String location = cursor.getString(cursor.getColumnIndexOrThrow("location"));
        
        holder.itemName.setText(itemName);
        holder.quantity.setText(String.valueOf(quantity));
        holder.location.setText(location);
        holder.deleteButton.setOnClickListener(v -> {
            databaseHelper.deleteInventoryItem(id);
            Toast.makeText(context, "Item deleted!", Toast.LENGTH_SHORT).show();
            notifyDataSetChanged();
        });
    }
    
    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
    
    public static class InventoryViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView quantity;
        TextView location;
        Button deleteButton;
        public InventoryViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.rowItemName);
            quantity = itemView.findViewById(R.id.rowItemQuantity);
            location = itemView.findViewById(R.id.rowItemLocation);
            deleteButton = itemView.findViewById(R.id.rowDeleteButton);
        }
    }
}
