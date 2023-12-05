package com.zybooks.mainproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// The inventory grid list
public class InventoryActivity extends AppCompatActivity {

    // Set recyclerview for rendering grid.
    private RecyclerView recyclerView;
    private List<ListItem> itemList;
    private Menu customMenu;
    TextView empty;
    Button addItem;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;


    // Instantiate database
    InventoryDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        // initiate database to populate list
        db = InventoryDatabase.getInstance(getApplicationContext());
        itemList = db.getItemList();

        addItem = findViewById(R.id.add_item_button);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewItemActivity.class);
                startActivity(intent);
            }
        });


        // Set up recyclerview
        recyclerView = findViewById(R.id.item_listView);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        empty = findViewById(R.id.empty);

        recyclerViewAdapter = new RecyclerViewAdapter(itemList, this, db);
        recyclerViewAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                checkEmpty();
            }
        });

        recyclerView.setAdapter(recyclerViewAdapter);

        checkEmpty();
    }

    /**
     * Disable going back to login
     */
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    /**
     * Checks the list for updates and makes sure its populated
     */
    public void checkEmpty() {
        if (itemList.isEmpty()) {
            System.out.println("Empty list!");
            recyclerView.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
        }
    }

    // Menu options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.Settings:
                intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                return true;

            case R.id.logout:
                intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
