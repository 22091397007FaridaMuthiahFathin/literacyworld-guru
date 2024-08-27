package id.literacyworld.guru;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.literacy.literacyworld.R;

import java.util.ArrayList;
import java.util.List;


public class Dashboard extends AppCompatActivity {


    private DatabaseReference databaseReference;
    private ViewPager2 viewPager; // Pastikan ini adalah ViewPager2
    private List<putPDF> pdfList;
    private PDFPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Inisialisasi ViewPager2
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        pdfList = new ArrayList<>();
        adapter = new PDFPagerAdapter(this, pdfList);
        // Pastikan ini menggunakan ViewPager2
        viewPager.setAdapter(adapter);


        retrievePDFFile();


        LinearLayout menulisLayout = findViewById(R.id.menulisLayout);
        LinearLayout profilLayout = findViewById(R.id.profilLayout);
        LinearLayout uploadPDFLayout = findViewById(R.id.uploadPDFLayout);
        LinearLayout uploadVideoLayout = findViewById(R.id.uploadVideoLayout);


        menulisLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, ListArticlesActivity.class);
                startActivity(intent);
            }
        });

        uploadPDFLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, PDF.class);
                startActivity(intent);
            }
        });

        uploadVideoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, VUpload.class);
                startActivity(intent);
            }
        });



        profilLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Profil.class);
                startActivity(intent);
            }
        });
    }
    private void retrievePDFFile() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Upload PDF");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pdfList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    putPDF putPDF = ds.getValue(putPDF.class);
                    pdfList.add(putPDF);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }
}