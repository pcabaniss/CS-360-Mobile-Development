package com.zybooks.mainproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Custom adapter for rendering items in grid.
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    // Get context
    private Context itemContext;
    private List<ListItem> itemList;
    InventoryDatabase db;



    public RecyclerViewAdapter(List<ListItem> items, Context context, InventoryDatabase database) {
        itemList = items;
        itemContext = context;
        db = database;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_item, parent, false);
        MyViewHolder mvh = new MyViewHolder(view, db);

        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ListItem currItem = itemList.get(position);


        // Set each items data
        holder.textView.setText(currItem.getName());
        holder.quantityView.setText(String.valueOf(currItem.getQuantity()));

        // Send to edit activity
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;
                intent = new Intent(itemContext, EditItemActivity.class);
                intent.putExtra(EditItemActivity.EXTRA, currItem);
                itemContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    // Custom viewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        private TextView textView;
        private TextView quantityView;

        Button button;

        InventoryDatabase db;

        public MyViewHolder(@NonNull View itemView, InventoryDatabase database) {
            super(itemView);

            db = database;

            textView = itemView.findViewById(R.id.item_name);
            quantityView = itemView.findViewById(R.id.item_amount);

        }

    }
}
