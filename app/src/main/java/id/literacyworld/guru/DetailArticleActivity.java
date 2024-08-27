package id.literacyworld.guru;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.literacy.literacyworld.R;

public class DetailArticleActivity extends AppCompatActivity {

    private TextView textViewTitle, textViewContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewContent = findViewById(R.id.textViewContent);

        // Mendapatkan data dari Intent
        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");

        // Menampilkan data di TextView
        textViewTitle.setText(title);
        textViewContent.setText(content);
    }
}