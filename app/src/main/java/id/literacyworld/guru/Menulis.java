package id.literacyworld.guru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.literacy.literacyworld.R;

public class Menulis extends AppCompatActivity {

    private EditText titleEditText, contentEditText;
    private View submitButton, viewArticlesButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menulis);
        // Inisialisasi Firebase Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("articles");

        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);
        submitButton = findViewById(R.id.submitButton);
        viewArticlesButton = findViewById(R.id.viewArticlesButton);


        submitButton.setOnClickListener(view -> submitArticle());
        viewArticlesButton.setOnClickListener(view -> {
            Intent intent = new Intent(Menulis.this, ListArticlesActivity.class);
            startActivity(intent);
        });
    }



    private void submitArticle() {
        String title = titleEditText.getText().toString().trim();
        String content = contentEditText.getText().toString().trim();

        if (!title.isEmpty() && !content.isEmpty()) {
            String articleId = databaseReference.push().getKey();
            Article article = new Article(articleId, title, content);

            assert articleId != null;
            databaseReference.child(articleId).setValue(article).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(Menulis.this, "Article submitted", Toast.LENGTH_SHORT).show();
                    titleEditText.setText("");
                    contentEditText.setText("");
                } else {
                    Toast.makeText(Menulis.this, "Failed to submit article", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
        }
    }
}

class Article {
    public String articleId;
    public String title;
    public String content;

    public Article() {

    }

    public Article(String articleId, String title, String content) {
        this.articleId = articleId;
        this.title = title;
        this.content = content;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}