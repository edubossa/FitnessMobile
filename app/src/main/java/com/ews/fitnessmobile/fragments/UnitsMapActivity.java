package com.ews.fitnessmobile.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.ews.fitnessmobile.PopupUnitView;
import com.ews.fitnessmobile.R;
import com.ews.fitnessmobile.dao.UnidadeDAO;
import com.ews.fitnessmobile.model.Unidade;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class UnitsMapActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {

    public static final String TAG = "[FragmentActivity]";

    private GoogleMap mMap;
    private UnidadeDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_units_map);
        this.dao = new UnidadeDAO(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(UnitsMapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMarkerClickListener(this); //OBS aqui tenho que adicionar os eventos que quero interceptar de forma implicita
        mMap = googleMap;
        loadUnits();
    }

    private void loadUnits() {
        List<Unidade> unidades = dao.getAll();
        for (Unidade u : unidades) {
            LatLng position = new LatLng(Double.valueOf(u.getLatitude()), Double.valueOf(u.getLongitude()));
            Marker marker = mMap.addMarker(new MarkerOptions().position(position)
                    .title(u.getCidade())
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
                    .snippet(u.getNome()));
            marker.setTag(u.getId());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 10));
        }
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        new PopupUnitView(this, marker, dao);
        return false;
    }


}
