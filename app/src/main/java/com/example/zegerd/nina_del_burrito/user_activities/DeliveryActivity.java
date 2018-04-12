package com.example.zegerd.nina_del_burrito.user_activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.zegerd.nina_del_burrito.R;
// Manually added R

public class DeliveryActivity extends FragmentActivity implements   OnMapReadyCallback,
                                                                    GoogleMap.OnMapClickListener,
                                                                    GoogleApiClient.ConnectionCallbacks,
                                                                    GoogleApiClient.OnConnectionFailedListener,
                                                                    com.google.android.gms.location.LocationListener {

    private GoogleMap mMap;

    private GoogleApiClient client;
    private Location lastLocation;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(com.example.zegerd.nina_del_burrito.R.id.map);
        mapFragment.getMapAsync(this);

        if (client == null){
            client = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000 * 5);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setOnMapClickListener(this);
        setMyLocation();
    }

    public void setMyLocation(){
        // verificar que haya permiso y si hay habilitar capa.
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            // Pedir permiso
            // arreglo de strings que son permisos
            // request code - igual que con actividades
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }else{
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] p, int[] r){
        if(requestCode == 0 && r[0] == PackageManager.PERMISSION_GRANTED){
            Log.wtf("PERMISOS", "Autorizado");
            setMyLocation();
        }else{
            Log.wtf("PERMISOS", "No Autorizado");
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;

        // Empezar request
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(client);
        if (lastLocation != null) {
            Log.d("LAST LOCATION", lastLocation.getLatitude() + ", " + lastLocation.getLongitude());
            Intent intent = new Intent();
            intent.putExtra("lat", lastLocation.getLatitude());
            intent.putExtra("lng", lastLocation.getLongitude());
            setResult(1, intent);
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, (com.google.android.gms.location.LocationListener) this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }
}
