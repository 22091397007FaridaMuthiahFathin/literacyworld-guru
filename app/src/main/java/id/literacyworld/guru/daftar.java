package id.literacyworld.guru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.literacy.literacyworld.R;

public class daftar extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword, editTextemail;

    private Button btnRegistrasi;

    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextemail = findViewById(R.id.editTextemail);
        btnRegistrasi = findViewById(R.id.btnRegistrasi);

        database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://literacy-f17fa-default-rtdb.firebaseio.com");

        btnRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                String email = editTextemail.getText().toString();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Data masih kosong",Toast.LENGTH_SHORT).show();
                } else {
                    database = FirebaseDatabase.getInstance().getReference("users");
                    database.child(username).child("username").setValue(username);
                    database.child(username).child("password").setValue(password);
                    database.child(username).child("email").setValue(email);

                    Toast.makeText(getApplicationContext(),"Register Berhasil",Toast.LENGTH_SHORT).show();
                    Intent register = new Intent(getApplicationContext(), login.class);
                    startActivity(register);
                }
            }
        });

    }
}