package unimas.fcsit.foodieroute;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by elliotching on 11-Apr-17.
 */

// sample get from the stackoverflow , targeting API-23 and above.
public class ActivityMaps extends AppCompatActivity
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    /* DO NOT CONTINUOUSLY MOVE CAMERA (SCREEN VIEW) TOWARDS THE USER LOCATION
    * AS THIS PAGE IS FOR SELLER TO SET LOCATION ONLY! */


    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    MapView mapView;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;

    Button buttonSaveLocation;

    double[] markerLocation;

    private final static int mapZoomLevel = 18;

    MapLocationListener mapLocationListener;

    Context context = this;
    AppCompatActivity activity = (AppCompatActivity) context;

    private static Dialog_Progress dialogProgress_loadLocation;

    private class MapLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            mLastLocation = location;

            // on first launch
            if(mCurrLocationMarker == null){
                dialogProgress_loadLocation.dismiss();
                // first launch, move map camera / viewport to user location
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, mapZoomLevel));
            }

            shiftMarkerToCurrentLocation();

            // Options: auto put marker at my current location
            // Options: auto move screen to my current location
        }
    }

    private class OnMapTouched implements GoogleMap.OnMapClickListener {

        @Override
        public void onMapClick(LatLng latLng) {
            shiftMarkerToTouchedLocation(latLng);
        }
    }

    private void shiftMarkerToCurrentLocation() {
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //Place current location marker
        LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker());
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
        markerLocation = new double[]{
                mLastLocation.getLatitude(),
                mLastLocation.getLongitude()
        };
    }

    private void shiftMarkerToTouchedLocation(LatLng latLng) {
        MarkerOptions marker = new MarkerOptions().position(latLng);

        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        mCurrLocationMarker = mGoogleMap.addMarker(marker);
        markerLocation = new double[]{
                latLng.latitude,
                latLng.longitude
        };
    }

    @Override
    public boolean onSupportNavigateUp() {
        setResult(RESULT_CANCELED);
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new UniversalLayoutInitToolbarAndTheme(activity, R.layout.activity_maps, R.id.toolbar, true);

        mapLocationListener = new MapLocationListener();

        buttonSaveLocation = (Button) findViewById(R.id.button_save_location);
        buttonSaveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLocation();
            }
        });

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

//        try {
//            MapsInitializer.initialize(activity.getApplicationContext());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        mapView.getMapAsync(this);
    }

    private void saveLocation() {
        Intent i = new Intent();
        i.putExtra("savedlocation", markerLocation);
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, mapLocationListener);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        // MapType: can be Satelite mode ETC
        //mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //Initialize Google Play Services

        mGoogleMap.setOnMapClickListener(new OnMapTouched());

        //if device OS SDK >= 23 (Marshmallow)
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            //IF Location Permission already granted
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                // enable button + cursor of "MyLocation" on top right corner
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                // Request Location Permission
                checkLocationPermission();
            }
        }

        //if device OS is version 5 Lollipop and below ( <= SDK_22 )
        else {
            // check location here
            if (checkLocationPermission_v22()) {
                // Location Permission already granted
                buildGoogleApiClient();
                // enable button + cursor of "MyLocation" on top right corner
                mGoogleMap.setMyLocationEnabled(true);
            }else{
                DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        activity.finish();
                    }
                };
                String StringWarningLocationNotGranted = ResFR.string(context, R.string.s_dialog_msg_location_not_granted);
                new Dialog_AlertNotice(context, "Location", StringWarningLocationNotGranted)
                        .setPositiveKey("OK",onClickListener);
            }
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

        dialogProgress_loadLocation = new Dialog_Progress(activity, "Location", "Getting user's location...", true);
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, mapLocationListener);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }


    private boolean checkLocationPermission_v22() {
        LocationManager locManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return true;
        } else {
            return false;
        }
    }

    public static final int FR_PERMISSIONS_REQUEST_CODE_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    FR_PERMISSIONS_REQUEST_CODE_LOCATION);
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
//
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//                new Dialog_AlertNotice(this, "Location Permission Needed",
//                        "This app needs the Location permission, please accept to use location functionality")
//                        .setPositiveKey("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        //Prompt the user once explanation has been shown
//                        ActivityCompat.requestPermissions(ActivityMaps.this,
//                                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                                FR_PERMISSIONS_REQUEST_CODE_LOCATION );
//                    }
//                }).show();
//
//            } else {
//                // No explanation needed, we can request the permission.
//
//            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case FR_PERMISSIONS_REQUEST_CODE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            activity.finish();
                        }
                    };
                    new Dialog_AlertNotice(context, "Denied", "Permission denied. Exiting map...").setPositiveKey("OK", onClickListener);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}

/*

XML ...........................
* <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical" android:layout_width="match_parent"
    android:layout_height="match_parent">

    <fragment xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:map="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/map"
        tools:context=".MapLocationActivity"
        android:name="com.google.android.gms.maps.SupportMapFragment"/>

</LinearLayout>
*
* */
