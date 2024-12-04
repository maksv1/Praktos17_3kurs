package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private EditText nameText, typeText;
    private Button addBtn, updateBtn, delBtn;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private String selectedClothingName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Paper.init(this);
        nameText = findViewById(R.id.nameText); // Название одежды
        typeText = findViewById(R.id.typeText); // Тип одежды
        addBtn = findViewById(R.id.addButton);
        updateBtn = findViewById(R.id.updateButton);
        delBtn = findViewById(R.id.deleteButton);
        listView = findViewById(R.id.listView);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getClothingNames());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                selectedClothingName = adapter.getItem(i);
                Clothing clothing = Paper.book().read(selectedClothingName, null);
                if (clothing != null) {
                    nameText.setText(clothing.getName());
                    typeText.setText(clothing.getType());
                }
            }
        });

        addBtn.setOnClickListener(view -> {
            String name = nameText.getText().toString();
            String type = typeText.getText().toString();
            if (!name.isEmpty() && !type.isEmpty()) {
                Clothing clothing = new Clothing(name, type);
                Paper.book().write(name, clothing);
                updateClothingList();
                clearInputs();
            }
        });

        updateBtn.setOnClickListener(view -> {
            if (selectedClothingName == null) {
                Toast.makeText(MainActivity.this, "Выберите одежду", Toast.LENGTH_SHORT).show();
                return;
            }
            String name = nameText.getText().toString();
            String type = typeText.getText().toString();
            if (!name.isEmpty() && !type.isEmpty()) {
                Paper.book().delete(selectedClothingName);
                Clothing updatedClothing = new Clothing(name, type);
                Paper.book().write(name, updatedClothing);
                updateClothingList();
                clearInputs();
            }
        });

        delBtn.setOnClickListener(view -> {
            if (selectedClothingName == null) {
                Toast.makeText(MainActivity.this, "Выберите одежду", Toast.LENGTH_SHORT).show();
                return;
            }
            Paper.book().delete(selectedClothingName);
            updateClothingList();
            clearInputs();
        });
    }

    private void clearInputs() {
        nameText.setText("");
        typeText.setText("");
        selectedClothingName = null;
    }

    private void updateClothingList() {
        adapter.clear();
        adapter.addAll(getClothingNames());
        adapter.notifyDataSetChanged();
    }

    private List<String> getClothingNames() {
        return new ArrayList<>(Paper.book().getAllKeys());
    }
}
