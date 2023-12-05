package com.zybooks.mainproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

// Handles the events on a Edit item page
public class EditItemActivity extends AppCompatActivity {

    // Key for incoming data
    public static final String EXTRA = "extra_data";

    // Instantiate database
    InventoryDatabase db;

    TextView name;
    EditText quantity;
    int init = 0;

    Button saveData;
    Button deleteItem;

    ImageButton decrease;
    ImageButton increase;

    private ListItem currItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        db = InventoryDatabase.getInstance(this);

        name = findViewById(R.id.item_name);
        quantity = findViewById(R.id.edit_quantity);

        saveData = findViewById(R.id.save_button);
        saveData.setEnabled(false);
        deleteItem = findViewById(R.id.delete_button);
        decrease = findViewById(R.id.minus_button);
        increase = findViewById(R.id.add_button);

        // Set up for add and minus buttons
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrement(v);
            }
        });

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment(v);
            }
        });

        // Get incoming data
        ListItem item = (ListItem) getIntent().getSerializableExtra(EXTRA);
        if (item != null){
            currItem = item;
            name.setText(item.getName());
            init = item.getQuantity();
        }

        quantity.setText(String.valueOf(init));

        name.addTextChangedListener(watcher);
        quantity.addTextChangedListener(watcher);

    }


    // Watcher for redundancy
    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            saveData.setEnabled(true);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    /**
     * Handles saving the data to the db
     * @param view
     */
    public void handleSave(View view) {
        boolean success;

        if (currItem != null) {
            currItem.setName(name.getText().toString());
            currItem.setQuantity(getQuantity());
            success = db.updateItem(currItem);
        }
        else {
            success = db.addItem(name.getText().toString(), getQuantity());
        }

        if (success) {
            NavUtils.navigateUpFromSameTask(this);
        }
        else {
            Toast.makeText(EditItemActivity.this, "Error saving data.", Toast.LENGTH_SHORT);
        }
    }

    /**
     * Handles deleting item
     * @param view
     */
    public void handleDelete(View view) {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Double checking").setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean del = db.deleteItem(currItem);
                        finish();

                        if (del) {
                            NavUtils.navigateUpFromSameTask(EditItemActivity.this);
                        }
                        else {
                            Toast.makeText(EditItemActivity.this, "Error deleting item.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("No", null).show();
    }

    /**
     * Add to value
     * @param view
     */
    public void increment(View view) {
        quantity.setText(String.valueOf(getQuantity() + 1));
    }

    /**
     * Decrease value
     * @param view
     */
    public void decrement(View view) {
        quantity.setText(String.valueOf(getQuantity() - 1));
    }

    /**
     * Gets quantity from string and makes sure its a number
     * @return
     */
    private int getQuantity() {
        String raw = quantity.getText().toString().replaceAll("[^\\d.]", "").trim();
        int num = raw.isEmpty() ? 0 : Integer.parseInt(raw);

        return num;
    }
 }
