package me.diegomelo.empleados;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddEmployeeActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private EditText nameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        db = FirebaseFirestore.getInstance();
        nameField = findViewById(R.id.nameField);
        Button addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(view -> addEmployee());
    }

    private void addEmployee() {
        String name = nameField.getText().toString();

        db.collection("employees")
                .add(new Employee(name))
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(AddEmployeeActivity.this, "Employee added.", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(AddEmployeeActivity.this, "Error adding employee.", Toast.LENGTH_SHORT).show());
    }
}