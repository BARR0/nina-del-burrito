package com.example.zegerd.nina_del_burrito;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.zegerd.nina_del_burrito.adapters.AllItemAdapter;
import com.example.zegerd.nina_del_burrito.classes.User;
import com.example.zegerd.nina_del_burrito.user_activities.NavigationUserActivity;
import com.example.zegerd.nina_del_burrito.user_activities.UserActivity;
import com.example.zegerd.nina_del_burrito.vendor_activities.VendorActivity;
import com.example.zegerd.nina_del_burrito.vendor_activities.VendorOrdersActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    public static final int VENDEDOR = 1;
    public static final int CLIENT = 0;

    public static AllItemAdapter allItemAdapter;
    public static final String USER_DATA = "user";

    // Instance of FireBaseAuth
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authListener;
    private User currentUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init UI items

        // Init FireBaseAuth
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Handle null user
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
        checkUserType(user);
    }

    private void checkUserType(final FirebaseUser user) {
        FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    currentUserInfo = dataSnapshot.getValue(User.class);
                    int userType = currentUserInfo.getRolId();
                    switch (userType)
                    {
                        case VENDEDOR:
                            showVendorScreen(user);
                            break;
                        case CLIENT:
                            showClientScreen();
                            break;
                        default:
                            Log.e("User rol type Error", "unknown user type: " + userType);
                            break;
                    }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showVendorScreen(FirebaseUser user) {
        Log.d("HIHI", user.getUid());
        FirebaseMessaging.getInstance().subscribeToTopic(user.getUid());
        Intent intent = new Intent(MainActivity.this, VendorActivity.class);
        startActivity(intent);
        finish();
    }

    private void showClientScreen() {
        allItemAdapter = new AllItemAdapter(this);
        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //startActivity(new Intent(getApplicationContext(), VendorOrdersActivity.class));
                Intent intent = new Intent(MainActivity.this, NavigationUserActivity.class);
                intent.putExtra(USER_DATA, currentUserInfo);
                startActivity(intent);
                finish();
            }
        }, secondsDelayed * 1000);
        //Intent intent = new Intent(MainActivity.this, UserActivity.class);
        //startActivity(intent);
        //finish();
    }


}
