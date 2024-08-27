package id.literacyworld.guru;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.literacy.literacyworld.R;

import java.util.ArrayList;


public class ListArticlesActivity extends AppCompatActivity {

    private ListView articleListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> articleTitles;  // Daftar untuk judul artikel
    private ArrayList<Article> articles;  // Daftar untuk objek artikel
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_articles);


        articleListView = findViewById(R.id.articleListView);
        articleTitles = new ArrayList<>();
        articles = new ArrayList<>();  // Inisialisasi daftar artikel
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, articleTitles);
        articleListView.setAdapter(adapter);

        // Inisialisasi Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("articles");

        // Mengambil data dari Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                articleTitles.clear();
                articles.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Article article = snapshot.getValue(Article.class);
                    if (article != null) {
                        articleTitles.add(article.getTitle());
                        articles.add(article);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
            }
        });

        // Menambahkan OnItemClickListener pada ListView
        articleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article clickedArticle = articles.get(position);
                Intent intent = new Intent(ListArticlesActivity.this, DetailArticleActivity.class);
                intent.putExtra("title", clickedArticle.getTitle());
                intent.putExtra("content", clickedArticle.getContent());
                startActivity(intent);
            }
        });
    }
}