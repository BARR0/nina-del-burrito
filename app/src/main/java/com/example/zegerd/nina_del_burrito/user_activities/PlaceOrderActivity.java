package com.example.zegerd.nina_del_burrito.user_activities;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.zegerd.nina_del_burrito.MainActivity;
import com.example.zegerd.nina_del_burrito.R;
import com.example.zegerd.nina_del_burrito.classes.Item;
import com.example.zegerd.nina_del_burrito.classes.Order;
import com.example.zegerd.nina_del_burrito.classes.User;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PlaceOrderActivity extends AppCompatActivity {
    public static final int RESULT_BOUGHT = 1;
    public static final int RESULT_NOT_BOUGHT = 0;
    public static final int PLACE_PICKER_REQUEST = 1;
    private TextView textViewTotal;
    private static TextView textViewDate;
    private EditText editTextDetails;
    private static GregorianCalendar time;
    private LatLng location;

    private int response;
    private FirebaseAuth mAuth;
    private User currentUser;

    protected GeoDataClient mGeoDataClient;
    protected PlaceDetectionClient mPlaceDetectionClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        // Init FireBase stuff
        mAuth = FirebaseAuth.getInstance();
        currentUser = (User) getIntent().getSerializableExtra(MainActivity.USER_DATA);

        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this, null);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

        response = RESULT_NOT_BOUGHT;
        setResult(response);

        textViewTotal = (TextView)findViewById(R.id.textViewTotal);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
        editTextDetails = (EditText)findViewById(R.id.editTextDetails);

        float total = 0f;
        for (Item item: NavigationUserActivity.carrito) {
            total += item.getPrecio();
        }

        textViewTotal.setText("Total: $" + total);
        time = new GregorianCalendar();
        textViewDate.setText(time.get(GregorianCalendar.HOUR) + ":" + time.get(GregorianCalendar.MINUTE));
        location = null;
    }

    public void getLocation(View v) {
//        Intent intent = new Intent(this, DeliveryActivity.class);
//        startActivityForResult(intent, 0);

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public void setTime(View v){
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void buy(View v){
        if (location == null){
            Toast.makeText(this, "Ubicacion Incorrecta", Toast.LENGTH_SHORT).show();
            return;
        }
        response = RESULT_BOUGHT;
        setResult(response);
//         TODO change this with the quantity chosen by the user
//        int quantity = 1;
        for (int i = 0; i < NavigationUserActivity.carrito.size(); ++i) {
            setOrders(NavigationUserActivity.carrito.get(i), CarritoActivity.quantities[i]);
        }
        Toast.makeText(this, "Orden Enviada", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void setOrders(Item item, int quantity){
        String vendorId = item.getVendorid();
        String clientId = mAuth.getCurrentUser().getUid();

        DatabaseReference currentItemDB = FirebaseDatabase.getInstance()
                .getReference()
                .child("ClientOrders")
                .child(vendorId)
                .child(clientId);
        // push() generates an unique id
        String key = currentItemDB.push().getKey();
        Order order = new Order(quantity,
                editTextDetails.getText().toString() != null ? editTextDetails.getText().toString() : "",
                time,
                vendorId + item.getNombre(),
                item.getNombre(),
                key,
                currentUser.getUsername(),
                clientId,
                location);

        currentItemDB.child(key).setValue(order);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                location = place.getLatLng();
//                String toastMsg = String.format("Place: %s", place.getName());
//                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }

//        if(data == null) return;
//        double lat = data.getDoubleExtra("lat", Double.NaN);
//        double lng = data.getDoubleExtra("lng", Double.NaN);
//        if (!Double.isNaN(lat) && !Double.isNaN(lng)){
//            location = new LatLng(lat, lng);
//        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            time = new GregorianCalendar();
            time.set(GregorianCalendar.HOUR, hourOfDay);
            time.set(GregorianCalendar.MINUTE, minute);
            textViewDate.setText(time.get(GregorianCalendar.HOUR) + ":" + time.get(GregorianCalendar.MINUTE));
        }
    }
}
