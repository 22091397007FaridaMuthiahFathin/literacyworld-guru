package id.literacyworld.guru;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.literacy.literacyworld.R;

import java.util.ArrayList;
import java.util.List;

public class BacaPDF extends AppCompatActivity {

    ListView listView;
    DatabaseReference databaseReference;
    List<putPDF> uploadedPDF;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baca_pdf);

        listView = findViewById(R.id.listview);
        uploadedPDF = new ArrayList<>();

        retrievePDFFile();

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            putPDF putPDF = uploadedPDF.get(i);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setType("application/pdf");
            intent.setData(Uri.parse(putPDF.getUrl()));
            startActivity(intent);
        });
    }

    private void retrievePDFFile() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Upload PDF");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uploadedPDF.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    putPDF putPDF = ds.getValue(putPDF.class);
                    uploadedPDF.add(putPDF);
                }
                String[] uploadsName = new String[uploadedPDF.size()];
                for (int i = 0; i < uploadsName.length; i++) {
                    uploadsName[i] = uploadedPDF.get(i).getName();
                }

                if (arrayAdapter == null) {
                    arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                            R.layout.activity_list_item_pdf, R.id.text1, uploadsName) {

                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            if (convertView == null) {
                                convertView = getLayoutInflater().inflate(R.layout.activity_list_item_pdf, parent, false);
                            }

                            TextView textView = convertView.findViewById(R.id.text1);
                            ImageView deleteIcon = convertView.findViewById(R.id.delete_icon);

                            textView.setText(uploadsName[position]); // Set nama PDF
                            textView.setTextColor(Color.BLACK);
                            textView.setTextSize(20);

                            // Atur tampilan ikon ceklis berdasarkan status file PDF
                            putPDF pdf = uploadedPDF.get(position);
                            if (pdf.isRead()) {
                                // Jika file telah dibaca, tampilkan ikon ceklis
                                Drawable checkIcon = getResources().getDrawable(R.drawable.checklist);
                                checkIcon.setBounds(0, 0, 50, 50); // Ubah ukuran ikon sesuai kebutuhan
                                textView.setCompoundDrawables(checkIcon, null, null, null);
                            } else {
                                // Jika file belum dibaca, hilangkan ikon ceklis
                                textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                            }

                            // Tambahkan listener untuk ikon hapus
                            deleteIcon.setOnClickListener(v -> removePDF(position));

                            return convertView;
                        }
                    };

                    listView.setAdapter(arrayAdapter);
                } else {
                    // Update data pada adapter jika sudah diinisialisasi sebelumnya
                    arrayAdapter.clear();
                    arrayAdapter.addAll(uploadsName);
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("BacaPDF", "Database Error: " + error.getMessage());
            }
        });
    }

    private void removePDF(int position) {
        // Pastikan bahwa position berada dalam rentang yang valid
        if (position < 0 || position >= uploadedPDF.size()) {
            Toast.makeText(BacaPDF.this, "Invalid position", Toast.LENGTH_SHORT).show();
            return;
        }

        putPDF pdfToRemove = uploadedPDF.get(position);
        // Dapatkan ID dari PDF yang akan dihapus
        String pdfIdToRemove = pdfToRemove.getId();

        // Periksa apakah pdfIdToRemove tidak null
        if (TextUtils.isEmpty(pdfIdToRemove)) {
            Toast.makeText(BacaPDF.this, "Invalid PDF ID", Toast.LENGTH_SHORT).show();
            return;
        }

        // Periksa apakah databaseReference telah diinisialisasi dengan benar
        if (databaseReference == null) {
            Toast.makeText(BacaPDF.this, "Database reference is null", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hapus entri dari Firebase Database
        databaseReference.child(pdfIdToRemove).removeValue()
                .addOnSuccessListener(aVoid -> {
                    // Hapus item dari daftar lokal
                    uploadedPDF.remove(position);
                    // Memberitahu adapter bahwa data telah berubah
                    arrayAdapter.notifyDataSetChanged();
                    // Tampilkan pesan sukses
                    Toast.makeText(BacaPDF.this, "File Deleted", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Tangani kesalahan saat menghapus dari Firebase Database
                    Toast.makeText(BacaPDF.this, "Failed to delete file from database: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}