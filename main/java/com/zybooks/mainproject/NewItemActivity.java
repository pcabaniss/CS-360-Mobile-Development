package com.zybooks.mainproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// Controls creating a new item
public class NewItemActivity extends AppCompatActivity {

    // instantiate database
    InventoryDatabase db;

    EditText nameText;
    EditText quantity;

    Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        nameText = findViewById(R.id.list_item_name);
        quantity = findViewById(R.id.item_quantity);
        db = InventoryDatabase.getInstance(getApplicationContext());
        create = findViewById(R.id.create_item_button);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean adding = db.addItem(nameText.getText().toString(), Integer.valueOf(quantity.getText().toString()));
                if (adding) {
                    Intent intent = new Intent(getApplicationContext(), InventoryActivity.class);

                    startActivity(intent);
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "There was an issue adding an item, please try again.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });




    }
}
