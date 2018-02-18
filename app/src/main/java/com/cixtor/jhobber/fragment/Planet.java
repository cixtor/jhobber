package com.cixtor.jhobber.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cixtor.jhobber.R;
import com.cixtor.jhobber.activity.Main;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

public class Planet extends Fragment implements OnMapReadyCallback, GoogleMap.OnCameraIdleListener {
    private Main parent;
    private GoogleMap mGoogleMap;
    private SupportMapFragment mMapFragment;
    private OnFragmentInteractionListener mListener;

    private final int GOOGLE_MAP_ZOOM = 12;
    private final double GOOGLE_MAP_LATITUDE = 49.2850668662163;
    private final double GOOGLE_MAP_LONGITUDE = -123.11317313882061;

    public Planet() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.parent = (Main) this.getActivity();

        if (mListener != null) {
            mListener.onFragmentInteraction("Map");
        }

        View v = inflater.inflate(R.layout.fragment_planet, container, false);

        if (mMapFragment == null) {
            mMapFragment = SupportMapFragment.newInstance();
            mMapFragment.getMapAsync(this);
        }

        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.googleMap, mMapFragment)
                .commit();

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        try {
            this.mGoogleMap = map;

            MapStyleOptions theme = MapStyleOptions.loadRawResourceStyle(parent, R.raw.google_map);

            if (!map.setMapStyle(theme)) {
                parent.alert("Failure parsing Google Maps stylesheet.");
                return;
            }

            LatLng gps = new LatLng(GOOGLE_MAP_LATITUDE, GOOGLE_MAP_LONGITUDE);

            CameraPosition camera = new CameraPosition.Builder()
                    .zoom(GOOGLE_MAP_ZOOM)
                    .target(gps)
                    .build();

            mGoogleMap.clear();

            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(camera));

            mGoogleMap.setOnCameraIdleListener(this);
        } catch (Resources.NotFoundException e) {
            parent.alert("Cannot find Google Maps stylesheet: " + e.getMessage());
        }
    }

    @Override
    public void onCameraIdle() {
        LatLngBounds current = mGoogleMap.getProjection().getVisibleRegion().latLngBounds;

        Toast.makeText(parent, current.toString(), Toast.LENGTH_SHORT).show();

        this.drawMapMarker(GOOGLE_MAP_LATITUDE, GOOGLE_MAP_LONGITUDE);
    }

    private void drawMapMarker(double latitude, double longitude) {
        LatLng gps = new LatLng(latitude, longitude);

        MarkerOptions options = new MarkerOptions()
                .title(getString(R.string.gps_you_are_here))
                .position(gps);

        mGoogleMap.addMarker(options);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String title);
    }
}
