package com.github.bagiasn.code4fun.activities;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.github.bagiasn.code4fun.R;
import com.github.bagiasn.code4fun.helpers.HttpHelper;
import com.github.bagiasn.code4fun.helpers.JsonHelper;
import com.github.bagiasn.code4fun.models.database.Attribute;
import com.github.bagiasn.code4fun.models.database.OrganizationMarker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap gMap;
    private ArrayList<OrganizationMarker> markers;
    private String keyword;
    private Location knownLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        markers = new ArrayList<>();

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, R.string.no_permissions, Toast.LENGTH_SHORT).show();
            return;
        }
        keyword = getIntent().getStringExtra("keyword");
        knownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(knownLocation.getLatitude(),
                knownLocation.getLongitude()), 12));
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);

        new GetMarkers().execute(keyword,
                String.valueOf(knownLocation.getLongitude()),
                String.valueOf(knownLocation.getLatitude()));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("Google Map", getString(R.string.no_permissions));
            return;
        }
        googleMap.setMyLocationEnabled(true);
    }

    private class GetMarkers extends AsyncTask<String,Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String url = "http://46.101.196.53:54870/getMapPlaces?text=" + params[0]
                    + "&longitude=" + params[1] + "&latitude=" + params[2];
            String jsonResponse = HttpHelper.makeJsonRequest(url);
            if (jsonResponse != null) {
                markers = JsonHelper.getMarkers(jsonResponse);
                if (markers != null) {
                    return true;
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if (result) {
                for (OrganizationMarker marker : markers) {
                    gMap.addMarker(new MarkerOptions()
                            .position(new LatLng(marker.getLatitude(), marker.getLongtitude()))
                            .title(marker.getName()));
                }
            } else {
                Toast.makeText(MapsActivity.this, R.string.error_markers, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
