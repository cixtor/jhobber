package com.cixtor.jhobber;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

public class PlanetFragment extends Fragment implements OnMapReadyCallback {

    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private SupportMapFragment mMapFragment;
    private OnFragmentInteractionListener mListener;

    private final String TAG = "JHOBBER";
    private final int GOOGLE_MAP_ZOOM = 12;
    private final double GOOGLE_MAP_LATITUDE = 49.2850668662163;
    private final double GOOGLE_MAP_LONGITUDE = -123.11317313882061;

    public PlanetFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_planet, container, false);

        if (mMapFragment == null) {
            mMapFragment = SupportMapFragment.newInstance();
            mMapFragment.getMapAsync(this);
        }

        getChildFragmentManager().beginTransaction().replace(R.id.googleMap, mMapFragment).commit();

        if (mListener != null) {
            mListener.onFragmentInteraction("Map");
        }

        return rootView;
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
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        LatLng latlng = new LatLng(GOOGLE_MAP_LATITUDE, GOOGLE_MAP_LONGITUDE);

        try {
            boolean success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    getActivity(),
                    R.raw.google_map
                )
            );

            if (!success) {
                Log.e(TAG, "Style parsing failure.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Cannot find Google Map Style; error: ", e);
        }

        CameraPosition cameraPosition = new CameraPosition.Builder()
            .zoom(GOOGLE_MAP_ZOOM)
            .target(latlng)
            .build();

        mGoogleMap.addMarker(new MarkerOptions().position(latlng).title("Current Location"));
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String title);
    }
}
