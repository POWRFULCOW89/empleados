package me.diegomelo.empleados;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class EmployeeListActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private ListView employeeListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> employeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        db = FirebaseFirestore.getInstance();
        employeeListView = findViewById(R.id.employeeListView);
        FloatingActionButton fab = findViewById(R.id.fab);

        employeeList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, employeeList);
        employeeListView.setAdapter(adapter);

        loadEmployees();

        fab.setOnClickListener(view -> startActivity(new Intent(EmployeeListActivity.this, AddEmployeeActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload the employees list each time the activity is resumed
        loadEmployees();
    }

    private void loadEmployees() {
        db.collection("employees").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                employeeList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String name = document.getString("name");
                    employeeList.add(name);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}
