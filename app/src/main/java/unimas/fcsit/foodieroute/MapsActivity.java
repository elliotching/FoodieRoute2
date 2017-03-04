package unimas.fcsit.foodieroute;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends Fragment {

    Context mContext;
    Fragment mFragment = this;
    AppCompatActivity mActivity;
    MapView mMapView;
    private GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_maps, container, false);

        mContext = this.getActivity();
        mActivity = (AppCompatActivity) mContext;

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new FoodieRoute_OnMapReadyCallback());

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
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
    public class FoodieRoute_OnMapReadyCallback implements OnMapReadyCallback {

        @Override
        public void onMapReady(GoogleMap googleMap) {
//            mMap = googleMap;
//
//            // Add a marker in Sydney and move the camera
//            LatLng sydney = new LatLng(-34, 151);
//            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


            mMap = googleMap;
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    Toast.makeText(mContext, latLng.toString(), Toast.LENGTH_SHORT).show();
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Marker Title").snippet("Marker Description"));
                }
            });
            // For showing a move to my location button
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
                return;
            }
            googleMap.setMyLocationEnabled(true);
            googleMap.getMyLocation();

            // For dropping a marker at a point on the Map
            LatLng sydney = new LatLng(-34, 151);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

            // For zooming automatically to the location of the marker
            CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();

            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

//    public class A implements ActivityCompat.OnRequestPermissionsResultCallback{
//
//        @Override
//        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        }
//    }
}
