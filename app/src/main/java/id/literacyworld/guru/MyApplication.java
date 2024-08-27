package id.literacyworld.guru;

import android.app.Application;
import com.google.firebase.messaging.FirebaseMessaging;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Inisialisasi Firebase Messaging
        FirebaseMessaging.getInstance().subscribeToTopic("murid_login");
    }
}
