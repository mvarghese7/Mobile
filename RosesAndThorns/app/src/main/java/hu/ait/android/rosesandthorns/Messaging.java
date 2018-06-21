package hu.ait.android.rosesandthorns;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by mayavarghese on 5/17/18.
 */

public class Messaging extends FirebaseMessagingService {

    public static final String TAG = "TAG";

    public Messaging() {
    }

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {


        Handler handlerUI = new Handler(
                Messaging.this.getMainLooper());
        handlerUI.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Messaging.this,
                        "PUSH: "+remoteMessage.getFrom(), Toast.LENGTH_SHORT).show();
            }
        });

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());


        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }
}

