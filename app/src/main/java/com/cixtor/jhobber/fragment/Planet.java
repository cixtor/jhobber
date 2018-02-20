package com.cixtor.jhobber.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cixtor.jhobber.R;
import com.cixtor.jhobber.activity.Main;
import com.cixtor.jhobber.model.Job;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Planet extends Fragment implements OnMapReadyCallback, GoogleMap.OnCameraIdleListener, View.OnClickListener {
    private Main parent;
    private TextView mJobQuery;
    private Button mJobSearch;
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

        mJobQuery = v.findViewById(R.id.jobQuery);
        mJobSearch = v.findViewById(R.id.jobSearch);

        mJobSearch.setOnClickListener(this);

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

            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(camera));

            mGoogleMap.setOnCameraIdleListener(this);
        } catch (Resources.NotFoundException e) {
            parent.alert("Cannot find Google Maps stylesheet: " + e.getMessage());
        }
    }

    @Override
    public void onCameraIdle() {
        this.triggerJobSearch();
    }

    @Override
    public void onClick(View v) {
        this.triggerJobSearch();
    }

    private void triggerJobSearch() {
        mGoogleMap.clear();

        mJobSearch.setEnabled(false);

        parent.hideKeyboard(mJobQuery);

        LatLngBounds current = mGoogleMap.getProjection().getVisibleRegion().latLngBounds;

        JsonObjectRequest obj = new JsonObjectRequest(
                Request.Method.GET,
                parent.WEB_SERVICE + "/jobs"
                        + "?query=" + mJobQuery.getText()
                        + "&sw_lat=" + current.southwest.latitude
                        + "&sw_lon=" + current.southwest.longitude
                        + "&ne_lat=" + current.northeast.latitude
                        + "&ne_lon=" + current.northeast.longitude,
                null,
                this.getJobsResponseListener(parent, this),
                this.getJobsErrorListener(parent, this)
        );

        parent.addRequestToQueue(obj);
    }

    private Response.Listener<JSONObject> getJobsResponseListener(final Main parent, final Planet here) {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                mJobSearch.setEnabled(true);

                try {
                    here.addJobsToTheMap(res);
                } catch (JSONException e) {
                    parent.alert(e.getMessage());
                }
            }
        };
    }

    public Response.ErrorListener getJobsErrorListener(final Main parent, final Planet here) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                /* https://stackoverflow.com/a/24700973 */
                parent.alert(error.toString());
                mJobSearch.setEnabled(false);
            }
        };
    }

    private void addJobsToTheMap(JSONObject res) throws JSONException {
        if (!res.getBoolean("ok")) {
            parent.alert(res.getString("error"));
            return;
        }

        ArrayList<Job> jobs = parent.collectJobsData(res);

        for (Job job : jobs) {
            this.drawMapMarker(job);
        }
    }

    private void drawMapMarker(Job job) {
        LatLng gps = new LatLng(job.getLatitude(), job.getLongitude());

        MarkerOptions options = new MarkerOptions()
                .title(job.getCompany() + "\n" + job.getTitle())
                .position(gps);

        mGoogleMap.addMarker(options);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String title);
    }
}
