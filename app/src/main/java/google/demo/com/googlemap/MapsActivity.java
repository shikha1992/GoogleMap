package google.demo.com.googlemap;

import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public String address_current = "";
    public double lat, longitude;
    Button btn_show_lat;
    GPSTracker gps;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        btn_show_lat = (Button) findViewById(R.id.btn_show_lat);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        btn_show_lat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gps = new GPSTracker(MapsActivity.this);

                // check if GPS enabled
                if (gps.canGetLocation()) {

                    lat = gps.getLatitude();
                    longitude = gps.getLongitude();
                    address_current = gps.address(lat, longitude);

                    mapFragment.getMapAsync(MapsActivity.this);

                    Toast.makeText(getApplicationContext(), "Your Lat is : " + lat + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                } else {
                    gps.showSettingsAlert();
                }
            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng get_lat_long = new LatLng(lat, longitude);
        mMap.addMarker(new MarkerOptions().position(get_lat_long).title(address_current));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom((get_lat_long), 12.0f));
        //////////draw route between two points
        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(lat, longitude), new LatLng(19.0180, 72.8448))
                .width(5)
                .color(Color.RED));

    }


}
