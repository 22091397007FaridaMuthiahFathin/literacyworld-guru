package id.literacyworld.guru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.literacy.literacyworld.R;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000; // Waktu tampilan splash screen (dalam milidetik)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Membuat handler untuk menangani delay sebelum memulai activity berikutnya
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Pindah ke activity berikutnya setelah SPLASH_TIME_OUT
                Intent loginIntent = new Intent(MainActivity.this, login.class);
                startActivity(loginIntent);
                finish(); // Menutup activity saat ini
            }
        }, SPLASH_TIME_OUT);
    }
}