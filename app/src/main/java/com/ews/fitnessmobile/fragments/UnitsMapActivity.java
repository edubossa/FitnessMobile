package com.ews.fitnessmobile.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ews.fitnessmobile.PopupUnitView;
import com.ews.fitnessmobile.R;
import com.ews.fitnessmobile.dao.UnidadeDAO;
import com.ews.fitnessmobile.model.Unidade;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class UnitsMapActivity extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {

    MapView mMapView;
    private GoogleMap googleMap;
    private UnidadeDAO dao;
    private Context ctx;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_units_map, container, false);
        this.ctx = view.getContext();
        this.dao = new UnidadeDAO(this.ctx);

        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(this.ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMarkerClickListener(this); //OBS aqui tenho que adicionar os eventos que quero interceptar de forma implicita
        this.googleMap = googleMap;
        loadUnits();
    }

    private void loadUnits() {
        List<Unidade> unidades = dao.getAll();
        for (Unidade u : unidades) {
            LatLng position = new LatLng(Double.valueOf(u.getLatitude()), Double.valueOf(u.getLongitude()));
            Marker marker = googleMap.addMarker(new MarkerOptions().position(position)
                    .title(u.getCidade())
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
                    .snippet(u.getNome()));
            marker.setTag(u.getId());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 10));
        }
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        new PopupUnitView(this.ctx, marker, dao);
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.map_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.viewList :
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_main, new UnitsFragment())
                        .addToBackStack(null)
                        .commit();
                break;
        }

        return super.onOptionsItemSelected(item);
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

}